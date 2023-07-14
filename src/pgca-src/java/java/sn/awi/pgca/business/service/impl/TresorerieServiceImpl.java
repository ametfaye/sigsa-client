package sn.awi.pgca.business.service.impl;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sn.awi.pgca.business.dao.ModelDAO;
import sn.awi.pgca.business.exception.BudgetException;
import sn.awi.pgca.business.exception.ConnectionException;
import sn.awi.pgca.business.exception.EntityDBDAOException;
import sn.awi.pgca.business.exception.UtilisateurException;
import sn.awi.pgca.business.service.ITresorerieService;
import sn.awi.pgca.dataMapping.DTOFactory;
import sn.awi.pgca.dataMapping.ModelFactory;
import sn.awi.pgca.dataModel.Allocation;
import sn.awi.pgca.dataModel.AvanceSurCredit;
import sn.awi.pgca.dataModel.Cheque;
import sn.awi.pgca.dataModel.CollecteProduits;
import sn.awi.pgca.dataModel.Commande;
import sn.awi.pgca.dataModel.Credit;
import sn.awi.pgca.dataModel.Intrant;
import sn.awi.pgca.dataModel.Personne;
import sn.awi.pgca.dataModel.PointDeCollecte;
import sn.awi.pgca.dataModel.PointDeVente;
import sn.awi.pgca.dataModel.Profil;
import sn.awi.pgca.dataModel.ProgrammeAgricol;
import sn.awi.pgca.dataModel.ReferentialIntrants;
import sn.awi.pgca.dataModel.Stock;
import sn.awi.pgca.dataModel.StockResiduelPointDeVente;
import sn.awi.pgca.dataModel.Subvention;
import sn.awi.pgca.dataModel.Tarificateur;
import sn.awi.pgca.dataModel.Utilisateur;
import sn.awi.pgca.dataModel.VenteProduitAssocie;
import sn.awi.pgca.dataModel.Ventes;
import sn.awi.pgca.dataModel.VersementBanque;
import sn.awi.pgca.utils.Password;
import sn.awi.pgca.utils.UtilString;
import sn.awi.pgca.web.bean.ConstantPGCA;
import sn.awi.pgca.web.dto.AllocationDTO;
import sn.awi.pgca.web.dto.AvanceCreditDTO;
import sn.awi.pgca.web.dto.ChequeDTO;
import sn.awi.pgca.web.dto.ConnectionDTO;
import sn.awi.pgca.web.dto.CreditDTO;
import sn.awi.pgca.web.dto.IntrantDTO;
import sn.awi.pgca.web.dto.ProduitDTO;
import sn.awi.pgca.web.dto.SubventionDTO;
import sn.awi.pgca.web.dto.UtilisateurDTO;
import sn.awi.pgca.web.dto.VentesDTO;
import sn.awi.pgca.web.dto.VersementBanqueDTO;

@Named("tresorerieService")
public class TresorerieServiceImpl implements ITresorerieService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7195128248996545017L;
	private static final Log LOG = LogFactory.getLog(TresorerieServiceImpl.class);
	@Inject
	private ModelDAO modelDAO;
	@Inject
	private ModelFactory modelFactory;
	@Inject
	private DTOFactory dtoFactory;

	// @Inject
	// private Inotification notifierService;

	UtilString utils = new UtilString();
	Date datejour = new Date();
	SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy");
	
	@Override
	public boolean vendreProduit(List<ProduitDTO> selectedProduit, VentesDTO infoVente) throws EntityDBDAOException {
		Personne auteurVente = (Personne) modelDAO.getEntityDBById(infoVente.getIdvendeur(), Personne.class);
		Stock stockVente = (Stock) modelDAO.getEntityDBById(infoVente.getIdstockVente(), Stock.class);

		
		if (auteurVente == null || stockVente == null)
			return false;

		Ventes newVente = new Ventes();
		newVente.setMontantVente(infoVente.getMontantVente());
		newVente.setMontantEncaisse(infoVente.getMontantVente());
		 newVente.setDateDeVente(dt1.format(datejour)); 
		newVente.setStockVente(stockVente);
		newVente.setVendeur(auteurVente);
		newVente.setClient(infoVente.getClient());
		newVente.setTelClient(infoVente.getTelclient());
		newVente.setAdresseClient(infoVente.getAdresseclient());
		newVente.setZoneVente(infoVente.getZoneVente());
		newVente.setMontantPayeEnBonDeSubvention(infoVente.getMontantEncaisseParBonDeSubvention());
		newVente.setMontantPayeEnCheque(infoVente.getMontantEncaisseParCheque());
		newVente.setMontantPayeEnEspece(infoVente.getMontantEncaisseParEspece());
		newVente.setMontantPayeBLP(infoVente.getMontantEncaisseBLP());
		newVente.setNumeroBLP(infoVente.getNumeroBLP());

		modelDAO.save(newVente);
		infoVente.setVentes_id(newVente.getVentes_id());

		for (ProduitDTO p : selectedProduit) {
			VenteProduitAssocie pVendu = new VenteProduitAssocie();
			pVendu.setVenteAssocie(newVente);
			pVendu.setProduitVendu((Intrant) modelDAO.getEntityDBById(p.getIdProduit(), Intrant.class));
			pVendu.setQuantiteVendu(p.getQuantiteProduitAvendre());
			pVendu.setMontantTotalVendu(p.getPrixTotal());
			pVendu.setPrixUnitaire(new Float(p.getPrixUnitaire()));
			modelDAO.save(pVendu);
			// Deduction vente sur le stock
			Intrant produitVendu = (Intrant) modelDAO.getEntityDBById(p.getIdProduit(), Intrant.class);
			produitVendu.setQuantite(produitVendu.getQuantite() - p.getQuantiteProduitAvendre());
			modelDAO.save(produitVendu);
			// Mise a jour complément subvention à recupérer auprès de l'etat
			if (produitVendu.getTarif() != null) {
				if (produitVendu.getTarif().getTauxSubvention() > 0) {
					Subvention s = new Subvention();
					s.setDateSubvention(new Date());
					s.setVenteConcerne(newVente);
					s.setPointDeVente(
							(PointDeVente) modelDAO.getEntityDBById(infoVente.getIdPointDeVente(), PointDeVente.class));
					s.setMontantSubvention( new Float (produitVendu.getTarif().getTotalsubventionEtatArecouvrir() * pVendu.getQuantiteVendu()));
					s.setDetailsSubvention("Subvention de "  + pVendu.getQuantiteVendu() + " (T) de "   + produitVendu.getIntrant().getLibelle() + " à "
							+  utils.formatFloatToCFA (produitVendu.getTarif().getTotalsubventionEtatArecouvrir()) + " la tonne.");
					// s.setProgrammeConcerne(produitVendu.getProgramme());

					modelDAO.save(s);
				}

			}

		}
		return true;
	}

	/***
	 * Pour les point de ventes les ventes se font sur les stock residuel :
	 * 
	 * A partir de l'id du stock 'pgca_stockresiduelpointdevente' on calcule la
	 * vente et et diminue le stock restant
	 * 
	 * @param selectedProduit
	 * @param infoVente
	 * @return
	 * @throws EntityDBDAOException
	 */

	@Override
	public boolean vendreProduitFromPointdeVente(IntrantDTO infosIntrant, VentesDTO infoVente)
			throws EntityDBDAOException {
		Personne auteurVente = (Personne) modelDAO.getEntityDBById(infoVente.getIdvendeur(), Personne.class);
		Stock stockVente = (Stock) modelDAO.getEntityDBById(infoVente.getIdstockVente(), Stock.class);

		if (auteurVente == null || stockVente == null)
			return false;

		StockResiduelPointDeVente stockPointDeVente = ((StockResiduelPointDeVente) modelDAO
				.getEntityDBById(infosIntrant.getIdStockResiduel(), StockResiduelPointDeVente.class));
		if (stockPointDeVente != null) {
			vendreProduitFromStockMiseEnplace(infosIntrant, infoVente, stockVente, auteurVente, stockPointDeVente);
			// Deduction vente sur le stock residuel
			stockPointDeVente.setTotalStockActuel(
					stockPointDeVente.getTotalStockActuel() - infosIntrant.getQuantiteAtransferer());
			modelDAO.save(stockPointDeVente);
		} else {
			Intrant intrant = (Intrant) modelDAO.getEntityDBById(infosIntrant.getIdProduit(), Intrant.class);
			
			if (intrant != null) {
				vendreProduitFromStockLocal(infosIntrant, infoVente, stockVente, auteurVente, intrant);
				// Deduction produit collecte
				intrant.setQuantite(intrant.getQuantite() - infosIntrant.getQuantiteAtransferer());
				modelDAO.save(intrant);
			}
		}
		return true;
	}

	// vente sur des produit mise en place (: déduction stock
	// pgca_stockResiduelPointDeVente)
	private boolean vendreProduitFromStockMiseEnplace(IntrantDTO infosIntrant, VentesDTO infoVente, Stock stockVente,
			Personne auteurVente, StockResiduelPointDeVente stockPointDeVente) {
		try {
			Ventes newVente = new Ventes();
			newVente.setMontantVente(
					new Float(infosIntrant.getQuantiteAtransferer() * infosIntrant.getPrixProducteur()));
			newVente.setMontantEncaisse(infoVente.getMontantVente());
			newVente.setDateDeVente(new java.sql.Date(Calendar.getInstance().getTime().getTime()) + "");
			newVente.setStockVente(stockVente);
			newVente.setVendeur(auteurVente);
			newVente.setClient(infoVente.getClient());
			newVente.setTelClient(infoVente.getTelclient());
			newVente.setAdresseClient(infoVente.getAdresseclient());
			newVente.setMontantPayeBLP(infoVente.getMontantEncaisseBLP());
			newVente.setNumeroBLP(infoVente.getNumeroBLP());
			
			// cas multi point de vente :  Ex : coordinateurs qui gére plusieurs PV
			if(infoVente.getZoneVente() != null)
			{
				if(infoVente.getZoneVente().trim().equals(""))
				{
					List<StockResiduelPointDeVente> stockDeReference = (List<StockResiduelPointDeVente>) modelDAO.genericClassLoaderById(StockResiduelPointDeVente.class, "stockResiduel_id", infoVente.getIdProduitAssocie());		
					if(stockDeReference !=  null && stockDeReference.size() > 0)
						infoVente.setZoneVente(stockDeReference.get(0).getPointdeVente().getLibelle());
				}
			}
			
				
				
			newVente.setZoneVente(infoVente.getZoneVente());
			newVente.setMontantPayeEnBonDeSubvention(infoVente.getMontantEncaisseParBonDeSubvention());
			newVente.setMontantPayeEnCheque(infoVente.getMontantEncaisseParCheque());
			newVente.setMontantPayeEnEspece(infoVente.getMontantEncaisseParEspece());
			modelDAO.save(newVente);
			infoVente.setVentes_id(newVente.getVentes_id());

			VenteProduitAssocie pVendu = new VenteProduitAssocie();
			pVendu.setVenteAssocie(newVente);
			pVendu.setProduitVendu(stockPointDeVente.getIntrant());
			pVendu.setQuantiteVendu(infosIntrant.getQuantiteAtransferer());
			pVendu.setMontantTotalVendu(infosIntrant.getQuantiteAtransferer() * infosIntrant.getPrixProducteur());
			pVendu.setPrixUnitaire(new Float(infosIntrant.getPrixProducteur()));
			modelDAO.save(pVendu);

			// Mise a jour complément subvention à recupérer auprès de l'etat
			if (stockPointDeVente.getIntrant() != null && stockPointDeVente.getIntrant().getTarif() != null) {
				if (stockPointDeVente.getIntrant().getTarif().getTauxSubvention() > 0) {
					Subvention s = new Subvention();
					java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
					s.setDateSubvention(date);
					s.setVenteConcerne(newVente);
					s.setStock(stockVente);
					s.setMontantSubvention(stockPointDeVente.getIntrant().getTarif().getPrixNonSubventionne()
							- stockPointDeVente.getIntrant().getTarif().getPrixProducteur());
					s.setDetailsSubvention("Subvention de " + stockPointDeVente.getIntrant().getLibelle()
							+ " à hauteur de " + stockPointDeVente.getIntrant().getTarif().getTauxSubvention() + "% ");
					// s.setProgrammeConcerne(produitVendu.getProgramme());
					modelDAO.save(s);
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// vente sur des produit mise en place (: déduction stock
	// pgca_stockResiduelPointDeVente)

	private boolean vendreProduitFromStockLocal(IntrantDTO infosIntrant, VentesDTO infoVente, Stock stockVente,
			Personne auteurVente, Intrant intrant) {
		try {

			Ventes newVente = new Ventes();
			newVente.setMontantVente(
					new Float(infosIntrant.getQuantiteAtransferer() * infosIntrant.getPrixProducteur()));
			newVente.setMontantEncaisse(infoVente.getMontantVente());
			newVente.setDateDeVente(new java.sql.Date(Calendar.getInstance().getTime().getTime()) + "");
			newVente.setStockVente(stockVente);
			newVente.setVendeur(auteurVente);
			newVente.setClient(infoVente.getClient());
			newVente.setTelClient(infoVente.getTelclient());
			newVente.setAdresseClient(infoVente.getAdresseclient());
			newVente.setZoneVente(infoVente.getZoneVente());
			newVente.setMontantPayeEnBonDeSubvention(infoVente.getMontantEncaisseParBonDeSubvention());
			newVente.setMontantPayeEnCheque(infoVente.getMontantEncaisseParCheque());
			newVente.setMontantPayeEnEspece(infoVente.getMontantEncaisseParEspece());
			newVente.setMontantPayeBLP(infoVente.getMontantEncaisseBLP());
			newVente.setNumeroBLP(infoVente.getNumeroBLP());
			modelDAO.save(newVente);
			infoVente.setVentes_id(newVente.getVentes_id());

			VenteProduitAssocie pVendu = new VenteProduitAssocie();
			pVendu.setVenteAssocie(newVente);
			pVendu.setProduitVendu(intrant);
			pVendu.setQuantiteVendu(infosIntrant.getQuantiteAtransferer());
			pVendu.setMontantTotalVendu(infosIntrant.getQuantiteAtransferer() * infosIntrant.getPrixProducteur());
			pVendu.setPrixUnitaire(new Float(infosIntrant.getPrixProducteur()));
			modelDAO.save(pVendu);

			// Mise a jour complément subvention à recupérer auprès de l'etat
			if (intrant != null && intrant.getTarif() != null) {
				if (intrant.getTarif().getTauxSubvention() > 0) {
					Subvention s = new Subvention();
					java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
					s.setDateSubvention(date);
					s.setVenteConcerne(newVente);
					s.setStock(stockVente);
					s.setMontantSubvention(intrant.getTarif().getPrixNonSubventionne()
							- intrant.getTarif().getPrixProducteur());
					s.setDetailsSubvention("Subvention de " + intrant.getLibelle()
							+ " à hauteur de " + intrant.getTarif().getTauxSubvention() + "% ");
					// s.setProgrammeConcerne(produitVendu.getProgramme());
					modelDAO.save(s);
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean vendreProduitAvecCredit(List<ProduitDTO> selectedProduit, VentesDTO infoVente, CreditDTO creditDTO)
			throws EntityDBDAOException {
		Personne auteurVente = (Personne) modelDAO.getEntityDBById(infoVente.getIdvendeur(), Personne.class);
		Stock stockVente = (Stock) modelDAO.getEntityDBById(infoVente.getIdstockVente(), Stock.class);

		if (auteurVente == null || stockVente == null)
			return false;

		Ventes newVente = new Ventes();
		newVente.setDateDeVente(infoVente.getDateVente());
		newVente.setMontantVente(infoVente.getMontantVente());
		newVente.setMontantEncaisse(infoVente.getMontantEncaisse());

		newVente.setStockVente(stockVente);
		newVente.setVendeur(auteurVente);
		newVente.setClient(infoVente.getClient());
		newVente.setTelClient(infoVente.getTelclient());
		newVente.setAdresseClient(infoVente.getAdresseclient());
		newVente.setZoneVente(infoVente.getZoneVente());

		newVente.setMontantPayeEnBonDeSubvention(infoVente.getMontantEncaisseParBonDeSubvention());
		newVente.setMontantPayeEnCheque(infoVente.getMontantEncaisseParCheque());
		newVente.setMontantPayeEnEspece(infoVente.getMontantEncaisseParEspece());
		newVente.setMontantPayeBLP(infoVente.getMontantEncaisseBLP());
		newVente.setNumeroBLP(infoVente.getNumeroBLP());

		modelDAO.save(newVente);

		infoVente.setVentes_id(newVente.getVentes_id());

		for (ProduitDTO p : selectedProduit) {
			VenteProduitAssocie pVendu = new VenteProduitAssocie();

			pVendu.setVenteAssocie(newVente);
			pVendu.setProduitVendu((Intrant) modelDAO.getEntityDBById(p.getIdProduit(), Intrant.class));
			pVendu.setQuantiteVendu(p.getQuantiteProduitAvendre());
			pVendu.setMontantTotalVendu(p.getPrixTotal());
			pVendu.setPrixUnitaire(new Float(p.getPrixUnitaire()));
			modelDAO.save(pVendu);

			// Deduction vente sur le stock
			Intrant produitVendu = (Intrant) modelDAO.getEntityDBById(p.getIdProduit(), Intrant.class);
			produitVendu.setQuantite(produitVendu.getQuantite() - p.getQuantiteProduitAvendre());
			modelDAO.save(produitVendu);

			// Mise a jour complément subvention à recupérer auprès de l'etat

			if (produitVendu.getTarif() != null) {
				if (produitVendu.getTarif().getTauxSubvention() > 0) {
					Subvention s = new Subvention();

					s.setDateSubvention(new Date());
					s.setVenteConcerne(newVente);
					s.setPointDeVente(
							(PointDeVente) modelDAO.getEntityDBById(infoVente.getIdPointDeVente(), PointDeVente.class));
					s.setMontantSubvention(produitVendu.getTarif().getPrixNonSubventionne()
							- produitVendu.getTarif().getPrixProducteur());
					s.setDetailsSubvention("Subvention de " + produitVendu.getLibelle() + " à hauteur de "
							+ produitVendu.getTarif().getTauxSubvention() + "% ");
					// s.setProgrammeConcerne(produitVendu.getProgramme());

					modelDAO.save(s);
				}

			}
		}

		// Creation du credit lié a la vente

		Credit crd = new Credit();

		crd.setAuteur(auteurVente);
		crd.setDateContraction(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
		crd.setMontantInitialCredit(creditDTO.getMontantInitialCredit());
		crd.setMontantRestantApayer(creditDTO.getMontantInitialCredit());
		// crd.setMontantRestantApayer(montantRestantApayer);
		crd.setResumeCredit(creditDTO.getResumeCredit());
		crd.setSouscripteur(creditDTO.getNomsouscripteur());
		crd.setTelSouscripteur(creditDTO.getTelSouscripteur());
		crd.setVenteConcerne(newVente);
		crd.setZone(creditDTO.getZoneCredit());
		crd.setStockPointdeVente(stockVente);

		modelDAO.save(crd);

		// Ref Credit : CRT + ID
		crd.setReferenceCredit("CRT000" + crd.getId());
		modelDAO.save(crd);
		return true;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean AllouerBudget(AllocationDTO allocationBudget) throws BudgetException, EntityDBDAOException {

		try {

			Personne collaborateur = (Personne) modelDAO
					.getEntityDBById(allocationBudget.getIdColloborateurBeneficiaire(), Personne.class);
			PointDeCollecte pc = (PointDeCollecte) modelDAO
					.getEntityDBById(allocationBudget.getIdPointDecollecteBeneficiaire(), PointDeCollecte.class);

			ProgrammeAgricol prog = (ProgrammeAgricol) modelDAO.getEntityDBById(allocationBudget.getIdProgramme(),
					ProgrammeAgricol.class);

			ReferentialIntrants p = (ReferentialIntrants) modelDAO
					.getEntityDBById(allocationBudget.getIdProduitACollecter(), ReferentialIntrants.class);

			if (collaborateur == null || pc == null || p == null || prog == null)
				return false;

			Allocation alloc = new Allocation();

			alloc.setCollaborateur(collaborateur);
			alloc.setPointdeCollecteConcerne(pc);
			alloc.setProgrammeConcerne(prog);
			alloc.setProduitACollecter(p);
			alloc.setMontantalloue(allocationBudget.getMontantalloue());
			alloc.setDateAllcation(new Date());
			alloc.setAuteurAllocation(allocationBudget.getAuteurAllocation());

			alloc.setMontantalloue(allocationBudget.getMontantalloue());
			modelDAO.save(alloc);
			LOG.info("--------------: Bugdet de " + allocationBudget.getMontantalloue() + "alloué avec succes");

			allocationBudget.setCollaborateurBeneficiare(collaborateur.getPrenom() + " " + collaborateur.getNom());
			return true;
		} catch (Exception e) {
			LOG.error("--------------:Erreur lors de l'allocation du budget");

			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public CreditDTO getListAvancebyIdCredit(Long idCredit) throws EntityDBDAOException {
		CreditDTO detailsCredits = new CreditDTO();
		Credit cr = (Credit) modelDAO.getEntityDBById(idCredit, Credit.class);

		if (cr == null)
			return null;

		detailsCredits.setCredit_id(cr.getCredit_id());
		detailsCredits.setAuteurCreditLibelle(
				cr.getAuteur() != null ? cr.getAuteur().getPrenom() + " " + cr.getAuteur().getNom() : "");
		detailsCredits.setDateContraction(cr.getDateContraction() + "");
		detailsCredits.setMontantInitialCredit(cr.getMontantInitialCredit());
		detailsCredits.setMontantRestantApayer(cr.getMontantRestantApayer());
		detailsCredits.setNomsouscripteur(cr.getSouscripteur());
		detailsCredits.setAdresseSouscripteur(cr.getAdresseSouscripteur());
		detailsCredits.setTelSouscripteur(cr.getTelSouscripteur());
		detailsCredits.setReferenceCredit(cr.getReferenceCredit());
		detailsCredits.setResumeCredit(cr.getResumeCredit());
		// detailsCredits.setVenteConcerne(cr.getVenteConcerne());
		detailsCredits.setZoneCredit(cr.getZone());

		List<AvanceSurCredit> listAvance = (List<AvanceSurCredit>) modelDAO
				.genericSqlClassLoaderById(AvanceSurCredit.class, "pgca_creditAvance", "cr_id", idCredit);

		if (listAvance != null) {
			detailsCredits.setListAvanceCredits(new ArrayList<AvanceCreditDTO>());

			for (AvanceSurCredit c : listAvance) {
				AvanceCreditDTO avance = new AvanceCreditDTO();

				avance.setId_avance(c.getAvanceCredit_id());
				avance.setDateAvance(c.getDateAvance());
				avance.setMontanance(c.getValeurAvance());
				avance.setMoyenPaiement(c.getMoyenPaiement());
				avance.setAuteurPaiement(c.getAuteurPaiement());
				detailsCredits.getListAvanceCredits().add(avance);
			}
		}
		return detailsCredits;
	}

	@Override
	public List<AvanceCreditDTO> loadAllAvancesFromCreditsId(Long credit_id) {
		List<AvanceSurCredit> listAvance = (List<AvanceSurCredit>) modelDAO
				.genericSqlClassLoaderById(AvanceSurCredit.class, "pgca_creditAvance", "cr_id", credit_id);

		List<AvanceCreditDTO> detailsCredits = new ArrayList<AvanceCreditDTO>();

		for (AvanceSurCredit c : listAvance) {
			AvanceCreditDTO avance = new AvanceCreditDTO();

			avance.setId_avance(c.getAvanceCredit_id());
			avance.setDateAvance(c.getDateAvance());
			avance.setMontanance(c.getValeurAvance());
			avance.setMoyenPaiement(c.getMoyenPaiement());

			detailsCredits.add(avance);
		}

		return detailsCredits;
	}

	@Override
	public List<SubventionDTO> loadAllSubventions() throws EntityDBDAOException {
		@SuppressWarnings("unchecked")
		List<Subvention> listAvance = (List<Subvention>) modelDAO.loadAllSubvention();

		List<SubventionDTO> listeSubvention = new ArrayList<SubventionDTO>();

		for (Subvention c : listAvance) {
			SubventionDTO s = new SubventionDTO();

			s.setDateSubvention(c.getDateSubvention());
			s.setDetailsSubvention(c.getDetailsSubvention());
			s.setId_subvention(c.getId_sub());
			s.setMontantSubvention(c.getMontantSubvention());
			s.setReferenceVentes(c.getVenteConcerne() != null ? c.getVenteConcerne().getVentes_id() + "" : "");
			s.setNomClientBenefiaire(c.getVenteConcerne() != null ? c.getVenteConcerne().getClient() + "" : "");
			s.setZone(c.getVenteConcerne() != null ? c.getVenteConcerne().getZoneVente() + "" : "");

			// s.setId_programme(c.getProgrammeConcerne() != null ?
			// c.getProgrammeConcerne().getId() : 0L );
			// s.setLibelleProgramme(c.getProgrammeConcerne() != null ?
			// c.getProgrammeConcerne().getLibelle() : "");

			listeSubvention.add(s);
		}

		return listeSubvention;
	}

	@Override
	public Map<Long, String> enregistrerAvance(AvanceCreditDTO avanceDTO, Long credit_id) {
		HashMap<Long, String> infosRetun = new HashMap<Long, String>();

		try {
			Credit cr = (Credit) modelDAO.getEntityDBById(credit_id, Credit.class);

			// Verif Excedent Paiement pour avance
			if (cr.getMontantRestantApayer() < avanceDTO.getMontanance()) {
				infosRetun.put(0L,
						"Le montant restant du credit à payer " + utils.formatFloatToCFA(cr.getMontantRestantApayer())
								+ " est inferieur au montant de l'avance "
								+ utils.formatFloatToCFA(avanceDTO.getMontanance()));
				return infosRetun;
			}

			AvanceSurCredit avance = new AvanceSurCredit();

			avance.setAvanceCredit_id(credit_id);
			avance.setCreditConcerne(cr);
			avance.setDateAvance(new Date());
			avance.setValeurAvance(avanceDTO.getMontanance());
			avance.setAuteurPaiement(avanceDTO.getAuteurPaiement());

			if (avanceDTO.getMoyenPaiement().equals("1"))
				avance.setMoyenPaiement("Espèces");
			else if (avanceDTO.getMoyenPaiement().equals("2"))
				avance.setMoyenPaiement("Chéque");
			else if (avanceDTO.getMoyenPaiement().equals("3"))
				avance.setMoyenPaiement("Bon de subvention");
			else if (avanceDTO.getMoyenPaiement().equals("4"))
				avance.setMoyenPaiement("Nature");

			modelDAO.save(avance);
			// deduction avance sur le credit
			cr.setMontantRestantApayer(cr.getMontantRestantApayer() - avanceDTO.getMontanance());
			modelDAO.save(cr);

			infosRetun.put(1L,
					"L'acompte de " + utils.formatFloatToCFA(avanceDTO.getMontanance())
							+ " est pris en compte avec succès, le reste à payer  de " + cr.getSouscripteur()
							+ " est de " + utils.formatFloatToCFA(cr.getMontantRestantApayer()));
			return infosRetun;

		} catch (EntityDBDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		infosRetun.put(2L, "Une erreur est survenue lors de l'enregistrement du credit");
		return infosRetun;
	}

	public boolean creerUtilisateur(UtilisateurDTO utilisateurdto) throws UtilisateurException {
		LOG.info("Création de utilisateur PGCA <" + utilisateurdto.getCode());
		try {
			Utilisateur user = modelDAO.getUtilisateurbyCode(utilisateurdto.getCode());
			if (user != null)
				throw new UtilisateurException("L'utilisateur de code '" + utilisateurdto.getCode() + "' existe déjà");
		} catch (UtilisateurException ue) {
			throw ue;
		}

		Password password = new Password();
		try {
			String pwd = password.getEncodedPasswordWithoutCharset(utilisateurdto.getPassword());
			utilisateurdto.setPassword(pwd);
		} catch (UnsupportedEncodingException uee) {
			throw new UtilisateurException("Erreur init mot de passe ", uee);
		}

		Personne personne = new Personne();
		modelFactory.createPersonne(personne, utilisateurdto);

		Utilisateur newutilisateur = new Utilisateur();
		modelFactory.createUtilisateur(newutilisateur, utilisateurdto);
		newutilisateur.setMdpamodifier(true);

		/****** Affectation Profil ***********/
		if (UtilString.isCorrect(utilisateurdto.getProfilId())) {
			Profil p;
			try {
				p = (Profil) modelDAO.getEntityDBById(new Long(utilisateurdto.getProfilId()), Profil.class);
				if (p != null)
					newutilisateur.setProfil(p);
				else {
					LOG.error("Erreur de recupération du profil pendant la création de l'utilisateur");
					return false;
				}
			} catch (NumberFormatException | EntityDBDAOException ex) {
				LOG.error("Erreur de recupération du profil pendant la création de l'utilisateur");
				ex.printStackTrace();
			}
		}
		/****** Affectation Profil OK **********/

		Long typeProfil = Long.parseLong(utilisateurdto.getProfilId());

		/****** Affectation ***********/
		if (typeProfil == 4) // Si agent de collecte , le point de collecte est
								// obligatoire
		{
			try {
				PointDeCollecte pc = (PointDeCollecte) modelDAO
						.getEntityDBById(new Long(utilisateurdto.getPointDecollectId()), PointDeCollecte.class);
				if (pc != null)
					newutilisateur.setPointdeCollecte(pc);
				else {
					LOG.error("Erreur de recupération du point de collecte pendant la création de l'utilisateur");
					return false;
				}
			} catch (NumberFormatException | EntityDBDAOException ex) {
				LOG.error("Erreur de recupération du point de collecte pendant la création de l'utilisateur");
				ex.printStackTrace();
			}
		} else if (typeProfil == 5) // Si agent de Vente le point de vente est
									// obligatoire
		{
			try {
				PointDeVente pv = (PointDeVente) modelDAO.getEntityDBById(new Long(utilisateurdto.getPointDeVentetId()),
						PointDeVente.class);
				if (pv != null)
					newutilisateur.setPointdeVente(pv);
				else {
					LOG.error("Erreur de recupération du point de vente pendant la création de l'utilisateur");
					return false;
				}
			} catch (NumberFormatException | EntityDBDAOException ex) {
				LOG.error("Erreur de recupération du point de vente pendant la création de l'utilisateur");
				ex.printStackTrace();
			}
		}

		try {
			modelDAO.save(personne);
			LOG.debug("tentative d'enregistrement de utilisateur <" + utilisateurdto.getCode());
			newutilisateur.setPersonne(personne);
			modelDAO.save(newutilisateur);

			LOG.info("Creation du user <" + utilisateurdto.getLibelle() + "> OK ");

		} catch (EntityDBDAOException e) {
			LOG.error(
					"Enregistrement utilisateur impossible pour <" + utilisateurdto.getCode() + "> :" + e.getMessage(),
					e);

		}
		return true;
	}

	/********************** DEPOT BANQUE ET UTILS *******************/
	/********************
	 * FIN BANQUE *************************************************************
	 ***************************************************************/
	@Override
	public boolean enregistrerVersmentBanque(VersementBanqueDTO v) {
		try {
			VersementBanque versement = new VersementBanque();
			PointDeVente pv = (PointDeVente) modelDAO.getEntityDBById(v.getIdPointdeVente(), PointDeVente.class);
			Personne auteur = (Personne) modelDAO.getEntityDBById(v.getIdPersonneAuteurVersment(), Personne.class);

			if (pv != null) {
				versement.setPointdeVente(pv);
			} else {
				PointDeCollecte pc = (PointDeCollecte) modelDAO.getEntityDBById(v.getIdPointDeCollecte(),
						PointDeCollecte.class);
				versement.setPointdeCollecte(pc);
			}
			versement.setMontantVersment(v.getMontantVersment());
			versement.setMoyenVersement(v.getMoyenVersment());
			versement.setLibelleVersment(v.getLibelleVersment());
			versement.setBanque(v.getBanque());
			versement.setNumeroBLP(v.getBlpNumero());
			versement.setMontantPayeParBLP(v.getBlpMontant());
			

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
			String[] tmp = v.getDateDeVersement().split("-");
			String dateString = tmp[2] + "/" + tmp[1] + "/" + tmp[0];
			Date parseDate = sdf.parse(dateString);
			versement.setDateVersement(parseDate);
			versement.setAuteurVersement(auteur);
			modelDAO.save(versement);
			modelDAO.synchroniseWithDB(versement); // Refresh : recuperation Id
													// Versement pour la
													// création du fichier

			String pathFile = v.getPathdocumentJustificatif() + "/Versement" + versement.getIdVersment() + "."
					+ v.getDocumentJustificatifextension();
			versement.setPathDocumentJustificatif(pathFile);
			v.setIdVersment(versement.getIdVersment());
			modelDAO.save(versement);
//
//			String title = "Versement Sedab N°" + versement.getIdVersment();
//			String message = auteur.getPrenom() + " " + auteur.getNom() + ", le gérant du point de vente  "
//					+ pv.getLibelle() + " a effectué  un versement  de <b>"
//					+ utils.formatFloatToCFA(v.getMontantVersment()) + " </b> à l'établissement financier <b>"
//					+ v.getBanque() + "</b> le " + dateString + ".";
//
//			File dir = new File(v.getPathdocumentJustificatif());
//			File[] tes = dir.listFiles();
//			String[] tess = dir.list();

			// String path = "Users/Amet/GED-PGCA/Versement/Macka Ndiaye";
			// notifierService.sendEmailWithAttachement(title, message, new
			// File(pathFile));

			return true;
		} catch (Exception e) {
			LOG.error("--------------: Erreur survement lors  de l'enregistrement du point de vente ");

			return false;
		}
	}

	/*********************** BANQUE *****************************/

	@Override
	public List<VersementBanqueDTO> recupererVersementBanqueByIdPointDeVente(Long idPointdeVente) {
		@SuppressWarnings("unchecked")
		List<VersementBanque> listVersement = (List<VersementBanque>) modelDAO
				.genericSqlClassLoaderById(VersementBanque.class, "pgca_versementBanque", "ptv_id", idPointdeVente);

		if (listVersement == null)
			return null;

		List<VersementBanqueDTO> lpdto = new ArrayList<VersementBanqueDTO>();

		for (VersementBanque v : listVersement) {
			VersementBanqueDTO nv = new VersementBanqueDTO();
			nv.setIdVersment(v.getIdVersment());
			// nv.setDate(utils.getFormatedDateFromString(v.getDateVersement()));
			nv.setDateDeVersement(utils.getFormatedDateFromString(v.getDateVersement()));
			nv.setIdPersonneAuteurVersment(v.getAuteurVersement() != null ? v.getAuteurVersement().getId() : null);
			nv.setLibellePersonneAuteurVersment(v.getAuteurVersement() != null
					? v.getAuteurVersement().getPrenom() + " " + v.getAuteurVersement().getNom() : "");
			nv.setLibelleVersment(v.getLibelleVersment());
			nv.setMontantVersment(v.getMontantVersment());
			nv.setMoyenVersment(v.getMoyenVersement());
			nv.setPathdocumentJustificatif(v.getPathDocumentJustificatif());
			nv.setBanque(v.getBanque());
			nv.setZone(v.getPointdeVente() != null ? v.getPointdeVente().getLibelle() : "Non renseigné");
			nv.setBlpMontant(v.getMontantPayeParBLP());
			nv.setBlpNumero(v.getNumeroBLP());
			lpdto.add(nv);
		}
		return lpdto;
	}

	@Override
	public List<VersementBanqueDTO> recupererVersementBanqueByIdPointDeCollecte(Long idPointdeCollecte) {
		@SuppressWarnings("unchecked")
		List<VersementBanque> listVersement = (List<VersementBanque>) modelDAO
				.genericSqlClassLoaderById(VersementBanque.class, "pgca_versementBanque", "pdc_id", idPointdeCollecte);

		if (listVersement == null)
			return null;

		List<VersementBanqueDTO> lpdto = new ArrayList<VersementBanqueDTO>();

		for (VersementBanque v : listVersement) {
			VersementBanqueDTO nv = new VersementBanqueDTO();
			nv.setIdVersment(v.getIdVersment());
			// nv.setDate(utils.getFormatedDateFromString(v.getDateVersement()));
			nv.setDateDeVersement(utils.getFormatedDateFromString(v.getDateVersement()));
			nv.setIdPersonneAuteurVersment(v.getAuteurVersement() != null ? v.getAuteurVersement().getId() : null);
			nv.setLibellePersonneAuteurVersment(v.getAuteurVersement() != null
					? v.getAuteurVersement().getPrenom() + " " + v.getAuteurVersement().getNom() : "");
			nv.setLibelleVersment(v.getLibelleVersment());
			nv.setMontantVersment(v.getMontantVersment());
			nv.setMoyenVersment(v.getMoyenVersement());
			nv.setBlpNumero((v.getNumeroBLP()));
			nv.setPathdocumentJustificatif(v.getPathDocumentJustificatif());
			nv.setBanque(v.getBanque());
			nv.setZone(v.getPointdeVente() != null ? v.getPointdeVente().getLibelle() : "Non renseigné");

			lpdto.add(nv);
		}

		return lpdto;
	}

	@Override
	public List<VersementBanqueDTO> recupererAllVersementBanqueByCampagne() {

		List<VersementBanque> depots = (List<VersementBanque>) modelDAO.genericClassLoader(VersementBanque.class);

		if (depots == null)
			return null;

		List<VersementBanqueDTO> lpdto = new ArrayList<VersementBanqueDTO>();

		for (VersementBanque v : depots) {
			VersementBanqueDTO nv = new VersementBanqueDTO();
			nv.setIdVersment(v.getIdVersment());
			// nv.setDate(utils.getFormatedDateFromString(v.getDateVersement()));
			nv.setDateDeVersement(utils.getFormatedDateFromString(v.getDateVersement()));
			nv.setIdPersonneAuteurVersment(v.getAuteurVersement() != null ? v.getAuteurVersement().getId() : null);
			nv.setLibellePersonneAuteurVersment(v.getAuteurVersement() != null
					? v.getAuteurVersement().getPrenom() + " " + v.getAuteurVersement().getNom() : "");
			nv.setLibelleVersment(v.getLibelleVersment());
			nv.setMontantVersment(v.getMontantVersment());
			nv.setMoyenVersment(v.getMoyenVersement());
			nv.setPathdocumentJustificatif(v.getPathDocumentJustificatif());
			nv.setBanque(v.getBanque());

			if (v.getPointdeVente() != null)
				nv.setZone(v.getPointdeVente().getLibelle());
			else if (v.getPointdeCollecte() != null)
				nv.setZone(v.getPointdeCollecte().getLibelle());
			else
				nv.setZone("Non renseigné");

			lpdto.add(nv);
		}

		return lpdto;
	}

	/********************
	 * FIN BANQUE
	 **************************************************/

	/********************** CREDIT UTILS *******************/
	/********************
	 * CREDIT *************************************************************
	 ***************************************************************/

	@Override
	public boolean enregistrerCredit(CreditDTO creditDTO) {

		try {

			Credit crd = new Credit();
			Personne p = (Personne) modelDAO.getEntityDBById(creditDTO.getAuteurCreditId(), Personne.class);

			Commande c = (Commande) modelDAO.getEntityDBById(creditDTO.getCommandeId(), Commande.class);

			if (p == null)
				return false;

			crd.setAuteur(p);
			crd.setDateContraction(new Date());
			crd.setMontantInitialCredit(creditDTO.getMontantInitialCredit());
			crd.setMontantRestantApayer(creditDTO.getMontantInitialCredit());
			crd.setCommandeConcerne(c);
			// crd.setMontantRestantApayer(montantRestantApayer);
			crd.setResumeCredit(creditDTO.getResumeCredit());
			crd.setSouscripteur(creditDTO.getNomsouscripteur());
			crd.setTelSouscripteur(creditDTO.getTelSouscripteur());
			// crd.setVenteConcerne(newVente);
			crd.setZone(creditDTO.getZoneCredit());
			// crd.setStockPointdeVente(stockVente);

			modelDAO.save(crd);

			crd.setReferenceCredit("CRD000" + crd.getCredit_id());

			modelDAO.save(crd);

			creditDTO.setReferenceCredit("CRD000" + crd.getCredit_id());
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	/********************
	 * FIN CREDIT
	 **************************************************/

	@Override
	public List<CreditDTO> loadAllCreditsOfProgramme() {

		List<Credit> creditsList = (List<Credit>) modelDAO.genericClassLoader(Credit.class);

		if (creditsList == null)
			return null;

		List<CreditDTO> creditDTO = new ArrayList<CreditDTO>();

		for (Credit c : creditsList) {
			CreditDTO cto = new CreditDTO();

			cto.setCredit_id(c.getCredit_id());
			cto.setTelSouscripteur(c.getTelSouscripteur());
			cto.setNomsouscripteur(c.getSouscripteur());
			cto.setMontantInitialCredit(c.getMontantInitialCredit());
			cto.setMontantRestantApayer(c.getMontantRestantApayer());
			cto.setAuteurCreditLibelle(
					c.getAuteur() != null ? c.getAuteur().getPrenom() + " " + c.getAuteur().getNom() : "");

			if (c.getDateContraction() != null) {
				String[] tmp = c.getDateContraction().toString().split(" ");
				cto.setDateContraction(tmp[0]);
			}

			if (c.getDateDerniereAvanceSurCredit() != null) {
				String[] tmp = c.getDateDerniereAvanceSurCredit().toString().split(" ");
				cto.setDateContraction(tmp[0]);
			}

			cto.setReferenceCredit(c.getReferenceCredit());
			cto.setResumeCredit(c.getResumeCredit());
			cto.setZoneCredit(c.getZone());
			cto.setVenteConcerneLibelle(c.getVenteConcerne() + "");

			creditDTO.add(cto);
		}
		return creditDTO;
	}

	@Override
	public float loadMontantTotalDesVentes() throws EntityDBDAOException {
		List<Ventes> lv = (List<Ventes>) modelDAO.genericClassLoader(Ventes.class);

		if (lv == null)
			return 0L;

		float montantTotalVente = 0;
		for (Ventes v : lv)
			montantTotalVente += v.getMontantVente();

		return montantTotalVente;
	}

	@Override
	public float loadMontantTotalDesVentesByIdProduit(Long idProduit) throws EntityDBDAOException {

		List<VenteProduitAssocie> lv = (List<VenteProduitAssocie>) modelDAO.genericSqlClassLoaderById(
				VenteProduitAssocie.class, "pgca_venteProduitsAssocies", "produit_id", idProduit);

		if (lv == null)
			return 0L;

		float montantTotalVente = 0;
		for (VenteProduitAssocie v : lv)
			montantTotalVente += v.getMontantTotalVendu();

		return montantTotalVente;

	}

	@Override
	public float loadMontantTotalDesCredits() throws EntityDBDAOException {
		List<Credit> lc = (List<Credit>) modelDAO.genericClassLoader(Credit.class);
		if (lc == null)
			return 0L;
		float montantTCredit = 0;
		for (Credit c : lc)
			montantTCredit += c.getMontantRestantApayer();
		return montantTCredit;
	}

	@Override
	public boolean tarifierProduit(boolean subventionne, Float tauxSubvention, Float prixAvantSubvention,
			Long idIntrant) throws EntityDBDAOException {

		Intrant intrantConcerce = (Intrant) modelDAO.getEntityDBById(idIntrant, Intrant.class);
		Tarificateur tarif;
		Long auteurModification = null;
		boolean updated = false;
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (request != null) {
			HttpSession session = request.getSession(false);
			auteurModification = (Long) session.getAttribute("connectedUserPersonneid");
		}

		if (intrantConcerce == null)
			return false;

		tarif = intrantConcerce.getTarif();

		if (tarif != null) // si le produit a déjà un tarif UPDATE IT sinon on//
							// le créé
		{
			tarif.setAuteurTarification((Personne) modelDAO.getEntityDBById(auteurModification, Personne.class));

			if (subventionne) {
				tarif.setSubventionne(true);
				tarif.setTauxSubvention(tauxSubvention);
				tarif.setPrixNonSubventionne(prixAvantSubvention);
				tarif.setPrixProducteur((prixAvantSubvention - (prixAvantSubvention * tauxSubvention) / 100));
				tarif.setTotalsubventionEtatArecouvrir(tarif.getPrixNonSubventionne() - tarif.getPrixProducteur());
			} else {
				tarif.setSubventionne(false);
				tarif.setTauxSubvention(0f);
				tarif.setPrixNonSubventionne(prixAvantSubvention);
				tarif.setPrixProducteur(prixAvantSubvention);
				tarif.setTotalsubventionEtatArecouvrir(0f);
			}
			modelDAO.save(tarif);
			updated = true;
		} else {
			tarif = new Tarificateur();
			tarif.setAuteurTarification((Personne) modelDAO.getEntityDBById(auteurModification, Personne.class));

			if (subventionne) {

				tarif.setSubventionne(true);
				tarif.setTauxSubvention(tauxSubvention);
				tarif.setPrixNonSubventionne(prixAvantSubvention);
				tarif.setPrixProducteur((prixAvantSubvention - (prixAvantSubvention * tauxSubvention) / 100));
				tarif.setTotalsubventionEtatArecouvrir(tarif.getPrixNonSubventionne() - tarif.getPrixProducteur());
			} else {
				tarif.setSubventionne(false);
				tarif.setTauxSubvention(0f);
				tarif.setPrixNonSubventionne(prixAvantSubvention);
				tarif.setPrixProducteur(prixAvantSubvention);
				tarif.setTotalsubventionEtatArecouvrir(0f);
			}
			modelDAO.save(tarif);
			intrantConcerce.setTarif(tarif);
			modelDAO.save(intrantConcerce);
			updated = true;
		}

		return updated;
	}

	@Override
	public List<CreditDTO> loadAllCreditsByIdPointDeVente(Long idPoindDeVente, Long idProgramme)
			throws EntityDBDAOException {
		@SuppressWarnings("unchecked")
		List<Credit> creditsList = (List<Credit>) modelDAO.genericSqlClassLoaderById(Credit.class, "pgca_credit",
				"stock_id", idPoindDeVente);

		if (creditsList == null)
			return null;

		List<CreditDTO> creditDTO = new ArrayList<CreditDTO>();

		for (Credit c : creditsList) {
			CreditDTO cto = new CreditDTO();

			cto.setCredit_id(c.getCredit_id());
			cto.setTelSouscripteur(c.getTelSouscripteur());
			cto.setNomsouscripteur(c.getSouscripteur());
			cto.setMontantInitialCredit(c.getMontantInitialCredit());
			cto.setMontantRestantApayer(c.getMontantRestantApayer());
			cto.setAuteurCreditLibelle(
					c.getAuteur() != null ? c.getAuteur().getPrenom() + " " + c.getAuteur().getNom() : "");

			if (c.getDateContraction() != null) {
				String[] tmp = c.getDateContraction().toString().split(" ");
				cto.setDateContraction(tmp[0]);
			}

			if (c.getDateDerniereAvanceSurCredit() != null) {
				String[] tmp = c.getDateDerniereAvanceSurCredit().toString().split(" ");
				cto.setDateContraction(tmp[0]);
			}

			cto.setReferenceCredit(c.getReferenceCredit());
			cto.setResumeCredit(c.getResumeCredit());
			cto.setZoneCredit(c.getZone());
			cto.setVenteConcerneLibelle(c.getVenteConcerne() + "");

			creditDTO.add(cto);
		}
		return creditDTO;
	}

	@Override
	public CreditDTO loadAllCreditsFromIdCommande(Long idCommmande) {
		Credit c = modelDAO.loadCreditByIdCMD(idCommmande);

		CreditDTO cto = null;
		if (c != null) {
			cto = new CreditDTO();

			cto.setCredit_id(c.getCredit_id());
			cto.setTelSouscripteur(c.getTelSouscripteur());
			cto.setNomsouscripteur(c.getSouscripteur());
			cto.setMontantInitialCredit(c.getMontantInitialCredit());
			cto.setMontantRestantApayer(c.getMontantRestantApayer());
			cto.setAuteurCreditLibelle(
					c.getAuteur() != null ? c.getAuteur().getPrenom() + " " + c.getAuteur().getNom() : "");

			cto.setDateContraction(c.getDateContraction() + "");

			cto.setReferenceCredit(c.getReferenceCredit());
			cto.setResumeCredit(c.getResumeCredit());
			cto.setZoneCredit(c.getZone());
			cto.setVenteConcerneLibelle(
					c.getCommandeConcerne() != null ? c.getCommandeConcerne().getReferenceCMD() : "");

		}
		return cto;
	}

	@Override
	public List<VersementBanqueDTO> loadAllVersementBanqueByIdPointDeVente(Long idPoindDeVente, Long idProgramme) {

		@SuppressWarnings("unchecked")
		List<VersementBanque> creditsList = (List<VersementBanque>) modelDAO
				.genericSqlClassLoaderById(VersementBanque.class, "pgca_versementBanque", "ptv_id", idPoindDeVente);

		if (creditsList == null)
			return null;

		List<VersementBanqueDTO> versementList = new ArrayList<VersementBanqueDTO>();

		for (VersementBanque c : creditsList) {
			VersementBanqueDTO vdto = new VersementBanqueDTO();

			vdto.setDateDeVersement(c.getDateVersement() + "");
			vdto.setMontantVersment(c.getMontantVersment());
			vdto.setLibellePersonneAuteurVersment(c.getAuteurVersement() != null
					? c.getAuteurVersement().getPrenom() + " " + c.getAuteurVersement().getNom() : "");
			vdto.setMoyenVersment(c.getMoyenVersement());
			vdto.setLibelleVersment(c.getLibelleVersment());
			vdto.setZone(c.getPointdeVente() != null ? c.getPointdeVente().getLibelle() : "");
			versementList.add(vdto);
		}
		return versementList;

	}

	@Override
	public List<VentesDTO> loadAllVentesByIdPointDeVente(Long idPoindDeVente, Long idProgramme) {

		@SuppressWarnings("unchecked")
		List<Ventes> ventesListes = (List<Ventes>) modelDAO.genericSqlClassLoaderById(Ventes.class, "pgca_ventes",
				"stock_id", idPoindDeVente);

		if (ventesListes == null)
			return null;

		List<VentesDTO> versementList = new ArrayList<VentesDTO>();

		for (Ventes v : ventesListes) {
			VentesDTO vdto = new VentesDTO();

			vdto.setMontantEncaisse(v.getMontantEncaisse());
			vdto.setClient(v.getClient());
			vdto.setDateVente(v.getDateDeVente() + "");
			vdto.setVendeurLibelle(v.getVendeur().getPrenom() != null
					? v.getVendeur().getPrenom() + " " + v.getVendeur().getNom() : "");
			vdto.setZoneVente(v.getZoneVente());

			versementList.add(vdto);
		}
		return versementList;
	}
	
	
	
	
	@Override
	public List<VentesDTO> loadAllVentesParPeriode(Date dateDebut, Date dateFin) throws EntityDBDAOException {

		@SuppressWarnings("unchecked")
		List<Ventes> ventesListes = (List<Ventes>) modelDAO.loadVentesParPeriode(dateDebut, dateFin);

		if (ventesListes == null)
			return null;

		List<VentesDTO> versementList = new ArrayList<VentesDTO>();

		for (Ventes v : ventesListes) {
			VentesDTO vdto = new VentesDTO();

			vdto.setMontantEncaisse(v.getMontantEncaisse());
			vdto.setClient(v.getClient());
			vdto.setDateVente(v.getDateDeVente() + "");
			vdto.setVendeurLibelle(v.getVendeur().getPrenom() != null
					? v.getVendeur().getPrenom() + " " + v.getVendeur().getNom() : "");
			vdto.setZoneVente(v.getZoneVente());

			versementList.add(vdto);
		}
		return versementList;
	}
	
	

	@Override
	public Map<String, Float> evaluerRepartitionCaisseByModePaiement(Long idPointdeVente) {
		@SuppressWarnings("unchecked")
		List<Ventes> ventes = (List<Ventes>) modelDAO.genericSqlClassLoaderById(Ventes.class, "pgca_ventes", "stock_id",
				idPointdeVente);

		if (ventes == null)
			return null;

		// List<Map<String, Float>> repartitionCaisse = new
		// ArrayList<Map<String,Float>>();

		Map<String, Float> caisseRepartition = new HashMap<String, Float>();
		float especes = 0;
		float bon = 0;
		float cheque = 0;

		for (Ventes v : ventes) {

			especes += v.getMontantPayeEnEspece();
			cheque += v.getMontantPayeEnCheque();
			bon += v.getMontantPayeEnBonDeSubvention();
		}

		caisseRepartition.put("ESPECES", especes);
		caisseRepartition.put("CHEQUE", cheque);
		caisseRepartition.put("BON", bon);
		return caisseRepartition;
	}

	@Override
	public float evaluerMontantTotalDepotBanqueByCampagne() {
		@SuppressWarnings("unchecked")
		List<VersementBanque> ventes = (List<VersementBanque>) modelDAO.genericClassLoader(VersementBanque.class);

		if (ventes == null)
			return 0;

		float totalVersement = 0;

		for (VersementBanque v : ventes) {
			totalVersement += v.getMontantVersment();
		}

		return totalVersement;
	}

	public UtilString getUtils() {
		return utils;
	}

	public void setUtils(UtilString utils) {
		this.utils = utils;
	}

	public ModelDAO getModelDAO() {
		return modelDAO;
	}

	public void setModelDAO(ModelDAO modelDAO) {
		this.modelDAO = modelDAO;
	}

	public void validate(ConnectionDTO connectionDTO) throws ConnectionException {

	}

	public ModelFactory getModelFactory() {
		return modelFactory;
	}

	public void setModelFactory(ModelFactory modelFactory) {
		this.modelFactory = modelFactory;
	}

	public DTOFactory getDtoFactory() {
		return dtoFactory;
	}

	public void setDtoFactory(DTOFactory dtoFactory) {
		this.dtoFactory = dtoFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AllocationDTO> loadAllAllocationByIdPointDeVente(Long idPoindCollecte, Long idProgramme)
			throws EntityDBDAOException {
		List<Allocation> listAvance = (List<Allocation>) modelDAO.genericSqlClassLoaderById(Allocation.class,
				"pgca_allocation", "pdc_id", idPoindCollecte);

		List<AllocationDTO> detailsCredits = new ArrayList<AllocationDTO>();

		for (Allocation c : listAvance) {
			AllocationDTO acdto = new AllocationDTO();

			acdto.setAlloc_id(c.getAlloc_id());
			acdto.setDateAllcation(c.getDateAllcation());
			acdto.setCollaborateurBeneficiare(c.getAuteurAllocation());
			acdto.setMontantalloue(c.getMontantalloue());
			acdto.setMontantUtilise(c.getMontantUtilise());
			acdto.setIdPointDecollecteBeneficiaire(c.getPointdeCollecteConcerne().getPdc_id());
			acdto.setPointdeCollecteBeneficiare(c.getPointdeCollecteConcerne().getLibelle());
			acdto.setAuteurAllocation(c.getAuteurAllocation());
			acdto.setCollaborateurBeneficiare(c.getCollaborateur() != null
					? c.getCollaborateur().getPrenom() + " " + c.getCollaborateur().getNom() : "");

			acdto.setIdIntrantACollecter(c.getProduitACollecter() != null ? c.getProduitACollecter().getId() : null);
			acdto.setIntranACollecter(c.getProduitACollecter() != null ? c.getProduitACollecter().getLibelle() : "");

			acdto.setProgramme(c.getProgrammeConcerne() != null ? c.getProgrammeConcerne().getLibelle() : null);
			acdto.setIdProgramme(c.getProgrammeConcerne() != null ? c.getProgrammeConcerne().getPgca_idprog() : null);
			acdto.setCampagneAgricole(c.getProgrammeConcerne() != null && c.getProgrammeConcerne().getCampagne() != null
					? c.getProgrammeConcerne().getCampagne().getLibelle() : "");

			// Collecte realiste sur une allocation
			List<CollecteProduits> lc = (List<CollecteProduits>) modelDAO.genericSqlClassLoaderById(
					CollecteProduits.class, "pgca_collecteProduits", "alloc_id", acdto.getAlloc_id());

			if (lc != null) {
				Long totalcollecte = 0L;

				for (CollecteProduits cc : lc)
					totalcollecte += cc.getProducteurPoidsTotal();
				acdto.setTotalCollecte(totalcollecte);
			}

			detailsCredits.add(acdto);

		}

		return detailsCredits;
	}

	@Override
	public List<AllocationDTO> loadAllAllocation(Long idProgramme) throws EntityDBDAOException {

		List<Allocation> listAvance = (List<Allocation>) modelDAO.genericClassLoader(Allocation.class);
		List<AllocationDTO> detailsCredits = new ArrayList<AllocationDTO>();

		for (Allocation c : listAvance) {
			AllocationDTO acdto = new AllocationDTO();

			acdto.setAlloc_id(c.getAlloc_id());
			acdto.setDateAllcation(c.getDateAllcation());
			acdto.setCollaborateurBeneficiare(c.getAuteurAllocation());
			acdto.setMontantalloue(c.getMontantalloue());
			acdto.setIdPointDecollecteBeneficiaire(c.getPointdeCollecteConcerne().getPdc_id());
			acdto.setPointdeCollecteBeneficiare(c.getPointdeCollecteConcerne().getLibelle());
			acdto.setAuteurAllocation(c.getAuteurAllocation());
			acdto.setCollaborateurBeneficiare(c.getCollaborateur() != null
					? c.getCollaborateur().getPrenom() + " " + c.getCollaborateur().getNom() : "");

			acdto.setIdIntrantACollecter(c.getProduitACollecter() != null ? c.getProduitACollecter().getId() : null);
			acdto.setIntranACollecter(c.getProduitACollecter() != null ? c.getProduitACollecter().getLibelle() : "");

			acdto.setProgramme(c.getProgrammeConcerne() != null ? c.getProgrammeConcerne().getLibelle() : null);
			acdto.setIdProgramme(c.getProgrammeConcerne() != null ? c.getProgrammeConcerne().getPgca_idprog() : null);
			acdto.setCampagneAgricole(c.getProgrammeConcerne() != null && c.getProgrammeConcerne().getCampagne() != null
					? c.getProgrammeConcerne().getCampagne().getLibelle() : "");

			detailsCredits.add(acdto);
		}

		return detailsCredits;
	}

	/*********************** CHEQUE SERVICES *****************************/
	@Override
	public boolean enregistreCheque(ChequeDTO dto) {

		try {

			Cheque cheque = new Cheque();

			if (dto.getIdVenteConcerne() != null) {
				Ventes v = (Ventes) modelDAO.getEntityDBById(dto.getIdVenteConcerne(), Ventes.class);
				cheque.setVenteConcerne(v);
			}

			if (dto.getIdavanceConcerne() != null) {
				AvanceSurCredit c = (AvanceSurCredit) modelDAO.getEntityDBById(dto.getIdavanceConcerne(),
						AvanceSurCredit.class);
				cheque.setAvanceConcerne(c);
				// cheque.setDescription("Ch");
			}

			cheque.setStatut(0);
			cheque.setNumeroCheque(dto.getNumeroCheque());
			cheque.setBanqueEmetrice(dto.getBanqueEmetrice());
			cheque.setMontantCheque(dto.getMontantCheque());
			cheque.setDescription(dto.getDescription());

			PointDeVente pv = (PointDeVente) modelDAO.getEntityDBById(dto.getIdVenteConcerne(), PointDeVente.class);

			cheque.setPointDeVente(pv);

			Date datejour = new Date();
			SimpleDateFormat dt1 = new SimpleDateFormat("dd-mm-yyyy");
			cheque.setDateEdition(datejour);

			modelDAO.save(cheque);

			return true;
		} catch (Exception e) {
			LOG.error("Une erreur est surveneur lors de l'enregistrement du chéque");
			return false;
		}

	}

	@Override
	public List<ChequeDTO> recupererChequeByIdPointDeVente(Long idPointdeVente) {
		@SuppressWarnings("unchecked")
		List<Cheque> listCheque = (List<Cheque>) modelDAO.genericSqlClassLoaderById(Cheque.class, "pgca_cheque",
				"ptv_id", idPointdeVente);

		if (listCheque == null)
			return null;

		List<ChequeDTO> lpdto = new ArrayList<ChequeDTO>();

		for (Cheque v : listCheque) {
			ChequeDTO nv = new ChequeDTO();

			nv.setId_cheque(v.getId_cheque());
			nv.setBanqueEmetrice(v.getBanqueEmetrice());
			nv.setDateEdition(v.getDateEdition());
			nv.setDateEncaissement(nv.getDateEncaissement());
			nv.setDescription(v.getDescription());
			nv.setIdPointdeVente(v.getPointDeVente() != null ? v.getPointDeVente().getPtv_id() : null);
			if (v.getStatut() == 0)
				nv.setLibelleStatut(ConstantPGCA.CHEQUE_A_ENCAISSER_Libelle);
			else if (v.getStatut() == 1)
				nv.setLibelleStatut(ConstantPGCA.CHEQUE_ENCAISSER_LIbelle);
			nv.setMontantCheque(v.getMontantCheque());
			nv.setStatut(v.getStatut());
			nv.setNumeroCheque(v.getNumeroCheque());

			lpdto.add(nv);
		}

		return lpdto;
	}

	@Override
	public List<ChequeDTO> recupererAllCheques() {
		@SuppressWarnings("unchecked")
		List<Cheque> lv = (List<Cheque>) modelDAO.genericClassLoader(Cheque.class);

		if (lv == null)
			return null;

		List<ChequeDTO> lpdto = new ArrayList<ChequeDTO>();

		for (Cheque v : lv) {
			ChequeDTO nv = new ChequeDTO();

			nv.setId_cheque(v.getId_cheque());
			nv.setBanqueEmetrice(v.getBanqueEmetrice());
			nv.setDateEdition(v.getDateEdition());

			if (v.getPointDeVente() != null)
				nv.setPointdevente(v.getPointDeVente().getLibelle());

			nv.setDateEncaissement(nv.getDateEncaissement());
			nv.setDescription(v.getDescription());
			nv.setIdPointdeVente(v.getPointDeVente() != null ? v.getPointDeVente().getPtv_id() : null);
			if (v.getStatut() == 0)
				nv.setLibelleStatut(ConstantPGCA.CHEQUE_A_ENCAISSER_Libelle);
			else if (v.getStatut() == 1)
				nv.setLibelleStatut(ConstantPGCA.CHEQUE_ENCAISSER_LIbelle);
			nv.setMontantCheque(v.getMontantCheque());
			nv.setNumeroCheque(v.getNumeroCheque());
			nv.setStatut(v.getStatut());

			lpdto.add(nv);
		}

		return lpdto;
	}

	@Override
	public boolean encaisseCheque(Long id_cheque) throws EntityDBDAOException {
		Cheque c = (Cheque) modelDAO.getEntityDBById(id_cheque, Cheque.class);

		if (c == null)
			return false;

		c.setStatut(ConstantPGCA.CHEQUE_ENCAISSER);
		modelDAO.save(c);
		return true;

	}

}