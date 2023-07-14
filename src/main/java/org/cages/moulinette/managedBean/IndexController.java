package org.cages.moulinette.managedBean;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.cages.moulinette.dto.UserInfosDTO;
import org.cages.moulinette.enumeration.EnumRole;
import org.cages.moulinette.service.IUserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.Getter;
import lombok.Setter;

@ManagedBean(name = "IndexController")
@SessionScoped
@Getter
@Setter
public class IndexController {

	private UserInfosDTO connectedUserDetails = new UserInfosDTO();

	@ManagedProperty(value = "#{userService}")
	private IUserService userService;

	@PostConstruct
	private void init() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			String page = "/odm/login.xhtml";
			if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(EnumRole.TBADM.getCode())))
				page = "/odm/dashboardAdmin.xhtml";
			else if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(EnumRole.TBCHS.getCode())))
				page = "/odm/dashboardChs.xhtml";
			else if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(EnumRole.TBGS.getCode())))
				page = "/odm/dashboardGs.xhtml";
			else if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(EnumRole.TBSGG.getCode())))
				page = "/odm/dashboardSgg.xhtml";
			else if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(EnumRole.TBSG.getCode())))
				page = "/odm/dashboardSg.xhtml";
			else 
				page = "/odm/login.xhtml";
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect(page);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String redirect() {
		return null;
		
	}
}
