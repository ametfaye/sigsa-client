package org.cages.moulinette.managedBean;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.cages.moulinette.dto.CategorieIntrantDTO;
import org.cages.moulinette.dto.UserInfosDTO;
import org.cages.moulinette.model.CategorieIntrant;
import org.cages.moulinette.service.ICategorieIntrantService;
import org.cages.moulinette.service.IUserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;

@ManagedBean(name = "CategorieIntrantController")
@SessionScoped
@Getter
@Setter
public class CategorieIntrantController {
	
	private String createCategorieIntrantErreurMsg;
	private String createCategorieIntrantSuccesMsg;

	private UserInfosDTO connectedUserDetails = new UserInfosDTO();
	private CategorieIntrantDTO newCategorieIntrantDto = new CategorieIntrantDTO();
	private CategorieIntrantDTO updateCategorieIntrantDto = new CategorieIntrantDTO();
	
	private List<CategorieIntrantDTO> categoriesIntrants = new ArrayList<>();

	@ManagedProperty(value = "#{userService}")
	private IUserService userService;

	@ManagedProperty(value = "#{categorieIntrantService}")
	private ICategorieIntrantService categorieIntrantService;

	@PostConstruct
	private void init() {
		initCategorieIntrantDTOs();
		resetErrorMsgs();
		connectedUserDetails = retrievedetailsConnectedUser();
		loadCategorieIntrantsList();
	}

	public UserInfosDTO retrievedetailsConnectedUser() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userService.findUserByMatricule(user.getUsername());
	}

	public void loadCategorieIntrantsList() {
		categoriesIntrants = categorieIntrantService.getAllCategoriesIntrants();
	}
	
	public String deleteCategorieIntrant(long id) {
		resetErrorMsgs();
		CategorieIntrant categorieIntrant = categorieIntrantService.getCategorieIntrantById(id);
		if (categorieIntrant != null) {
			categorieIntrantService.deleteCategorieIntrant(id);	
			setCreateCategorieIntrantSuccesMsg("La catégorie d'intrant "+categorieIntrant.getLibelle()+" est supprimé avec succès");
		}
		loadCategorieIntrantsList();
		return "categorieIntrant.xhtml?faces-redirect=true";
	}

	public String updateCategorieIntrant(long id) {
		resetErrorMsgs();

		if ("".equals(updateCategorieIntrantDto.getCode())) {
			setCreateCategorieIntrantErreurMsg("Le code est obligatoire");
			return "categorieIntrant.xhtml?faces-redirect=true";
		}
		if ("".equals(updateCategorieIntrantDto.getLibelle())) {
			setCreateCategorieIntrantErreurMsg("Le nom est obligatoire");
			return "categorieIntrant.xhtml?faces-redirect=true";
		}
		
		CategorieIntrant categorieIntrant = categorieIntrantService.getCategorieIntrantById(id);
		if (categorieIntrant != null) {
			categorieIntrant.setCode(updateCategorieIntrantDto.getCode());
			categorieIntrant.setLibelle(updateCategorieIntrantDto.getLibelle());
			categorieIntrantService.updateCategorieIntrant(categorieIntrant);
			setCreateCategorieIntrantSuccesMsg("La catégorie d'intrant "+categorieIntrant.getLibelle()+" est modifié avec succès");
		}
		loadCategorieIntrantsList();
		updateCategorieIntrantDto = new CategorieIntrantDTO();
		return "categorieIntrant.xhtml?faces-redirect=true";
	}
	
	public String createCategorieIntrant() {
		resetErrorMsgs();
		if ("".equals(newCategorieIntrantDto.getLibelle())) {
			setCreateCategorieIntrantErreurMsg("Le nom est obligatoire");
			return "categorieIntrant.xhtml?faces-redirect=true";
		}
		if ("".equals(newCategorieIntrantDto.getCode())) {
			setCreateCategorieIntrantErreurMsg("Le code est obligatoire");
			return "categorieIntrant.xhtml?faces-redirect=true";
		}
		
		CategorieIntrant ci = categorieIntrantService.getCategorieIntrantByCode(newCategorieIntrantDto.getCode());
		if (ci != null) {
			setCreateCategorieIntrantErreurMsg("La catégorie d'intrant "+newCategorieIntrantDto.getCode()+" existe déjà !");
			return "categorieIntrant.xhtml?faces-redirect=true";
		} else {
			//String base64 = Base64.getEncoder().encodeToString(newCategorieIntrantDto.getPictoFile().getContents());
			CategorieIntrant categorieIntrant = new CategorieIntrant(newCategorieIntrantDto.getCode(), newCategorieIntrantDto.getLibelle(), "picto");
			categorieIntrantService.createCategorieIntrant(categorieIntrant);
			setCreateCategorieIntrantSuccesMsg("La catégorie d'intrant "+newCategorieIntrantDto.getLibelle()+" est crée avec succès");
		}
		loadCategorieIntrantsList();
		newCategorieIntrantDto = new CategorieIntrantDTO();
		
		return "categorieIntrant.xhtml?faces-redirect=true";
	}
	
	private void initCategorieIntrantDTOs() {
		newCategorieIntrantDto = new CategorieIntrantDTO();
	}

	public void resetErrorMsgs() {
		setCreateCategorieIntrantErreurMsg(null);
		setCreateCategorieIntrantSuccesMsg(null);
	}

}
