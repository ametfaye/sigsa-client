package org.cages.moulinette.managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIOutput;
import javax.faces.event.AjaxBehaviorEvent;

import org.cages.moulinette.dto.SousServiceDTO;
import org.cages.moulinette.dto.UserInfosDTO;
import org.cages.moulinette.model.EntitePublicRef;
import org.cages.moulinette.model.Service;
import org.cages.moulinette.model.SousService;
import org.cages.moulinette.service.IEntitePublicRefService;
import org.cages.moulinette.service.IServiceReferentialService;
import org.cages.moulinette.service.ISousServiceRefService;
import org.cages.moulinette.service.IUserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;

@ManagedBean(name = "SousServiceController")
@SessionScoped
@Getter
@Setter
public class SousServiceController {

	private String createSousServiceErreurMsg;
	private String createSousServiceSuccesMsg;
	private long sousServiceToCreateServiceId;

	private UserInfosDTO connectedUserDetails = new UserInfosDTO();
	private SousServiceDTO newSousServiceDto = new SousServiceDTO();
	private SousServiceDTO updateSousServiceDto = new SousServiceDTO();
	private List<Service> services = new ArrayList<>();
	private long sousServiceToCreateEntitePubliqueId;

	private List<EntitePublicRef> entitePublicRefs = new ArrayList<>();

	private List<SousService> sousServices = new ArrayList<>();

	@ManagedProperty(value = "#{userService}")
	private IUserService userService;

	@ManagedProperty(value = "#{sousServiceRefService}")
	private ISousServiceRefService sousServiceRefService;

	@ManagedProperty(value = "#{serviceReferentialService}")
	private IServiceReferentialService serviceReferentialService;
	
	@ManagedProperty(value = "#{entitePublicRefService}")
	private IEntitePublicRefService entitePublicRefService;

	@PostConstruct
	private void init() {
		initEntitePubliqueDTOs();
		resetErrorMsgs();
		connectedUserDetails = retrievedetailsConnectedUser();
		loadSousServiceList();
		loadEntitePublicRefList();
	}

	public UserInfosDTO retrievedetailsConnectedUser() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userService.findUserByMatricule(user.getUsername());
	}

	public void loadSousServiceList() {
		sousServices = sousServiceRefService.getAllSousServices();
	}

	private void loadEntitePublicRefList() {
		entitePublicRefs = entitePublicRefService.getAllEntitePublicRefs();
	}

	public String deleteSousService(long id) {
		resetErrorMsgs();
		SousService sousService = sousServiceRefService.getSousServiceById(id);
		if (sousService != null) {
			sousServiceRefService.deleteSousService(id);
			setCreateSousServiceSuccesMsg(
					"L'entité publique " + sousService.getLibelle() + " est supprimé avec succès");
		}
		loadSousServiceList();
		return "sousService.xhtml?faces-redirect=true";
	}

	public String updateSousService(long id) {
		resetErrorMsgs();
		if ("".equals(updateSousServiceDto.getNom())) {
			setCreateSousServiceErreurMsg("Le nom est obligatoire");
			return "sousService.xhtml?faces-redirect=true";
		}

		if ("".equals(updateSousServiceDto.getCode())) {
			setCreateSousServiceErreurMsg("Le code est obligatoire");
			return "sousService.xhtml?faces-redirect=true";
		}

		if (sousServiceToCreateServiceId == 0L) {
			setCreateSousServiceErreurMsg("Vous devez choisir un service !");
			return "sousService.xhtml?faces-redirect=true";
		}

		if (sousServiceToCreateEntitePubliqueId == 0L) {
			setCreateSousServiceErreurMsg("Vous devez choisir un établissement !");
			return "sousService.xhtml?faces-redirect=true";
		}

		SousService sousService = sousServiceRefService.getSousServiceById(id);
		if (sousService != null) {
			sousService.setLibelle(updateSousServiceDto.getNom());
			if (sousService.getCode().equals(updateSousServiceDto.getCode())) {
				setCreateSousServiceErreurMsg("Sous Service " + newSousServiceDto.getCode() + " existe déjà !");
				return "sousService.xhtml?faces-redirect=true";
			}
			sousService.setCode(updateSousServiceDto.getCode());
			sousServiceRefService.updateSousService(sousService);
			setCreateSousServiceSuccesMsg("Sous Service " + sousService.getLibelle() + " est modifié avec succès");
		}
		loadSousServiceList();
		updateSousServiceDto = new SousServiceDTO();
		return "sousService.xhtml?faces-redirect=true";
	}

	public String createSousService() {
		resetErrorMsgs();
		if ("".equals(newSousServiceDto.getNom())) {
			setCreateSousServiceErreurMsg("Le nom est obligatoire");
			return "sousService.xhtml?faces-redirect=true";
		}
		if ("".equals(newSousServiceDto.getCode())) {
			setCreateSousServiceErreurMsg("Le code est obligatoire");
			return "sousService.xhtml?faces-redirect=true";
		}

		if (sousServiceToCreateServiceId == 0L) {
			setCreateSousServiceErreurMsg("Vous devez choisir un service !");
			return "sousService.xhtml?faces-redirect=true";
		}

		if (sousServiceToCreateEntitePubliqueId == 0L) {
			setCreateSousServiceErreurMsg("Vous devez choisir un établissement !");
			return "sousService.xhtml?faces-redirect=true";
		}

		SousService sousService = sousServiceRefService.getSousServiceByCode(newSousServiceDto.getCode());
		if (sousService != null) {
			setCreateSousServiceErreurMsg("Sous Service " + newSousServiceDto.getCode() + " existe déjà !");
			return "sousService.xhtml?faces-redirect=true";
		} else {
			Service service = serviceReferentialService.getServiceById(sousServiceToCreateServiceId);
			SousService sousServiceToCreate = new SousService(newSousServiceDto.getCode(), newSousServiceDto.getNom(),
					service);
			sousServiceRefService.createSousService(sousServiceToCreate);
			setCreateSousServiceSuccesMsg("Sous Service " + newSousServiceDto.getNom() + " est crée avec succès");
		}
		loadSousServiceList();
		newSousServiceDto = new SousServiceDTO();

		return "sousService.xhtml?faces-redirect=true";
	}

	private void initEntitePubliqueDTOs() {
		newSousServiceDto = new SousServiceDTO();
	}

	public String upSousService() {
		newSousServiceDto = new SousServiceDTO();
		return "sousService.xhtml?faces-redirect=true";
	}

	public void entitePublicRefsSelectionChanged(final AjaxBehaviorEvent event) {
		long id = (long) ((UIOutput) event.getSource()).getValue();
		services = serviceReferentialService.getServicesByEntitePublicRefsId(id);
	}

	public void resetErrorMsgs() {
		setCreateSousServiceErreurMsg(null);
		setCreateSousServiceSuccesMsg(null);
	}

}
