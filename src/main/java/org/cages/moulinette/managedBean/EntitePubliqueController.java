package org.cages.moulinette.managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.cages.moulinette.dto.EntitePubliqueDTO;
import org.cages.moulinette.dto.UserInfosDTO;
import org.cages.moulinette.model.EntitePublicRef;
import org.cages.moulinette.service.IEntitePublicRefService;
import org.cages.moulinette.service.IUserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;

@ManagedBean(name = "EntitePubliqueController")
@SessionScoped
@Getter
@Setter
public class EntitePubliqueController {
	
	private String createEntitePubliqueErreurMsg;
	private String createEntitePubliqueSuccesMsg;

	private UserInfosDTO connectedUserDetails = new UserInfosDTO();
	private EntitePubliqueDTO newEntitePubliqueDto = new EntitePubliqueDTO();
	private EntitePubliqueDTO updateEntitePubliqueDto = new EntitePubliqueDTO();
	
	private List<EntitePublicRef> entitesPubliques = new ArrayList<>();

	@ManagedProperty(value = "#{userService}")
	private IUserService userService;

	@ManagedProperty(value = "#{entitePublicRefService}")
	private IEntitePublicRefService entitePublicRefService;

	@PostConstruct
	private void init() {
		initEntitePubliqueDTOs();
		resetErrorMsgs();
		connectedUserDetails = retrievedetailsConnectedUser();
		loadEntitePubliquesList();
	}

	public UserInfosDTO retrievedetailsConnectedUser() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userService.findUserByMatricule(user.getUsername());
	}

	public void loadEntitePubliquesList() {
		entitesPubliques = entitePublicRefService.getAllEntitePublicRefs();
	}
	
	public String deleteEntitePublique(long id) {
		resetErrorMsgs();
		EntitePublicRef entitePublicRef = entitePublicRefService.getEntitePublicRefById(id);
		if (entitePublicRef != null) {
			entitePublicRefService.deleteEntitePublique(id);	
			setCreateEntitePubliqueSuccesMsg("L'entité publique "+entitePublicRef.getLibelle()+" est supprimé avec succès");
		}
		loadEntitePubliquesList();
		return "adminEntitesPubliquesList.xhtml?faces-redirect=true";
	}

	public String updateEntitePublique(long id) {
		resetErrorMsgs();
		if ("".equals(updateEntitePubliqueDto.getLibelle())) {
			setCreateEntitePubliqueErreurMsg("Le nom est obligatoire");
			return "adminEntitesPubliquesList.xhtml?faces-redirect=true";
		}

		if ("".equals(updateEntitePubliqueDto.getCode())) {
			setCreateEntitePubliqueErreurMsg("Le code est obligatoire");
			return "adminEntitesPubliquesList.xhtml?faces-redirect=true";
		}
		EntitePublicRef entitePublicRef = entitePublicRefService.getEntitePublicRefById(id);
		if (entitePublicRef != null) {
			entitePublicRef.setLibelle(updateEntitePubliqueDto.getLibelle());
			entitePublicRefService.updateEntitePublique(entitePublicRef);
			setCreateEntitePubliqueSuccesMsg("L'entité publique "+entitePublicRef.getLibelle()+" est modifié avec succès");
		}
		loadEntitePubliquesList();
		updateEntitePubliqueDto = new EntitePubliqueDTO();
		return "adminEntitesPubliquesList.xhtml?faces-redirect=true";
	}
	
	public String createEntitePublique() {
		resetErrorMsgs();
		if ("".equals(newEntitePubliqueDto.getLibelle())) {
			setCreateEntitePubliqueErreurMsg("Le nom est obligatoire");
			return "adminEntitesPubliquesList.xhtml?faces-redirect=true";
		}
		if ("".equals(newEntitePubliqueDto.getCode())) {
			setCreateEntitePubliqueErreurMsg("Le code est obligatoire");
			return "adminEntitesPubliquesList.xhtml?faces-redirect=true";
		}

		EntitePublicRef entitePublicRef = entitePublicRefService.getEntitePubliqueByCode(newEntitePubliqueDto.getCode());
		if (entitePublicRef != null) {
			setCreateEntitePubliqueErreurMsg("L'entité publique "+newEntitePubliqueDto.getCode()+" existe déjà !");
			return "adminEntitesPubliquesList.xhtml?faces-redirect=true";
		} else {
			EntitePublicRef entitePubliqueToCreate = new EntitePublicRef(newEntitePubliqueDto.getCode(), newEntitePubliqueDto.getLibelle());
			entitePublicRefService.createEntitePublique(entitePubliqueToCreate);
			setCreateEntitePubliqueSuccesMsg("L'entité publique "+newEntitePubliqueDto.getLibelle()+" est crée avec succès");
		}
		loadEntitePubliquesList();
		newEntitePubliqueDto = new EntitePubliqueDTO();
		
		return "adminEntitesPubliquesList.xhtml?faces-redirect=true";
	}
	
	private void initEntitePubliqueDTOs() {
		newEntitePubliqueDto = new EntitePubliqueDTO();
	}

	public void resetErrorMsgs() {
		setCreateEntitePubliqueErreurMsg(null);
		setCreateEntitePubliqueSuccesMsg(null);
	}

}
