package org.cages.moulinette.managedBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.cages.moulinette.dto.AgentDTO;
import org.cages.moulinette.dto.RoleCreateDTO;
import org.cages.moulinette.dto.RoleDTO;
import org.cages.moulinette.dto.UserInfosDTO;
import org.cages.moulinette.model.Agent;
import org.cages.moulinette.model.EntitePublicRef;
import org.cages.moulinette.model.Role;
import org.cages.moulinette.model.Service;
import org.cages.moulinette.model.UserInfos;
import org.cages.moulinette.service.IAgentService;
import org.cages.moulinette.service.IRoleService;
import org.cages.moulinette.service.IServiceReferentialService;
import org.cages.moulinette.service.IUserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;

@ManagedBean(name = "UtilisateurController")
@SessionScoped
@Getter
@Setter
public class UtilisateurController {

	private final static Logger LOGGER = Logger.getLogger(UtilisateurController.class);
	
	private AgentDTO agentBeneficiareDTO;
	private Agent agentBeneficiare = new Agent();
	private AgentDTO agentToUpdateDTO = new AgentDTO();
	private UserInfosDTO userToUpdate = new UserInfosDTO();
	private RoleCreateDTO roleCreateDTO = new RoleCreateDTO();
	private RoleCreateDTO roleUpdateDTO = new RoleCreateDTO();
	
	// roles selected
	private List<String> selectedRolesCreation = new ArrayList<>();
	private List<String> selectedRolesUpdate = new ArrayList<>();
	
	private String userMatricule;
	private String userMatriculeUpdate;
	
	private String userPassword;
	private String userConfirmPassword;
	
	private String updateUserTelephone;
	private String updateUserPassword;
	private String updateUserConfirmPassword;
		
	private String createUserErreurMsg;
	private String createUserSuccesMsg;
	
	private boolean roleChecked;
	
	private UserInfosDTO connectedUserDetails = new UserInfosDTO();

	private List<RoleDTO> rolesToUpdate = new ArrayList<>();
	
	private List<Service> services = new ArrayList<>();
	private List<EntitePublicRef> entitePublicRefs = new ArrayList<>();
	
	private List<UserInfosDTO> usersLists;

	@ManagedProperty(value = "#{userService}")
	private IUserService userService;

	@ManagedProperty(value = "#{roleService}")
	private IRoleService roleService;

	@ManagedProperty(value = "#{serviceReferentialService}")
	private IServiceReferentialService serviceReferentialService;
	
	@ManagedProperty(value = "#{agentService}")
	private IAgentService agentService;

	@PostConstruct
	private void initConfiguration() {
		resetErrorMsgs();
		connectedUserDetails = retrievedetailsConnectedUser();
		loadUsersList();
	}

	private void loadUsersList() {
		usersLists = userService.loadAllUsersDTO(connectedUserDetails.getIdUser());
	}

	public UserInfosDTO retrievedetailsConnectedUser() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userService.findUserByMatricule(user.getUsername());
	}
	
	public String showUpdateUser() {
		resetErrorMsgs();
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		userMatriculeUpdate = request.getParameter("userMatriculeUpdate");
		Agent agent = agentService.findByMatricule(userMatriculeUpdate);
		
		if(null != agent ) {
			AgentDTO agentDto = new AgentDTO(userMatriculeUpdate, agent.getPersonnePhysique().getNom(), agent.getPersonnePhysique().getPrenom(),
					agent.getFonction().getLibelle(), agent.getIndice(), agent.getFonction().getGrade().getLibelle(), agent.getGroupe().getLibelle());
			agentDto.setServiceLibelle(agent.getSousService().getLibelle());
			agentDto.setEtablissement(agent.getSousService().getService().getEntite().getLibelle());
			agentDto.setTelephone(agent.getPersonnePhysique().getTelephone());
			setAgentToUpdateDTO(agentDto);	
		}
		userToUpdate = userService.findUserByMatricule(userMatriculeUpdate);
		UserInfos user = userService.findUserByIdentifiant(userMatriculeUpdate);
		List<Role> allRoles = roleService.getAllRoles();
		Set<Role> roles = user.getRoles();
		List<Integer> rIds = roles.stream().map(Role::getId).collect(Collectors.toList());
		
		List<RoleDTO> roleDTOs = new ArrayList<>();
		allRoles.stream().forEach(role -> {
			RoleDTO roleDTO = new RoleDTO(role.getId(), role.getRole(), role.getLibelle(), role.getDescription());
			if (rIds.contains(role.getId())) {
				roleDTO.setSelected(true);
			}
			roleDTOs.add(roleDTO);		

		});
		//userToUpdate.setRoles(roleDTOs);
		roleUpdateDTO = new RoleCreateDTO();

		return "adminUtilisateurUpdate.xhtml?faces-redirect=true";
	}
	
	public String updateUser() {
		if ("".equals(userToUpdate.getTelephone()) || userToUpdate.getTelephone() == null) {
			setCreateUserErreurMsg("Le numéro de téléphone est obligatoire");
			return "adminUtilisateurUpdate.xhtml?faces-redirect=true";
		}
		
		if ("".equals(userToUpdate.getEmail()) || userToUpdate.getEmail() == null) {
			setCreateUserErreurMsg("L'email est obligatoire");
			return "adminUtilisateurUpdate.xhtml?faces-redirect=true";
		}
		
		if ("".equals(updateUserPassword) || updateUserPassword == null) {
			setCreateUserErreurMsg("Vous devez choisir un mot de passe !");
			return "adminUtilisateurUpdate.xhtml?faces-redirect=true";
		}

		if (!updateUserConfirmPassword.equals(updateUserPassword)) {
			setCreateUserErreurMsg("Les mots de passe ne correspondent pas !");
			return "adminUtilisateurUpdate.xhtml?faces-redirect=true";
		}
		
		updateTBSelectedRolesFromObject();
		updateEDITransSelectedRolesFromObject();
		updateValAccSignFromObject();
		updateAutresFonctionnalites();
		
		if (!roleUpdateDTO.isTBADM() && !roleUpdateDTO.isTBCHS() && !roleUpdateDTO.isTBGS() 
				&& !roleUpdateDTO.isTBSG() && !roleUpdateDTO.isTBSGG()) {
			setCreateUserErreurMsg("Veuillez selectionner au moins un tableau de bord !");
			return "adminUtilisateurUpdate.xhtml?faces-redirect=true";
		}
		
		if (userToUpdate.getRoles().isEmpty()) {
			setCreateUserErreurMsg("Veuillez selectionner au moins un rôle !");
			return "adminUtilisateurUpdate.xhtml?faces-redirect=true";
		}
		
		UserInfos existingUser = userService.findUserById(userToUpdate.getIdUser());
		if (existingUser != null) {
			userService.update(existingUser, updateUserPassword, userToUpdate);
			setCreateUserSuccesMsg("Le compte est modifié avec succès !");
		}
		loadUsersList();
		return "adminUtilisateursList.xhtml?faces-redirect=true";
	}
	
	public void changeMatricule(){
		setAgentBeneficiare(agentService.findByMatricule(userMatricule));
		if (agentBeneficiare != null) {
			AgentDTO agentDto = new AgentDTO(userMatricule, agentBeneficiare.getPersonnePhysique().getNom(),
						agentBeneficiare.getPersonnePhysique().getPrenom(), agentBeneficiare.getSousService().getSsrvId());
			agentDto.setServiceLibelle(agentBeneficiare.getSousService().getLibelle());
			agentDto.setEtablissement(agentBeneficiare.getSousService().getService().getEntite().getLibelle());
			if(null != agentDto)
				setAgentBeneficiareDTO(agentDto);
		}
	}
	
	public String deleteUser(long idUser) {
		UserInfos existingUser = userService.findUserById(idUser);
		if (existingUser != null) {
			userService.delete(existingUser.getId());
			setCreateUserSuccesMsg("Le compte est supprimé avec succès !");
		}
		loadUsersList();
		return "adminUtilisateursList.xhtml?faces-redirect=true";
	}
	
	public void addRoleDTO(final AjaxBehaviorEvent event) {
		((UIOutput) event.getSource()).getValue();
		updateDashboardValues();
	}
	
	public void updateRoleDTO(final AjaxBehaviorEvent event) {
		((UIOutput) event.getSource()).getValue();
		updateDashboardValuesUpdate();
	}

	public String createUser() {
		setCreateUserErreurMsg(null);
			
		if (agentBeneficiareDTO == null || "".equals(agentBeneficiareDTO.getMatricule()) || agentBeneficiareDTO.getMatricule() == null) {
			setCreateUserErreurMsg("La matricule est obligatoire");
			return "adminUtilisateurEdition.xhtml?faces-redirect=true";
		}
		
		if ("".equals(userPassword) || userPassword == null) {
			setCreateUserErreurMsg("Vous devez choisir un mot de passe !");
			return "adminUtilisateurEdition.xhtml?faces-redirect=true";
		}

		if (!userConfirmPassword.equals(userPassword)) {
			setCreateUserErreurMsg("Les mots de passe ne correspondent pas !");
			return "adminUtilisateurEdition.xhtml?faces-redirect=true";
		}
		addTBSelectedRolesFromObject();
		addEDITransSelectedRolesFromObject();
		addValAccSignFromObject();
		addAutresFonctionnalites();
		
		if (!roleCreateDTO.isTBADM() && !roleCreateDTO.isTBCHS() && !roleCreateDTO.isTBGS() 
				&& !roleCreateDTO.isTBSG() && !roleCreateDTO.isTBSGG()) {
			setCreateUserErreurMsg("Veuillez selectionner au moins un tableau de bord !");
			return "adminUtilisateurEdition.xhtml?faces-redirect=true";
		}
		
		if (selectedRolesCreation.isEmpty()) {
			setCreateUserErreurMsg("Veuillez selectionner au moins un rôle !");
			return "adminUtilisateurEdition.xhtml?faces-redirect=true";
		}

		UserInfosDTO existingUser = userService.findUserByMatricule(agentBeneficiareDTO.getMatricule());

		if (existingUser != null) {
			setCreateUserErreurMsg("Un compte avec la matricule " + agentBeneficiareDTO.getMatricule()+ " existe déjà");
			return "adminUtilisateurEdition.xhtml?faces-redirect=true";
		}

		try {
			userService.createUser(agentBeneficiare, userPassword, selectedRolesCreation);
			setCreateUserSuccesMsg("Le compte est créé avec succès !");
			selectedRolesCreation = new ArrayList<>();
			roleCreateDTO = new RoleCreateDTO();
			loadUsersList();
			return "adminUtilisateursList.xhtml?faces-redirect=true";

		} catch (Exception e) {
			setCreateUserErreurMsg("Erreur survenue, merci de vérifier les champs obligatoires !");
			LOGGER.error(e.getMessage());
			return "adminUtilisateurEdition.xhtml?faces-redirect=true";
		}
	}

	public String disableEnableUser(long idUser, int statut) {
		UserInfos existingUser = userService.findUserById(idUser);
		if (existingUser != null) {
			userService.disableEnableUser(existingUser, statut);
			if (statut == 0) {
				setCreateUserSuccesMsg("Le compte "+existingUser.getAgent().getPersonnePhysique().getPrenom()+" "+existingUser.getAgent().getPersonnePhysique().getNom()+" est désactivé avec succès !");
			} else {
				setCreateUserSuccesMsg("Le compte "+existingUser.getAgent().getPersonnePhysique().getPrenom()+" "+existingUser.getAgent().getPersonnePhysique().getNom()+" est activé avec succès !");	
			}
		}
		loadUsersList();
		return "adminUtilisateursList.xhtml?faces-redirect=true";
	}
	
	public void resetErrorMsgs() {
		setCreateUserErreurMsg(null);
		setCreateUserSuccesMsg(null);
	}
	
	private void updateDashboardValues() {
		if (roleCreateDTO.isTBADM()) {
			roleCreateDTO.setTBCHS(false);
			roleCreateDTO.setTBGS(false);
			roleCreateDTO.setTBSG(false);
			roleCreateDTO.setTBSGG(false);
			roleCreateDTO.setADMIN(true);
		} else {
			roleCreateDTO.setADMIN(false);
		}
		if (roleCreateDTO.isADMIN()) {
			roleCreateDTO.setTBADM(false);
			roleCreateDTO.setTBGS(false);
			roleCreateDTO.setTBSG(false);
			roleCreateDTO.setTBSGG(false);
			roleCreateDTO.setTBCHS(false);
		} else {
			roleCreateDTO.setADMIN(false);
		}
		if (roleCreateDTO.isTBCHS()) {
			roleCreateDTO.setTBADM(false);
			roleCreateDTO.setTBGS(false);
			roleCreateDTO.setTBSG(false);
			roleCreateDTO.setTBSGG(false);
			roleCreateDTO.setADMIN(false);
		} else {
			roleCreateDTO.setTBCHS(false);
		}
		if (roleCreateDTO.isTBGS()) {
			roleCreateDTO.setTBADM(false);
			roleCreateDTO.setTBCHS(false);
			roleCreateDTO.setTBSG(false);
			roleCreateDTO.setTBSGG(false);
			roleCreateDTO.setADMIN(false);
		} else {
			roleCreateDTO.setTBGS(false);
		}
		if (roleCreateDTO.isTBSG()) {
			roleCreateDTO.setTBADM(false);
			roleCreateDTO.setTBGS(false);
			roleCreateDTO.setTBCHS(false);
			roleCreateDTO.setTBSGG(false);
			roleCreateDTO.setADMIN(false);
		} else {
			roleCreateDTO.setTBSG(false);
		}
		if (roleCreateDTO.isTBSGG()) {
			roleCreateDTO.setTBADM(false);
			roleCreateDTO.setTBGS(false);
			roleCreateDTO.setTBSG(false);
			roleCreateDTO.setTBCHS(false);
			roleCreateDTO.setADMIN(false);
		} else {
			roleCreateDTO.setTBSGG(false);
		}
	}
	
	private void updateDashboardValuesUpdate() {
		if (roleUpdateDTO.isTBADM()) {
			roleUpdateDTO.setTBCHS(false);
			roleUpdateDTO.setTBGS(false);
			roleUpdateDTO.setTBSG(false);
			roleUpdateDTO.setTBSGG(false);
			roleUpdateDTO.setADMIN(true);
		} else {
			roleUpdateDTO.setADMIN(false);
		}
		if (roleUpdateDTO.isADMIN()) {
			roleUpdateDTO.setTBADM(false);
			roleUpdateDTO.setTBGS(false);
			roleUpdateDTO.setTBSG(false);
			roleUpdateDTO.setTBSGG(false);
			roleUpdateDTO.setTBCHS(false);
		} else {
			roleUpdateDTO.setADMIN(false);
		}
		if (roleUpdateDTO.isTBCHS()) {
			roleUpdateDTO.setTBADM(false);
			roleUpdateDTO.setTBGS(false);
			roleUpdateDTO.setTBSG(false);
			roleUpdateDTO.setTBSGG(false);
			roleUpdateDTO.setADMIN(false);
		} else {
			roleUpdateDTO.setTBCHS(false);
		}
		if (roleUpdateDTO.isTBGS()) {
			roleUpdateDTO.setTBADM(false);
			roleUpdateDTO.setTBCHS(false);
			roleUpdateDTO.setTBSG(false);
			roleUpdateDTO.setTBSGG(false);
			roleUpdateDTO.setADMIN(false);
		} else {
			roleUpdateDTO.setTBGS(false);
		}
		if (roleUpdateDTO.isTBSG()) {
			roleUpdateDTO.setTBADM(false);
			roleUpdateDTO.setTBGS(false);
			roleUpdateDTO.setTBCHS(false);
			roleUpdateDTO.setTBSGG(false);
			roleUpdateDTO.setADMIN(false);
		} else {
			roleUpdateDTO.setTBSG(false);
		}
		if (roleUpdateDTO.isTBSGG()) {
			roleUpdateDTO.setTBADM(false);
			roleUpdateDTO.setTBGS(false);
			roleUpdateDTO.setTBSG(false);
			roleUpdateDTO.setTBCHS(false);
			roleUpdateDTO.setADMIN(false);
		} else {
			roleUpdateDTO.setTBSGG(false);
		}
	}
	
	private void addTBSelectedRolesFromObject() {
		if (roleCreateDTO.isTBSGG()) {
			selectedRolesCreation.add("TBSGG");
		}
		if (roleCreateDTO.isTBCHS()) {
			selectedRolesCreation.add("TBCHS");
		}
		if (roleCreateDTO.isTBADM()) {
			selectedRolesCreation.add("TBADM");
		}
		if (roleCreateDTO.isTBSG()) {
			selectedRolesCreation.add("TBSG");
		}
		if (roleCreateDTO.isTBGS()) {
			selectedRolesCreation.add("TBGS");
		}
	}
	
	private void addEDITransSelectedRolesFromObject() {
		if (roleCreateDTO.isEDIODM()) {
			selectedRolesCreation.add("EDIODM");
		}
		if (roleCreateDTO.isUPODM()) {
			selectedRolesCreation.add("UPODM");
		}
		if (roleCreateDTO.isADPART()) {
			selectedRolesCreation.add("ADPART");
		}
		if (roleCreateDTO.isTRODM()) {
			selectedRolesCreation.add("TRODM");
		}
		if (roleCreateDTO.isERAODM()) {
			selectedRolesCreation.add("ERAODM");
		}
		if (roleCreateDTO.isGFODM()) {
			selectedRolesCreation.add("GFODM");
		}
	}

	private void addAutresFonctionnalites() {
		if (roleCreateDTO.isGENODM()) {
			selectedRolesCreation.add("GENODM");
		}
		if (roleCreateDTO.isGENAST()) {
			selectedRolesCreation.add("GENAST");
		}
		if (roleCreateDTO.isSVDOC()) {
			selectedRolesCreation.add("SVDOC");
		}
		if (roleCreateDTO.isADMIN() && !selectedRolesCreation.contains("ADMIN")) {
			selectedRolesCreation.add("ADMIN");
		}
		if (roleCreateDTO.isPUBODM()) {
			selectedRolesCreation.add("PUBODM");
		}
		if (roleCreateDTO.isEASTMAIL()) {
			selectedRolesCreation.add("EASTMAIL");
		}
		if (roleCreateDTO.isEODMMAIL()) {
			selectedRolesCreation.add("EODMMAIL");
		}
	}

	private void addValAccSignFromObject() {
		if (roleCreateDTO.isVRCHS()) {
			selectedRolesCreation.add("VRCHS");
		}
		if (roleCreateDTO.isARSG()) {
			selectedRolesCreation.add("ARSG");
		}
		if (roleCreateDTO.isARSGG()) {
			selectedRolesCreation.add("ARSGG");
		}
		if (roleCreateDTO.isVRSGG()) {
			selectedRolesCreation.add("VRSGG");
		}
		if (roleCreateDTO.isSIGNAST()) {
			selectedRolesCreation.add("SIGNAST");
		}
		if (roleCreateDTO.isSIGNODM()) {
			selectedRolesCreation.add("SIGNODM");
		}
	}
	
	private void updateTBSelectedRolesFromObject() {
		if (roleUpdateDTO.isTBSGG()) {
			selectedRolesUpdate.add("TBSGG");
		}
		if (roleUpdateDTO.isTBCHS()) {
			selectedRolesUpdate.add("TBCHS");
		}
		if (roleUpdateDTO.isTBADM()) {
			selectedRolesUpdate.add("TBADM");
		}
		if (roleUpdateDTO.isTBSG()) {
			selectedRolesUpdate.add("TBSG");
		}
		if (roleUpdateDTO.isTBGS()) {
			selectedRolesUpdate.add("TBGS");
		}
	}
	
	private void updateEDITransSelectedRolesFromObject() {
		if (roleUpdateDTO.isEDIODM()) {
			selectedRolesUpdate.add("EDIODM");
		}
		if (roleUpdateDTO.isUPODM()) {
			selectedRolesUpdate.add("UPODM");
		}
		if (roleUpdateDTO.isADPART()) {
			selectedRolesUpdate.add("ADPART");
		}
		if (roleUpdateDTO.isTRODM()) {
			selectedRolesUpdate.add("TRODM");
		}
		if (roleUpdateDTO.isERAODM()) {
			selectedRolesUpdate.add("ERAODM");
		}
		if (roleUpdateDTO.isGFODM()) {
			selectedRolesUpdate.add("GFODM");
		}
	}

	private void updateAutresFonctionnalites() {
		if (roleUpdateDTO.isGENODM()) {
			selectedRolesUpdate.add("GENODM");
		}
		if (roleUpdateDTO.isGENAST()) {
			selectedRolesUpdate.add("GENAST");
		}
		if (roleUpdateDTO.isSVDOC()) {
			selectedRolesUpdate.add("SVDOC");
		}
		if (roleUpdateDTO.isADMIN() && !selectedRolesUpdate.contains("ADMIN")) {
			selectedRolesUpdate.add("ADMIN");
		}
		if (roleUpdateDTO.isPUBODM()) {
			selectedRolesUpdate.add("PUBODM");
		}
		if (roleUpdateDTO.isEASTMAIL()) {
			selectedRolesUpdate.add("EASTMAIL");
		}
		if (roleUpdateDTO.isEODMMAIL()) {
			selectedRolesUpdate.add("EODMMAIL");
		}
	}

	private void updateValAccSignFromObject() {
		if (roleUpdateDTO.isVRCHS()) {
			selectedRolesUpdate.add("VRCHS");
		}
		if (roleUpdateDTO.isARSG()) {
			selectedRolesUpdate.add("ARSG");
		}
		if (roleUpdateDTO.isARSGG()) {
			selectedRolesUpdate.add("ARSGG");
		}
		if (roleUpdateDTO.isVRSGG()) {
			selectedRolesUpdate.add("VRSGG");
		}
		if (roleUpdateDTO.isSIGNAST()) {
			selectedRolesUpdate.add("SIGNAST");
		}
		if (roleUpdateDTO.isSIGNODM()) {
			selectedRolesUpdate.add("SIGNODM");
		}
	}

}
