package org.cages.moulinette.managedBean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.cages.moulinette.dto.CampagneAgricoleDTO;
import org.cages.moulinette.dto.IntrantDTO;
import org.cages.moulinette.dto.ProgrammeAgricolDTO;
import org.cages.moulinette.dto.UserInfosDTO;
import org.cages.moulinette.service.ICampagneAgricoleServices;
import org.cages.moulinette.service.IUserService;
import org.cages.moulinette.service.IntrantService;
import org.cages.moulinette.utils.SigsaCONSTANTES;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;

@ManagedBean(name = "CampagneAgricoleController")
@SessionScoped
@Getter
@Setter
public class CampagneAgricoleController {

	private CampagneAgricoleDTO campagneAcreer;
	private ProgrammeAgricolDTO programmeAcreer;
	private UserInfosDTO connectedUserDetails = new UserInfosDTO();
	private List<CampagneAgricoleDTO> listCampagne; // ouvertes
	private List<IntrantDTO> intrantsDTO; // intants actifs

	@ManagedProperty(value = "#{intrantService}")
	private IntrantService intrantService;

	private Logger logger = LoggerFactory.getLogger(CampagneAgricoleController.class);
	private String blocErreurMsg;

	@ManagedProperty(value = "#{campagneServices}")
	private ICampagneAgricoleServices campagneServices;
	
	@ManagedProperty(value = "#{userService}")
	private IUserService userService;

	@PostConstruct
	private void init() {
		campagneAcreer = new CampagneAgricoleDTO();
		connectedUserDetails = retrievedetailsConnectedUser();
		listCampagne  = campagneServices.loadCampagneByStatus(SigsaCONSTANTES.CampagneSTATUS_OUVERT);
	}

	public String ouvrirCampagneCheckErrors() {
		if (campagneAcreer.getDateOuverture().trim().equals("")) {
			logger.error("La date d'ouverture de la camapgne est obligatoire");
			return "pgcaCampagneAgricoleEdition.xhtml";
		}

		if (campagneAcreer.getDateFermeture().trim().equals("")) {
			logger.error("La date de fermeture de la camapgne est obligatoire");
			return "pgcaCampagneAgricoleEdition.xhtml";
		}

		logger.info("Controller : Aucune erreur :  tentative de création ");
		return ouvrirCampagne();

	}

	private String ouvrirCampagne() {
		try {
			logger.info("Controller : Création campagne ...");
			campagneServices.ouvertureCampagneAgricole(campagneAcreer);
			logger.info("Controller : Campagne créé avec success  ...");
		} catch (Exception e) {
			logger.error("Controller :  Erreur survenue lors de la création  ...");
		}
		return "pgcaCampagneAgricoleList.xhtml";
	}

	public List<CampagneAgricoleDTO> loadListCampagne() {
		try {
			logger.info("Controller : Recuperation liste des campagnes ...");
			if (listCampagne != null)
				return listCampagne;

			listCampagne = campagneServices.loadListCampagneAgricole();
		} catch (Exception e) {
			logger.error("Controller :  Erreur survenue lors de la création  ...");
		}
		return listCampagne;
	}

	
	
	/*****  PROGRAMME SERVICES *****/
	
	public String ouvrirProgramme() {
		try {
			logger.info("Controller : Création campagne ...");
			campagneServices.ouvertureCampagneAgricole(campagneAcreer);
			logger.info("Controller : Campagne créé avec success  ...");
		} catch (Exception e) {
			logger.error("Controller :  Erreur survenue lors de la création  ...");
		}
		return "pgcaCampagneAgricoleEdition.xhtml";
	}

	public List<CampagneAgricoleDTO> loadListProgramme() {
		try {
			logger.info("Controller : Recuperation liste des campagnes ...");
			if (listCampagne != null)
				return listCampagne;

			listCampagne = campagneServices.loadListCampagneAgricole();
		} catch (Exception e) {
			logger.error("Controller :  Erreur survenue lors de la création  ...");
		}
		return listCampagne;
	}
	
	
	
	public List<IntrantDTO>  loadIntrant() {
		if(intrantsDTO == null || intrantsDTO.isEmpty());
		intrantsDTO  = intrantService.getAllIntrants();
		return intrantsDTO;
	}
	

	
	public void retirerAgentNew() {
		//
	}
	
	
	
	public UserInfosDTO retrievedetailsConnectedUser() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userService.findUserByEmail(user.getUsername());
	}
}
