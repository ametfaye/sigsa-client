package sn.awi.pgca.business.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.jfree.util.Log;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sn.awi.pgca.business.dao.ModelDAO;
import sn.awi.pgca.business.exception.EntityDBDAOException;
import sn.awi.pgca.business.exception.ProgrammeException;
import sn.awi.pgca.business.service.IMagasinierService;
import sn.awi.pgca.dataModel.Intrant;
import sn.awi.pgca.dataModel.OrdreLivraison;
import sn.awi.pgca.web.bean.ConstantPGCA;
import sn.awi.pgca.web.dto.CommandeDTO;
import sn.awi.pgca.web.dto.ProduitDTO;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
@Named("magasinierService")
public class MagasinierServiceImpl implements IMagasinierService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7426629362613903323L;
	/**
	 * DAO pour la persistance.
	 */
	@Inject
	private ModelDAO modelDAO;

	/*************** S T O C K S E R V I C E S ********************************/

	public ProduitDTO loadProduitDTObyIdProduit(Long idProdui) throws ProgrammeException, EntityDBDAOException {
		Intrant tp = (Intrant) modelDAO.getEntityDBById(idProdui, Intrant.class);

		if (tp == null) {
			Log.error("Impossible de recuperer le produit avec l'id <" + idProdui + ">");
			return null;
		}

		ProduitDTO idto = new ProduitDTO();
		idto.setIdProduit(tp.getProduit_id());
		idto.setIdtypeProduit(tp.getIntrant().getTypeIntrantId().getId());
		idto.setIdStockProduit(tp.getStockRef().getId());
		idto.setIdCampagne(tp.getProgramme().getId());
		idto.setLibelleProduit(tp.getIntrant().getLibelle());
		idto.setLibelleProgramme(tp.getProgramme().getLibelle());
		idto.setLibelleCampagne(tp.getProgramme().getCampagne().getLibelle());
		idto.setLibellePointdeStock(tp.getStockRef().getLibelle());
		idto.setQuantite(tp.getQuantite());
		idto.setTypeProduit(tp.getIntrant().getTypeIntrantId().getLibelle());
		idto.setProvenance(tp.getProvenance());
		idto.setPictoImages(tp.getIntrant().getTypeIntrantId().getPictoIntrant());
		
		// Tarif
		if (tp.getTarif() != null) {
			idto.setPrixUnitaire(tp.getTarif().getPrixNonSubventionne());
		}

		return idto;

	}

	@Override
	public List<ProduitDTO> loadStockProduitByIdPointdeVenteMagasinier(Long idStockPointDeVente)
			throws EntityDBDAOException {
		List<Intrant> allstock = modelDAO.loadStockByIdPointdeVente(idStockPointDeVente);

		if (allstock == null)
			return null;

		List<ProduitDTO> allstockDTO = new ArrayList<ProduitDTO>();

		for (Intrant p : allstock) {
			ProduitDTO dtoP = new ProduitDTO();

			dtoP.setIdProduit(p.getProduit_id());
			dtoP.setLibelle(p.getIntrant().getLibelle());
			dtoP.setLibelleProgramme(p.getProgramme().getLibelle());
			
			Double q = BigDecimal.valueOf(p.getQuantite())
				    .setScale(2, RoundingMode.HALF_UP)
				    .doubleValue();
			
			dtoP.setQuantite(q);
			dtoP.setIdtypeProduit(p.getIntrant().getTypeIntrantId().getTypeIntrantId());
			dtoP.setIdCampagne(p.getProgramme().getCampagne().getId_ca());
			dtoP.setLibelleProgramme(p.getProgramme().getCampagne().getLibelle());

			dtoP.setIdProgramme(p.getProgramme().getId_ca());
			dtoP.setLibelleProgramme(p.getProgramme().getLibelle());

			if (p.getTarif() != null && p.getTarif().getPrixProducteur() != null) {
				dtoP.setPrixUnitaire(p.getTarif().getPrixProducteur());
				dtoP.setSubvention(p.getTarif().getTauxSubvention());
				
				if(p.getTarif().getAuteurTarification().getId() != null)
					dtoP.setAuteurCreationIntrant(p.getTarif().getAuteurTarification().getId());
				
			} else 
				dtoP.setPrixUnitaire(0f);
			
			

			allstockDTO.add(dtoP);
		}

		return allstockDTO;
	}

	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CommandeDTO> getAllOrdresDelivraisons(Long idPointDeVente) {
		List<OrdreLivraison> cmdList = (List<OrdreLivraison>) modelDAO.loadOLenAttenteDeTraitementByPointDeVente(idPointDeVente);
		if (cmdList == null)
			return null;

		List<CommandeDTO> cmddto = new ArrayList<CommandeDTO>();

		for (OrdreLivraison c : cmdList) {
			CommandeDTO dto = new CommandeDTO();

			dto.setIdCommande(c.getOrd_id());
			dto.setIdOrdre(c.getOrd_id());
			dto.setReferenceCMD(c.getReferenceOrdre());
			dto.setDateLivraisonSouhaite(c.getDateLivraisonSouhaiteOrdre());
			dto.setStatutCMD(c.getStatutOrd() + "");
			dto.setAuteurCommandeNom(
					c.getEditeurCommandePersonne().getPrenom() + c.getEditeurCommandePersonne().getNom());
			dto.setClientNom(c.getClientNom());
			dto.setClientTel(c.getClientTel());
			dto.setClienAdresse(c.getClientAddresse());
			dto.setCommuneLibelle(c.getClientCommuneDechargement());
			dto.setClientCNI(c.getNumeroCNIClient());

			if (c.getStatutOrd() == 0) {
				dto.setStatusLibelle(ConstantPGCA.ORD_A_TRAITER_LIBELLE);
				dto.setStyleClassCSSstatusCMD(ConstantPGCA.CMD_STATUT_CLASS_EN_COURS);
				dto.setIconFontAwesome(ConstantPGCA.ICON_ENCOURS);
			} else if (c.getStatutOrd() == 1) {
				dto.setStatusLibelle(ConstantPGCA.ORD_TRAITE_LIBELLE);
				dto.setIconFontAwesome(ConstantPGCA.ICON_TRAITE);
				dto.setStyleClassCSSstatusCMD(ConstantPGCA.CMD_STATUT_CLASS_TRAITE);

			} else if (c.getStatutOrd() == 2) {
				dto.setStatusLibelle(ConstantPGCA.ORD_REFUSE_LIBELLE);
				dto.setIconFontAwesome(ConstantPGCA.ICON_REFUSE);
				dto.setStyleClassCSSstatusCMD(ConstantPGCA.CMD_STATUT_CLASS_REFUSE);

			}
			else if (c.getStatutOrd() == 4) {
				dto.setStatusLibelle(ConstantPGCA.ORD_VALIDE_LIBELLE);
				dto.setIconFontAwesome(ConstantPGCA.ICON_TRAITE);
				dto.setStyleClassCSSstatusCMD(ConstantPGCA.CMD_STATUT_CLASS_VALIDE);

			}
			cmddto.add(dto);
		}
		return cmddto;
	}

	
	/*** tous les ordes valide **/
	@SuppressWarnings("unchecked")
	@Override
	public List<CommandeDTO> getAllOrdresValideByIdPointDeVente(Long idPointDeVente) {
		List<OrdreLivraison> cmdList = (List<OrdreLivraison>) modelDAO.loadOLValidesDeTraitementByPointDeVente(idPointDeVente);
		if (cmdList == null)
			return null;

		List<CommandeDTO> cmddto = new ArrayList<CommandeDTO>();

		for (OrdreLivraison c : cmdList) {
			CommandeDTO dto = new CommandeDTO();

			dto.setIdCommande(c.getOrd_id());
			dto.setIdOrdre(c.getOrd_id());
			dto.setReferenceCMD(c.getReferenceOrdre());
			dto.setDateLivraisonSouhaite(c.getDateLivraisonSouhaiteOrdre());
			dto.setStatutCMD(c.getStatutOrd() + "");
			dto.setAuteurCommandeNom(
					c.getEditeurCommandePersonne().getPrenom() + c.getEditeurCommandePersonne().getNom());
			dto.setClientNom(c.getClientNom());
			dto.setClientTel(c.getClientTel());
			dto.setClienAdresse(c.getClientAddresse());
			dto.setCommuneLibelle(c.getClientCommuneDechargement());
			dto.setClientCNI(c.getNumeroCNIClient());

			if (c.getStatutOrd() == 0) {
				dto.setStatusLibelle(ConstantPGCA.ORD_A_TRAITER_LIBELLE);
				dto.setStyleClassCSSstatusCMD(ConstantPGCA.CMD_STATUT_CLASS_EN_COURS);
				dto.setIconFontAwesome(ConstantPGCA.ICON_ENCOURS);
			} else if (c.getStatutOrd() == 1) {
				dto.setStatusLibelle(ConstantPGCA.ORD_TRAITE_LIBELLE);
				dto.setIconFontAwesome(ConstantPGCA.ICON_TRAITE);
				dto.setStyleClassCSSstatusCMD(ConstantPGCA.CMD_STATUT_CLASS_TRAITE);

			} else if (c.getStatutOrd() == 2) {
				dto.setStatusLibelle(ConstantPGCA.ORD_REFUSE_LIBELLE);
				dto.setIconFontAwesome(ConstantPGCA.ICON_REFUSE);
				dto.setStyleClassCSSstatusCMD(ConstantPGCA.CMD_STATUT_CLASS_REFUSE);

			}
			else if (c.getStatutOrd() == 4) {
				dto.setStatusLibelle(ConstantPGCA.ORD_VALIDE_LIBELLE);
				dto.setIconFontAwesome(ConstantPGCA.ICON_TRAITE);
				dto.setStyleClassCSSstatusCMD(ConstantPGCA.CMD_STATUT_CLASS_VALIDE);

			}
			cmddto.add(dto);
		}
		return cmddto;
	}

	
	
	
	@Override
	public List<CommandeDTO> getAllOrdresDelivraisonsEnAttente(Long idPointDeVente) {
		@SuppressWarnings("unchecked")
		List<OrdreLivraison> cmdList = (List<OrdreLivraison>) modelDAO.loadOLenAttenteDeTraitementByPointDeVente(idPointDeVente);

		if (cmdList == null)
			return null;

		List<CommandeDTO> cmddto = new ArrayList<CommandeDTO>();

		for (OrdreLivraison c : cmdList) {
			CommandeDTO dto = new CommandeDTO();

			dto.setIdCommande(c.getOrd_id());
			dto.setIdOrdre(c.getOrd_id());
			dto.setReferenceCMD(c.getReferenceOrdre());
			dto.setDateLivraisonSouhaite(c.getDateLivraisonSouhaiteOrdre());
			dto.setStatutCMD(c.getStatutOrd() + "");
			dto.setAuteurCommandeNom(
					c.getEditeurCommandePersonne().getPrenom() + c.getEditeurCommandePersonne().getNom());
			dto.setClientNom(c.getClientNom());
			dto.setClientTel(c.getClientTel());
			dto.setClienAdresse(c.getClientAddresse());
			dto.setCommuneLibelle(c.getClientCommuneDechargement());
			dto.setClientCNI(c.getNumeroCNIClient());

			if (c.getStatutOrd() == 0) {
				dto.setStatusLibelle(ConstantPGCA.ORD_A_TRAITER_LIBELLE);
				dto.setIconFontAwesome(ConstantPGCA.ICON_ENCOURS);
			} else if (c.getStatutOrd() == 1) {
				dto.setStatusLibelle(ConstantPGCA.ORD_TRAITE_LIBELLE);
				dto.setIconFontAwesome(ConstantPGCA.ICON_TRAITE);

			} else if (c.getStatutOrd() == 2) {
				dto.setStatusLibelle(ConstantPGCA.ORD_REFUSE_LIBELLE);
				dto.setIconFontAwesome(ConstantPGCA.ICON_REFUSE);
			}
			else if (c.getStatutOrd() == 4) {
				dto.setStatusLibelle(ConstantPGCA.ORD_TRAITE_LIBELLE);
				dto.setIconFontAwesome(ConstantPGCA.ICON_TRAITE);
			}
			cmddto.add(dto);
		}
		return cmddto;
	}

	
	
	@Override
	public boolean traiterOrdreDeLivraion(Long idCommande) throws EntityDBDAOException {
		OrdreLivraison o  = (OrdreLivraison) modelDAO.getEntityDBById(idCommande, OrdreLivraison.class);
		
		try {
			o.setStatutOrd(ConstantPGCA.ORD_TRAITE);
			
			modelDAO.save(o);
			return true;
			 
		} catch (Exception e) {
			Log.error("Erreur survenue lors du traitement de l'ordre de loivraion");
		}
	
	return false;
	}
	
	
	
	
	@Override
	public boolean enregistrerVenteByIdPointdeVenteMagasinier(ProduitDTO produit, Long idStock)
			throws EntityDBDAOException {
		// TODO Auto-generated method stub
		return false;
	}

	public ModelDAO getModelDAO() {
		return modelDAO;
	}

	public void setModelDAO(ModelDAO modelDAO) {
		this.modelDAO = modelDAO;
	}



	/***************
	 * E-N-D I N T R A N T S DES C A M P A G N E S
	 ***************************/

}
