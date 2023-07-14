package org.cages.moulinette.managedBean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.cages.moulinette.dto.UserInfosDTO;
import org.cages.moulinette.service.IUserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;

@ManagedBean(name = "DashboardControllerChs")
@RequestScoped
@Getter
@Setter
public class DashboardControllerChs {

	private UserInfosDTO connectedUserDetails = new UserInfosDTO();
	
	@ManagedProperty(value = "#{userService}")
	private IUserService userService;



	@PostConstruct
	private void initConfiguration() {
		connectedUserDetails = retrievedetailsConnectedUser();
	}

	public UserInfosDTO retrievedetailsConnectedUser() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userService.findUserByMatricule(user.getUsername());
	}
}
