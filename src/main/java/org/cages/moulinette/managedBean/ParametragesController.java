package org.cages.moulinette.managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.cages.moulinette.dto.ParametreModuleDTO;
import org.cages.moulinette.dto.UserInfosDTO;
import org.cages.moulinette.model.ModuleApplicatif;
import org.cages.moulinette.model.ParametreModule;
import org.cages.moulinette.model.Service;
import org.cages.moulinette.service.IModuleApplicatifService;
import org.cages.moulinette.service.IParametreModuleService;
import org.cages.moulinette.service.IServiceReferentialService;
import org.cages.moulinette.service.IUserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;

@ManagedBean(name = "ParametragesController")
@SessionScoped
@Getter
@Setter
public class ParametragesController {
	
	private String createParametragesErreurMsg;
	private String createParametragesSuccesMsg;
	
	private long serviceToCreateId;
	
	private ParametreModuleDTO newParametreModuleDto = new ParametreModuleDTO();

	private UserInfosDTO connectedUserDetails = new UserInfosDTO();
	
	private List<ParametreModule> parametres = new ArrayList<>();
	private List<Service> services = new ArrayList<>();

	@ManagedProperty(value = "#{userService}")
	private IUserService userService;

	@ManagedProperty(value = "#{parametreModuleService}")
	private IParametreModuleService parametreModuleService;

	@ManagedProperty(value = "#{serviceReferentialService}")
	private IServiceReferentialService serviceReferentialService;

	@ManagedProperty(value = "#{moduleApplicatifService}")
	private IModuleApplicatifService moduleApplicatifService;

	@PostConstruct
	private void init() {
		resetErrorMsgs();
		connectedUserDetails = retrievedetailsConnectedUser();
		loadParametresList();
		loadServicesList();
	}

	public UserInfosDTO retrievedetailsConnectedUser() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userService.findUserByMatricule(user.getUsername());
	}

	public void loadParametresList() {
		parametres = parametreModuleService.getParametresModulesMission();
	}

	public void loadServicesList() {
		services = serviceReferentialService.getAllServices();
	}
	
	public void onCellEdit(ParametreModule parametreModule) {
        parametreModuleService.updateParametresModule(parametreModule);
    }
	
	public String createParametreModule() {
		resetErrorMsgs();
		if ("".equals(newParametreModuleDto.getCode())) {
			setCreateParametragesErreurMsg("Le code est obligatoire");
			return "adminParametragesMission.xhtml?faces-redirect=true";
		}
		if ("".equals(newParametreModuleDto.getLibelle())) {
			setCreateParametragesErreurMsg("Le libellé est obligatoire");
			return "adminParametragesMission.xhtml?faces-redirect=true";
		}
		if ("".equals(newParametreModuleDto.getValeur())) {
			setCreateParametragesErreurMsg("La valeur est obligatoire");
			return "adminParametragesMission.xhtml?faces-redirect=true";
		}
		if (serviceToCreateId == 0L) {
			setCreateParametragesErreurMsg("Vous devez choisir un service !");
			return "adminParametragesMission.xhtml?faces-redirect=true";
		}
		Service service = serviceReferentialService.getServiceById(serviceToCreateId);
		ModuleApplicatif moduleApplicatif = moduleApplicatifService.findFirstByMdaCode("ODM");
		ParametreModule param = new ParametreModule(newParametreModuleDto.getCode(), newParametreModuleDto.getLibelle(), newParametreModuleDto.getValeur(), service, moduleApplicatif);
		parametreModuleService.createParametreModule(param);
		setCreateParametragesSuccesMsg("Le service "+newParametreModuleDto.getLibelle()+" est crée avec succès");
		loadParametresList();
		newParametreModuleDto = new ParametreModuleDTO();
		return "adminParametragesMission.xhtml?faces-redirect=true";
	}
	
	public String deleteParametre(String code) {
		resetErrorMsgs();
		ParametreModule parametreModule = parametreModuleService.getParametreModuleByCode(code);
		if (parametreModule != null) {
			parametreModuleService.deleteParametre(parametreModule);	
			setCreateParametragesSuccesMsg("Le paramètre "+parametreModule.getPamLibelle()+" est supprimé avec succès");
		}
		loadParametresList();
		return "adminParametragesMission.xhtml?faces-redirect=true";	
	}

	public void resetErrorMsgs() {
		setCreateParametragesErreurMsg(null);
		setCreateParametragesSuccesMsg(null);
	}

}
