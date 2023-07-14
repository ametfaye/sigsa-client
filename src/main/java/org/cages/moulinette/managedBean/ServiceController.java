package org.cages.moulinette.managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.cages.moulinette.dto.ServiceDTO;
import org.cages.moulinette.dto.UserInfosDTO;
import org.cages.moulinette.model.Adresse;
import org.cages.moulinette.model.Contact;
import org.cages.moulinette.model.EntitePublicRef;
import org.cages.moulinette.model.Service;
import org.cages.moulinette.service.IAdresseService;
import org.cages.moulinette.service.IContactService;
import org.cages.moulinette.service.IEntitePublicRefService;
import org.cages.moulinette.service.IServiceReferentialService;
import org.cages.moulinette.service.IUserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;

@ManagedBean(name = "ServiceController")
@SessionScoped
@Getter
@Setter
public class ServiceController {
	
	private String createServiceErreurMsg;
	private String createServiceSuccesMsg;
	
	private long serviceToCreateEtablissementId;

	private UserInfosDTO connectedUserDetails = new UserInfosDTO();
	private ServiceDTO updateServiceDto = new ServiceDTO();
	private ServiceDTO newServiceDto = new ServiceDTO();
	
	private List<Service> services = new ArrayList<>();
	private List<EntitePublicRef> entitePublicRefs = new ArrayList<>();
	private List<Adresse> adresses = new ArrayList<>();
	private List<Contact> contacts = new ArrayList<>();

	@ManagedProperty(value = "#{userService}")
	private IUserService userService;

	@ManagedProperty(value = "#{serviceReferentialService}")
	private IServiceReferentialService serviceReferentialService;
	
	@ManagedProperty(value = "#{entitePublicRefService}")
	private IEntitePublicRefService entitePublicRefService;
	
	@ManagedProperty(value = "#{adresseService}")
	private IAdresseService adresseService;
	
	@ManagedProperty(value = "#{contactService}")
	private IContactService contactService;

	@PostConstruct
	private void init() {
		initServiceDTOs();
		resetErrorMsgs();
		connectedUserDetails = retrievedetailsConnectedUser();
		loadEntitePublicRefList();
		loadServicesList();
	}

	public UserInfosDTO retrievedetailsConnectedUser() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userService.findUserByMatricule(user.getUsername());
	}

	public void loadServicesList() {
		services = serviceReferentialService.getAllServices();
	}
	
	private void loadEntitePublicRefList() {
		entitePublicRefs = entitePublicRefService.getAllEntitePublicRefs();
	}

	public String deleteService(long srvId) {
		resetErrorMsgs();
		Service service = serviceReferentialService.getServiceById(srvId);
		if (service != null) {
			serviceReferentialService.deleteService(srvId);	
			setCreateServiceSuccesMsg("Le service "+service.getLibelle()+" est supprimé avec succès");
		}
		loadServicesList();
		return "adminServicesList.xhtml?faces-redirect=true";
	}
	
	public String updateService(long srvId) {
		resetErrorMsgs();
		if ("".equals(updateServiceDto.getLibelle())) {
			setCreateServiceErreurMsg("Le nom est obligatoire");
			return "adminServicesList.xhtml?faces-redirect=true";
		}
		if ("".equals(updateServiceDto.getCode())) {
			setCreateServiceErreurMsg("Le code est obligatoire");
			return "adminServicesList.xhtml?faces-redirect=true";
		}
		Service service = serviceReferentialService.getServiceById(srvId);
		if (service != null) {
			service.setLibelle(updateServiceDto.getLibelle());
			serviceReferentialService.updateService(service);
			setCreateServiceSuccesMsg("Le service "+service.getLibelle()+" est modifié avec succès");
		}
		loadServicesList();
		updateServiceDto = new ServiceDTO();
		return "adminServicesList.xhtml?faces-redirect=true";
	}

	public String createService() {
		resetErrorMsgs();
		if ("".equals(newServiceDto.getLibelle())) {
			setCreateServiceErreurMsg("Le nom est obligatoire");
			return "adminServicesList.xhtml?faces-redirect=true";
		}
		if ("".equals(newServiceDto.getCode())) {
			setCreateServiceErreurMsg("Le code est obligatoire");
			return "adminServicesList.xhtml?faces-redirect=true";
		}
		if (serviceToCreateEtablissementId == 0L) {
			setCreateServiceErreurMsg("Vous devez choisir un établissement !");
			return "adminServicesList.xhtml?faces-redirect=true";
		}
		
		Service service = serviceReferentialService.getServiceByCode(newServiceDto.getCode());
		if (service != null) {
			setCreateServiceErreurMsg("Le service "+newServiceDto.getCode()+" existe déjà !");
			return "adminServicesList.xhtml?faces-redirect=true";
		} else {
			EntitePublicRef entite = entitePublicRefService.getEntitePublicRefById(serviceToCreateEtablissementId);
			Service serviceToCreate = new Service(newServiceDto.getCode(), newServiceDto.getLibelle(), null, null, entite);
			serviceReferentialService.createService(serviceToCreate);
			setCreateServiceSuccesMsg("Le service "+newServiceDto.getLibelle()+" est crée avec succès");
		}
		loadServicesList();
		newServiceDto = new ServiceDTO();
		return "adminServicesList.xhtml?faces-redirect=true";
	}
	
	private void initServiceDTOs() {
		newServiceDto = new ServiceDTO();
		updateServiceDto = new ServiceDTO();
	}

	public void resetErrorMsgs() {
		setCreateServiceErreurMsg(null);
		setCreateServiceSuccesMsg(null);
	}

}
