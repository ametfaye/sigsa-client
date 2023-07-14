package org.cages.moulinette.managedBean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.cages.moulinette.service.IFooterService;

import lombok.Getter;
import lombok.Setter;

@ManagedBean(name = "FooterController")
@SessionScoped
@Getter
@Setter
public class FooterController {
	
	private String appVersion;
	
	@ManagedProperty(value = "#{footerService}")
	private IFooterService footerService;
	
	@PostConstruct
	private void init() {
		appVersion = footerService.getAppVersion();
	}
}
