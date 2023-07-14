package org.cages.moulinette.managedBean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.cages.moulinette.dto.UserInfosDTO;
import org.cages.moulinette.service.IUserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;

@ManagedBean(name = "TopBarController")
@SessionScoped
@Getter
@Setter
public class TopBarController {

	private UserInfosDTO connectedUserDetails = new UserInfosDTO();
	


	@ManagedProperty(value = "#{userService}")
	private IUserService userService;



	@PostConstruct
	private void init() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			connectedUserDetails = userService.findUserByMatricule(((User) auth.getPrincipal()).getUsername());
			
		}
	}
}
