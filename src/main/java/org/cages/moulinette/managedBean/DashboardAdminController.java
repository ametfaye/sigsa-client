package org.cages.moulinette.managedBean;

import java.util.List;

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

@ManagedBean(name = "DashboardAdminController")
@RequestScoped
@Getter
@Setter
public class DashboardAdminController {

	private int profilsActifs;
	private int profilsInactifs;

	private UserInfosDTO connectedUserDetails = new UserInfosDTO();

	private List<UserInfosDTO> usersLists;

	@ManagedProperty(value = "#{userService}")
	private IUserService userService;

	@PostConstruct
	private void initConfiguration() {
		connectedUserDetails = retrievedetailsConnectedUser();
		profilsActifs = userService.countActifsUsers();
		profilsInactifs = userService.countInactifsUsers();	
		loadUsersList();
	}

	private void loadUsersList() {
		usersLists = userService.loadAllUsersDTO(connectedUserDetails.getIdUser());
	}

	public UserInfosDTO retrievedetailsConnectedUser() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userService.findUserByMatricule(user.getUsername());
	}
}
