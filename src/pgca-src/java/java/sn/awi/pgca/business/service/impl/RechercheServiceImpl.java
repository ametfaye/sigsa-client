package sn.awi.pgca.business.service.impl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.jfree.util.Log;

import sn.awi.pgca.business.dao.ModelDAO;
import sn.awi.pgca.business.service.IRechercheService;
import sn.awi.pgca.dataModel.MiseEnPlaceEffectuer;
import sn.awi.pgca.dataModel.VersementBanque;
import sn.awi.pgca.web.dto.MiseEnplaceDTOPointDeVente;
import sn.awi.pgca.web.dto.RechercheMiseEnPlaceDTO;
import sn.awi.pgca.web.dto.VersementBanqueDTO;

@Named("rechercheService")
public class RechercheServiceImpl implements IRechercheService , Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7149984825476878583L;
	@Inject
	private ModelDAO modelDAO;

	
	@Override
	public List<MiseEnplaceDTOPointDeVente> rechercherMiseEnplace(RechercheMiseEnPlaceDTO dto) {
		try {
			List<MiseEnPlaceEffectuer> mepeffectuees = modelDAO.rechercherMiseEnplace(dto);
			List<MiseEnplaceDTOPointDeVente> listeMEP = new ArrayList<MiseEnplaceDTOPointDeVente>();
			for (MiseEnPlaceEffectuer m : mepeffectuees) {
				MiseEnplaceDTOPointDeVente nv = new MiseEnplaceDTOPointDeVente();			
				nv.setLibelleIntrantAMettreEnplace(m.getMiseEnplaceConcerne().getProduitAmettreEnPlace().getLibelle());
				nv.setReliquatIntrantAMettreEnplace(m.getMiseEnplaceConcerne().getReliquat());
				nv.setQuotaIntrantAMettreEnplace(m.getMiseEnplaceConcerne().getQuota());
	
				nv.setIdCamion(m.getCamion().getId());
				listeMEP.add(nv);	
			}	
			return listeMEP;
		} catch (Exception e) {
			Log.error("Erreur recherche des mise en place" + e.getMessage());
			return null;
		}	
	}
	
	
	
	@Override
	public List<MiseEnplaceDTOPointDeVente> rechercherMiseEnplaceEffectueParIntrant(RechercheMiseEnPlaceDTO dtot) {
		try {
			List<MiseEnPlaceEffectuer> mepeffectuees = modelDAO.rechercherMiseEnplaceEffectueParIntrant( dtot );
			List<MiseEnplaceDTOPointDeVente> listeMEP = new ArrayList<MiseEnplaceDTOPointDeVente>();
			
			for (MiseEnPlaceEffectuer m : mepeffectuees) {
				MiseEnplaceDTOPointDeVente nv = new MiseEnplaceDTOPointDeVente();			
				
				nv.setIdDepartement(m.getMiseEnplaceConcerne().getPointdeVenteConcerne().getDepartement().getId());
				nv.setDepartement(m.getMiseEnplaceConcerne().getPointdeVenteConcerne().getDepartement().getLibelle());
				nv.setIdCommuneResiduel(m.getMiseEnplaceConcerne().getPointdeVenteConcerne().getId());
				nv.setRegion(m.getMiseEnplaceConcerne().getPointdeVenteConcerne().getDepartement().getRegion().getLibelle());
				nv.setLibelleIntrantAMettreEnplace(m.getMiseEnplaceConcerne().getProduitAmettreEnPlace().getLibelle());
				nv.setReliquatIntrantAMettreEnplace(m.getMiseEnplaceConcerne().getReliquat());
				nv.setQuotaIntrantAMettreEnplace(m.getMiseEnplaceConcerne().getQuota());
				nv.setQuantiteIntrantAMettreEnplace(m.getQuantiteAmettreEnplace());
	
				nv.setIdCamion(m.getCamion().getId());
				nv.setCamion(m.getCamion().getNumeroCamion() + "");
				nv.setChauffeur(m.getChauffeur().getChauffeur().getPrenom() + " "  + m.getChauffeur().getChauffeur().getNom());	
				nv.setTransporteur(m.getTransporteur().getLibelle());
				nv.setFournisseur(m.getFounnisseur().getLibelle());
				nv.setDateMiseEnplace(m.getDateMiseEnplaceEffective() + "");
				nv.setNomPointDeVente(m.getPointdeVenteLibelle());
				//nv.setDepartementPointdeVente(m.getMiseEnplaceConcerne().getPointdeVenteConcerne().getAdresse().getDepartement().getLibelle());
				//nv.setNomGerant(m.getMiseEnplaceConcerne().getPointdeVenteConcerne().getGerant().getPrenom() + " " + m.getMiseEnplaceConcerne().getPointdeVenteConcerne().getGerant().getNom());
				
				nv.setBlMiseEnPlace(m.getBlManuel());
				nv.setLvMiseEnPlace(m.getLvManuel());
				nv.setFournisseur(m.getFounnisseur().getLibelle());
				nv.setNomProgramme(m.getProgrammeConcerne().getLibelle());
				
				if(m.getDateMiseEnplaceEffective()  !=  null)
				{
					SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy"); // HH:mm:ss
					nv.setDateMEPSTR(df.format(m.getDateMiseEnplaceEffective()));
				}
				listeMEP.add(nv);
			}	
			return listeMEP;
		} catch (Exception e) {
			Log.error("Erreur recherche des mise en place" + e.getMessage());
			return null;
		}	
	}
	
	
	
	
	




}
