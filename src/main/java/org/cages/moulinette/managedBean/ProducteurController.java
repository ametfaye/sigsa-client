package org.cages.moulinette.managedBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpServletRequest;

import org.cages.moulinette.dto.AgentDTO;
import org.cages.moulinette.dto.PersonnePhysiqueDTO;
import org.cages.moulinette.dto.ProducteurDTO;
import org.cages.moulinette.dto.UserInfosDTO;
import org.cages.moulinette.model.Agent;
import org.cages.moulinette.model.Fonction;
import org.cages.moulinette.model.Groupe;
import org.cages.moulinette.model.PersonnePhysique;
import org.cages.moulinette.model.Producteur;
import org.cages.moulinette.model.Service;
import org.cages.moulinette.model.SousService;
import org.cages.moulinette.service.IAgentService;
import org.cages.moulinette.service.IPersonnePhysiqueService;
import org.cages.moulinette.service.IProducteurService;
import org.cages.moulinette.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;

@ManagedBean(name = "ProducteurController")
@SessionScoped
@Getter
@Setter
public class ProducteurController {

	private Logger logger = LoggerFactory.getLogger(ProducteurController.class);

	private UserInfosDTO connectedUserDetails = new UserInfosDTO();
	private List<PersonnePhysiqueDTO> personnePhysiqueDTOs = new ArrayList<>();
	private List<PersonnePhysique> personnePhysiques = new ArrayList<>();
	private List<PersonnePhysiqueDTO> selectedPphs = new ArrayList<>();

	private ProducteurDTO producteurAcrer = new ProducteurDTO();
	private PersonnePhysiqueDTO pphToCreateDto = new PersonnePhysiqueDTO();

	private Date dateImmatriculation;
	HttpServletRequest request;
	Integer nbrOrdreATraiter;
	Long pphIdAdd;
	String matriculeUp;
	private String blocErreurMsg, blocConfirmationMsg;
	private String typeProducteur = "PersonneMorale";
	private Long destinationListDTOSelectedId;

	@ManagedProperty(value = "#{userService}")
	private IUserService userService;

	@ManagedProperty(value = "#{agentService}")
	private IAgentService agentService;

	@ManagedProperty(value = "#{personnePhysiqueService}")
	private IPersonnePhysiqueService personnePhysiqueService;

	@ManagedProperty(value = "#{producteurService}")
	private IProducteurService producteurService;

	@PostConstruct
	private void initConfiguration() {
		resetErrorMsgs();
		connectedUserDetails = retrievedetailsConnectedUser();
		personnePhysiqueDTOs = personnePhysiqueService.getAllPersonnePhysiques();
		producteurAcrer = new ProducteurDTO();
	}

	public UserInfosDTO retrievedetailsConnectedUser() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userService.findUserByMatricule(user.getUsername());
	}
	
	public String selectTypeProducteur() {
	  if(typeProducteur.equals("PersonneMorale")){
		typeProducteur = "PersonneMorale";
	  } else{
		  typeProducteur = "PersonnePhysique";
	  }
	  return "adminDistributeur.xhtml?faces-redirect=true";
	}

	public void annulerProducteur() {
		producteurAcrer = new ProducteurDTO();
		pphToCreateDto = new PersonnePhysiqueDTO();
		selectedPphs = new ArrayList<>();
		personnePhysiques = new ArrayList<>();
	}
	
	public void annulerCreationPph() {
		pphToCreateDto = new PersonnePhysiqueDTO();
	}
	
	public String createPph() {
		setBlocErreurMsg(null);
		
		if ("".equals(pphToCreateDto.getNom())) {
			blocErreurMsg = "Le nom est obligatoire";
			return "adminDistributeur.xhtml?faces-redirect=true";
		}
		
		if ("".equals(pphToCreateDto.getPrenom())) {
			blocErreurMsg = "Le prénom est obligatoire";
			return "adminDistributeur.xhtml?faces-redirect=true";
		}
		
		if ("".equals(pphToCreateDto.getEmail())) {
			blocErreurMsg = "L'email est obligatoire";
			return "adminDistributeur.xhtml?faces-redirect=true";
		}
		
		if ("".equals(pphToCreateDto.getSexe())) {
			blocErreurMsg = "Le sexe est obligatoire";
			return "adminDistributeur.xhtml?faces-redirect=true";
		}
		
		if ("".equals(pphToCreateDto.getTelephone())) {
			blocErreurMsg = "Le numéro de téléphone est obligatoire";
			return "adminDistributeur.xhtml?faces-redirect=true";
		}
					
		PersonnePhysique personnePhysique = personnePhysiqueService.findByEmail(pphToCreateDto.getEmail());
		if (personnePhysique != null) {
			setBlocErreurMsg("Le représentant "+personnePhysique.getPrenom()+" "+personnePhysique.getNom()+" existe déjà !");
			return "orderEdition.xhtml?faces-redirect=true&idBlocToShow=1";
		} else {
			PersonnePhysique personnePhysiqueToCreate = new PersonnePhysique(pphToCreateDto.getPrenom(), pphToCreateDto.getNom(),
									pphToCreateDto.getEmail(), pphToCreateDto.getTelephone(), pphToCreateDto.getSexe());
			PersonnePhysique pphCreated = personnePhysiqueService.createPersonnePhysique(personnePhysiqueToCreate);
			setBlocConfirmationMsg("Le représentant "+pphCreated.getPrenom()+" "+pphCreated.getNom()+" est créé avec succès");
		
			personnePhysiques.add(pphCreated);
			selectedPphs.add(new PersonnePhysiqueDTO(pphCreated.getPphId(), pphCreated.getPrenom(), pphCreated.getNom(), pphCreated.getEmail()));
			
		}
		pphToCreateDto = new PersonnePhysiqueDTO();
		personnePhysiqueDTOs = personnePhysiqueService.getAllPersonnePhysiques();
		return "adminDistributeur.xhtml?faces-redirect=true";
	}

	public void ajouterRepresentant() {
		setBlocErreurMsg(null);
		if (null != pphIdAdd && pphIdAdd != 0L) {
			PersonnePhysique personnePhysique = personnePhysiqueService.getPersonnePhysiqueById(pphIdAdd);
			PersonnePhysiqueDTO personnePhysiqueDTO = personnePhysiqueService.getPersonnePhysiqueDtoById(pphIdAdd);
			boolean agentExiste = false;
			for (int i = 0; i < selectedPphs.size(); i++) {
				PersonnePhysiqueDTO pDto = selectedPphs.get(i);
				if (pDto.getPphId() == pphIdAdd) {
					agentExiste = true;
					blocErreurMsg = "Le représentant existe déjà dans la liste";
					break;
				}
			}
			if (null != personnePhysique && agentExiste == false) {
				personnePhysiques.add(personnePhysique);
				selectedPphs.add(personnePhysiqueDTO);
				pphIdAdd = null;
			}
		}
	}

	public void retirerRepresentant(Long id) {
		setBlocErreurMsg(null);
		int indexselectedPphs = -1;
		int indexselectedPersonnePhysique = -1;
		for (int i = 0; i < selectedPphs.size(); i++) {
			PersonnePhysiqueDTO personnePhysiqueDTO = selectedPphs.get(i);
			if (personnePhysiqueDTO.getPphId() == id) {
				indexselectedPphs = i;
				break;
			}
		}
		if (indexselectedPphs != -1){
			selectedPphs.remove(indexselectedPphs);
		}
		for (int i = 0; i < personnePhysiques.size(); i++) {
			PersonnePhysique personnePhysique = personnePhysiques.get(i);
			if (personnePhysique.getPphId() == id) {
				indexselectedPersonnePhysique = i;
				break;
			}
		}
		if (indexselectedPersonnePhysique != -1){
			personnePhysiques.remove(indexselectedPersonnePhysique);
		}
		personnePhysiqueService.delete(id);
	}
	
	public String saveProducteur() throws ParseException {
		resetErrorMsgs();

		if ("".equals(producteurAcrer.getNumNinea())) {
			blocErreurMsg = "Le numéro NINEA est obligatoire";
			return "adminDistributeur.xhtml?faces-redirect=true";
		}

		if ("".equals(producteurAcrer.getDateImmatriculation()) || producteurAcrer.getDateImmatriculation() == null) {
			blocErreurMsg = "La date d'immatriculation est obligatoire";
			return "adminDistributeur.xhtml?faces-redirect=true";
		} else {
			dateImmatriculation = new SimpleDateFormat("yyyy-MM-dd").parse(producteurAcrer.getDateImmatriculation());
		}
		
		if ("".equals(producteurAcrer.getEmail()) && "PersonnePhysique".equals(typeProducteur)) {
			blocErreurMsg = "L'email est obligatoire";
			return "adminDistributeur.xhtml?faces-redirect=true";
		}
		
		if (("".equals(producteurAcrer.getNom()) || "".equals(producteurAcrer.getPrenom())) && "PersonnePhysique".equals(typeProducteur)) {
			blocErreurMsg = "Le nom et le prénom sont obligatoires";
			return "adminDistributeur.xhtml?faces-redirect=true";
		}

		if (selectedPphs.isEmpty()) {
			blocErreurMsg = "Veuillez sélectionner au moins un représentant";
			return "adminDistributeur.xhtml?faces-redirect=true";
		}

		if ("".equals(producteurAcrer.getAdresse())) {
			blocErreurMsg = "L'adresse est obligatoire";
			return "adminDistributeur.xhtml?faces-redirect=true";
		}

		if ("".equals(producteurAcrer.getDescriptif())) {
			blocErreurMsg = "Le descriptif est obligatoire";
			return "adminDistributeur.xhtml?faces-redirect=true";
		}

		Producteur prodCreated = new Producteur();

		prodCreated = producteurService.createProducteur(typeProducteur, personnePhysiques, producteurAcrer, dateImmatriculation);
		if (prodCreated == null) {
			blocErreurMsg = "Le producteur n'a pas été enregistré";
			return "adminDistributeur.xhtml?faces-redirect=true";
		}
		blocConfirmationMsg = "Le producteur a été enregistré avec succès";

		producteurAcrer = new ProducteurDTO();
		pphToCreateDto = new PersonnePhysiqueDTO();
		selectedPphs = new ArrayList<>();
		personnePhysiques = new ArrayList<>();
		return "adminDistributeur.xhtml?faces-redirect=true";

	}

	public void resetErrorMsgs() {
		setBlocConfirmationMsg(null);
		setBlocErreurMsg(null);
	}

}
