package org.cages.moulinette.managedBean;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;

import org.cages.moulinette.dto.CategorieIntrantDTO;
import org.cages.moulinette.dto.CoupleDTO;
import org.cages.moulinette.dto.IntrantDTO;
import org.cages.moulinette.dto.UniteDeMesureDTO;
import org.cages.moulinette.dto.UserInfosDTO;
import org.cages.moulinette.model.Intrant;
import org.cages.moulinette.service.ICategorieIntrantService;
import org.cages.moulinette.service.IUniteDeMesureService;
import org.cages.moulinette.service.IUserService;
import org.cages.moulinette.service.IntrantService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;

@ManagedBean(name = "IntrantController")
@SessionScoped
@Getter
@Setter
public class IntrantController {
	
	private long categorieIntrantId;
	private long uniteDeMesureId;

	private String createIntrantErreurMsg;
	private String createIntrantSuccesMsg;

	private UserInfosDTO connectedUserDetails = new UserInfosDTO();
	private IntrantDTO newIntrantDto = new IntrantDTO();
	private IntrantDTO updateIntrantDto = new IntrantDTO();

	private List<IntrantDTO> intrants = new ArrayList<>();
	private List<CategorieIntrantDTO> categorieIntrantDTOs = new ArrayList<>();
	private List<UniteDeMesureDTO> uniteDeMesureDTOs = new ArrayList<>();

	@ManagedProperty(value = "#{userService}")
	private IUserService userService;

	@ManagedProperty(value = "#{intrantService}")
	private IntrantService intrantService;

	@ManagedProperty(value = "#{categorieIntrantService}")
	private ICategorieIntrantService categorieIntrantService;

	@ManagedProperty(value = "#{uniteDeMesureService}")
	private IUniteDeMesureService uniteDeMesureService;

	@PostConstruct
	private void init() {
		initIntrantsDTOs();
		resetErrorMsgs();
		connectedUserDetails = retrievedetailsConnectedUser();
		loadIntrantsList();
		loadCategorieIntrantsList();
		loadUnitesDeMesureList();
	}

	public UserInfosDTO retrievedetailsConnectedUser() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userService.findUserByMatricule(user.getUsername());
	}

	public void loadCategorieIntrantsList() {
		categorieIntrantDTOs = categorieIntrantService.getAllCategoriesIntrants();
	}

	public void loadUnitesDeMesureList() {
		uniteDeMesureDTOs = uniteDeMesureService.getAllUnitesDeMesure();
	}

	public void loadIntrantsList() {
		intrants = intrantService.getAllIntrants();
	}

	public String deleteIntrant(long id) {
		resetErrorMsgs();
		Intrant intrant = intrantService.getIntrantById(id);
		if (intrant != null) {
			intrantService.deleteIntrant(id);
			setCreateIntrantSuccesMsg("L'intrant " + intrant.getLibelle() + " est supprimé avec succès");
		}
		loadIntrantsList();
		return "intrant.xhtml?faces-redirect=true";
	}

	public String updateIntrant(long id) {
		resetErrorMsgs();

		if (updateIntrantDto.getQuantite() == 0) {
			setCreateIntrantErreurMsg("La quantité est obligatoire");
			return "intrant.xhtml?faces-redirect=true";
		}
		if ("".equals(updateIntrantDto.getLibelle())) {
			setCreateIntrantErreurMsg("Le nom est obligatoire");
			return "intrant.xhtml?faces-redirect=true";
		}

		Intrant intrant = intrantService.getIntrantById(id);
		if (intrant != null) {
			intrant.setLibelle(updateIntrantDto.getLibelle());
			intrant.setQuantite(updateIntrantDto.getQuantite());
			intrantService.updateIntrant(intrant);
			setCreateIntrantSuccesMsg("L'intrant " + intrant.getLibelle() + " est modifié avec succès");
		}
		loadIntrantsList();
		updateIntrantDto = new IntrantDTO();
		return "intrant.xhtml?faces-redirect=true";
	}

	public String createIntrant() {
		resetErrorMsgs();
		if ("".equals(newIntrantDto.getLibelle())) {
			setCreateIntrantErreurMsg("Le nom est obligatoire");
			return "intrant.xhtml?faces-redirect=true";
		}
		if (categorieIntrantId == 0L) {
			setCreateIntrantErreurMsg("Vous devez choisir une catégorie !");
			return "intrant.xhtml?faces-redirect=true";
		}
		if (uniteDeMesureId == 0L) {
			setCreateIntrantErreurMsg("Vous devez choisir l'unité de mesure !");
			return "intrant.xhtml?faces-redirect=true";
		}

		//String base64 = Base64.getEncoder().encodeToString(newIntrantDto.getPictoFile().getContents());

		Intrant intrant = new Intrant(newIntrantDto.getLibelle(), 0.0, 0.0, 0.0, "", null);
		intrant.setCategorieIntrant(categorieIntrantService.getCategorieIntrantById(categorieIntrantId));
		intrant.setUniteDeMesure(uniteDeMesureService.getUniteDeMesureById(uniteDeMesureId));
		intrantService.createIntrant(intrant);
		setCreateIntrantSuccesMsg("L'intrant " + newIntrantDto.getLibelle() + " est crée avec succès");

		loadIntrantsList();
		newIntrantDto = new IntrantDTO();

		return "intrant.xhtml?faces-redirect=true";
	}

	private void initIntrantsDTOs() {
		newIntrantDto = new IntrantDTO();
	}

	public void resetErrorMsgs() {
		setCreateIntrantErreurMsg(null);
		setCreateIntrantSuccesMsg(null);
	}

	
	// AJAX UPDATE
	

	
	// catégorie
	Long idSelectedTypeIntrant;
	private List<IntrantDTO> intrantsSelectedCategie = new ArrayList<>();
	public void loadIntrantByCategorie(final AjaxBehaviorEvent event) {
		intrantsSelectedCategie = intrantService.getIntrantsByCategorie(idSelectedTypeIntrant);
	}
	
	// Variété
	Long idSelectedteIntrant;
	Long idSelectedVarieteIntrant;
	private List<IntrantDTO> intrantsSelectedVariete = new ArrayList<>();
	public void loadIntrantByVariete(final AjaxBehaviorEvent event) {
		//intrantsSelectedCategie = intrantService.getIntrantsByCategorie(idSelectedCategorieIntrant);
	}
}
