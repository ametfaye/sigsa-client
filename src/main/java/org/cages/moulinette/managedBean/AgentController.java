package org.cages.moulinette.managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;

import org.cages.moulinette.dto.AgentDTO;
import org.cages.moulinette.dto.UserInfosDTO;
import org.cages.moulinette.model.Agent;
import org.cages.moulinette.model.EntitePublicRef;
import org.cages.moulinette.model.Fonction;
import org.cages.moulinette.model.Groupe;
import org.cages.moulinette.model.PersonnePhysique;
import org.cages.moulinette.model.Service;
import org.cages.moulinette.model.SousService;
import org.cages.moulinette.service.IAgentService;
import org.cages.moulinette.service.IEntitePublicRefService;
import org.cages.moulinette.service.IFonctionService;
import org.cages.moulinette.service.IPersonnePhysiqueService;
import org.cages.moulinette.service.IServiceReferentialService;
import org.cages.moulinette.service.ISousServiceRefService;
import org.cages.moulinette.service.IUserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;

@ManagedBean(name = "AgentController")
@SessionScoped
@Getter
@Setter
public class AgentController {
	
	private String createAgentErreurMsg;
	private String createAgentSuccesMsg;

	private UserInfosDTO connectedUserDetails = new UserInfosDTO();
	private AgentDTO agentToCreateDto = new AgentDTO();
	private AgentDTO agentToUpdateDto = new AgentDTO();
	
	private List<Service> services = new ArrayList<>();
	private List<EntitePublicRef> entitePublicRefs = new ArrayList<>();
	private List<Fonction> fonctions = new ArrayList<>();
	private List<Groupe> groupes = new ArrayList<>();
	private List<AgentDTO> agentsLists = new ArrayList<>();
	private List<SousService> sousServices = new ArrayList<>();
	
	private long agentToCreateServiceId;
	private long agentToCreateFonctionId;
	private long agentToCreateEntitePubliqueId;
	private long agentToCreateGroupeId;
	private long agentToCreateSousServiceId;
	
	@ManagedProperty(value = "#{userService}")
	private IUserService userService;
	
	@ManagedProperty(value = "#{agentService}")
	private IAgentService agentService;
	
	@ManagedProperty(value = "#{personnePhysiqueService}")
	private IPersonnePhysiqueService personnePhysiqueService;

	@ManagedProperty(value = "#{serviceReferentialService}")
	private IServiceReferentialService serviceReferentialService;

	@ManagedProperty(value = "#{sousServiceRefService}")
	private ISousServiceRefService sousServiceRefService;
	
	@ManagedProperty(value = "#{entitePublicRefService}")
	private IEntitePublicRefService entitePublicRefService;
	
	@ManagedProperty(value = "#{fonctionService}")
	private IFonctionService fonctionService;


	@PostConstruct
	private void init() {
		resetErrorMsgs();
		connectedUserDetails = retrievedetailsConnectedUser();
		loadEntitePublicRefList();
		loadServicesList();
		loadFonctionsList();
		loadAgentsList();
		loadSousServicesList();
	}

	public UserInfosDTO retrievedetailsConnectedUser() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userService.findUserByMatricule(user.getUsername());
	}

	private void loadAgentsList() {
		agentsLists = agentService.findAllNotDeleted();
	}


	
	private void loadServicesList() {
		services = serviceReferentialService.getAllServices();
	}
	
	public void loadSousServicesList() {
		sousServices = sousServiceRefService.getAllSousServices();
	}
	
	private void loadEntitePublicRefList() {
		entitePublicRefs = entitePublicRefService.getAllEntitePublicRefs();
	}
	
	private void loadFonctionsList() {
		fonctions = fonctionService.getAllFonctions();
	}
	
	public void entitePublicRefsSelectionChanged(final AjaxBehaviorEvent event) {
		long id = (long) ((UIOutput) event.getSource()).getValue();
		services = serviceReferentialService.getServicesByEntitePublicRefsId(id);
	}
	
	public void serviceSelectionChanged(final AjaxBehaviorEvent event) {
		long id = (long) ((UIOutput) event.getSource()).getValue();
		sousServices = sousServiceRefService.getSousServicesByServiceId(id);
	}
	
	Boolean showBlocSousService ;
	public void showService(final AjaxBehaviorEvent event) {
		
		if(agentToCreateFonctionId == 1L)
			showBlocSousService = false;
		else
			showBlocSousService = true;		
	}
	
	public void annulerCreationAgent() {
		agentToCreateDto =  new AgentDTO();
	}

	public String showUpdateAgent() {
		resetErrorMsgs();
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String agentMatriculeUpdate = request.getParameter("agentMatriculeUpdate");
		
		Agent agent = agentService.findByMatricule(agentMatriculeUpdate);
		if(null != agent ) {
			AgentDTO agentDto = new AgentDTO(agent.getMatricule(), agent.getPersonnePhysique().getNom(),
								agent.getPersonnePhysique().getPrenom(), agent.getIndice(), agent.getCorps(),
								agent.getEchelon(), agent.getHierarchie(), agent.getPersonnePhysique().getSexe(),
								agent.getFonction().getId(), agent.getGroupe().getGrId(), agent.getSousService().getService().getEntite().getRedId(),
								agent.getSousService().getService().getSrvId(), agent.getSousService().getSsrvId(), agent.getPersonnePhysique().getEmail(),
								agent.getPersonnePhysique().getTelephone());			
			setAgentToUpdateDto(agentDto);
		}
		return "adminAgentUpdate.xhtml?faces-redirect=true";
	}
	
	public String deleteAgent(String matricule) {
		Agent agent = agentService.findByMatricule(matricule);
		if (agent != null) {
			agentService.delete(agent);
			setCreateAgentSuccesMsg("L'agent est supprimé avec succès !");
		}
		loadAgentsList();
		return "adminAgentsList.xhtml?faces-redirect=true";
	}
	
	public String createAgent() {
		setCreateAgentErreurMsg(null);
		
		
		if(!agentToCreateDto.checkEmptyFields().isEmpty()  || !agentToCreateDto.checkFieldsValidities().isEmpty() ) {
			setCreateAgentErreurMsg(agentToCreateDto.checkEmptyFields() + agentToCreateDto.checkFieldsValidities());
			return "adminAgentEdition.xhtml?faces-redirect=true";
		}
	
					
		Agent agent = agentService.findByMatricule(agentToCreateDto.getMatricule());
		if (agent != null) {
			setCreateAgentErreurMsg("L'agent avec la matricule "+agentToCreateDto.getMatricule()+" existe déjà !");
			return "adminAgentEdition.xhtml?faces-redirect=true";
		} else {
			Fonction fonction = fonctionService.getFonctionById(agentToCreateFonctionId);
			SousService sousService = sousServiceRefService.getSousServiceById(agentToCreateSousServiceId);
			PersonnePhysique personnePhysique = new PersonnePhysique(agentToCreateDto.getPrenom(), agentToCreateDto.getNom(), agentToCreateDto.getEmail(), agentToCreateDto.getTelephone(),
					agentToCreateDto.getSexe());
			PersonnePhysique pph = personnePhysiqueService.createPersonnePhysique(personnePhysique);
			Agent agentToCreate = new Agent(agentToCreateDto.getMatricule(), agentToCreateDto.getIndice(), agentToCreateDto.getCorps(),
					agentToCreateDto.getEchelon(), agentToCreateDto.getHierarchie(), 0 ,pph, fonction, sousService, null);
			agentService.createAgent(agentToCreate);
			setCreateAgentSuccesMsg("L'agent avec la matricule "+agentToCreateDto.getMatricule()+" est créé avec succès");
		}
		agentToCreateDto = new AgentDTO();
		loadAgentsList();
		return "adminAgentsList.xhtml?faces-redirect=true";
	}

	public String updateAgent() {
		resetErrorMsgs();
		String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
		        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
		
		if ("".equals(agentToUpdateDto.getIndice()) || agentToUpdateDto.getIndice() == null) {
			setCreateAgentErreurMsg("L'indice est obligatoire");
			return "adminAgentUpdate.xhtml?faces-redirect=true";
		}
		
		if ("".equals(agentToUpdateDto.getEmail()) || agentToUpdateDto.getEmail() == null) {
			setCreateAgentErreurMsg("L'email est obligatoire");
			return "adminAgentUpdate.xhtml?faces-redirect=true";
		}
		
		if (!agentToUpdateDto.getEmail().matches(regexPattern)) {
			setCreateAgentErreurMsg("Veuillez mettre une adresse email valide");
			return "adminAgentUpdate.xhtml?faces-redirect=true";
		}
		
		if ("".equals(agentToUpdateDto.getTelephone()) || agentToUpdateDto.getTelephone() == null) {
			setCreateAgentErreurMsg("Le numéro de téléphone est obligatoire");
			return "adminAgentUpdate.xhtml?faces-redirect=true";
		}
		
		if ("".equals(agentToUpdateDto.getEchelon()) || agentToUpdateDto.getEchelon() == null) {
			setCreateAgentErreurMsg("L'échelon est obligatoire");
			return "adminAgentUpdate.xhtml?faces-redirect=true";
		}
		
		if ("".equals(agentToUpdateDto.getHierarchie()) || agentToUpdateDto.getHierarchie() == null) {
			setCreateAgentErreurMsg("L'hiérarchie est obligatoire");
			return "adminAgentUpdate.xhtml?faces-redirect=true";
		}
		
		if ("".equals(agentToUpdateDto.getCorps()) || agentToUpdateDto.getCorps() == null) {
			setCreateAgentErreurMsg("Le corps est obligatoire");
			return "adminAgentUpdate.xhtml?faces-redirect=true";
		}
		
		if (agentToUpdateDto.getServiceId() == 0L) {
			setCreateAgentErreurMsg("Vous devez choisir un service !");
			return "adminAgentUpdate.xhtml?faces-redirect=true";
		}
		
		if (agentToUpdateDto.getEtablissementId() == 0L) {
			setCreateAgentErreurMsg("Vous devez choisir un établissement !");
			return "adminAgentUpdate.xhtml?faces-redirect=true";
		}
		
		if (agentToUpdateDto.getSousServiceId() == 0L) {
			setCreateAgentErreurMsg("Vous devez choisir un sous service !");
			return "adminAgentUpdate.xhtml?faces-redirect=true";
		}
		
		if (agentToUpdateDto.getFonctionId() == 0L) {
			setCreateAgentErreurMsg("Vous devez choisir une fonction !");
			return "adminAgentUpdate.xhtml?faces-redirect=true";
		}
		
		if (agentToUpdateDto.getGroupeId() == 0L) {
			setCreateAgentErreurMsg("Vous devez choisir un groupe !");
			return "adminAgentUpdate.xhtml?faces-redirect=true";
		}
		Agent agentToUpdate = agentService.findByMatricule(agentToUpdateDto.getMatricule());
		
		Fonction fonction = fonctionService.getFonctionById(agentToUpdateDto.getFonctionId());
		SousService sousService = sousServiceRefService.getSousServiceById(agentToUpdateDto.getSousServiceId());
		
		PersonnePhysique pph = agentToUpdate.getPersonnePhysique();
		pph.setSexe(agentToUpdateDto.getSexe());
		
		agentToUpdate.setFonction(fonction);
		agentToUpdate.setSousService(sousService);
		agentToUpdate.setCorps(agentToUpdateDto.getCorps());
		agentToUpdate.setIndice(agentToUpdateDto.getIndice());
		agentToUpdate.setEchelon(agentToUpdateDto.getEchelon());
		agentToUpdate.setHierarchie(agentToUpdateDto.getHierarchie());

		agentService.updateAgent(agentToUpdate);
		setCreateAgentSuccesMsg("L'agent avec la matricule "+agentToUpdateDto.getMatricule()+" est modifié avec succès");
		
		agentToUpdateDto = new AgentDTO();
		loadAgentsList();
		return "adminAgentsList.xhtml?faces-redirect=true";
	}

	public void resetErrorMsgs() {
		setCreateAgentErreurMsg(null);
		setCreateAgentSuccesMsg(null);
	}
	
}
