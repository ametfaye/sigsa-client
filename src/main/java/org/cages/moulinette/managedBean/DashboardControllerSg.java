package org.cages.moulinette.managedBean;

import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.cages.moulinette.dto.UserInfosDTO;
import org.cages.moulinette.service.IUserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;

@ManagedBean(name = "DashboardControllerSg")
@SessionScoped
@Getter
@Setter
public class DashboardControllerSg {

	private String statsOdmEnCours;
	private String statsOdmPlanifiees;
	private String statsOdmEffectuees;
	
	private Boolean siNational = true;
	private int exercice;
	
	private UserInfosDTO connectedUserDetails = new UserInfosDTO();
	


	@ManagedProperty(value = "#{userService}")
	private IUserService userService;



	@PostConstruct
	private void initConfiguration() {
		connectedUserDetails = retrievedetailsConnectedUser();
		exercice =  Calendar.getInstance().get(Calendar.YEAR);
	}

	public UserInfosDTO retrievedetailsConnectedUser() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userService.findUserByMatricule(user.getUsername());
	}

}
