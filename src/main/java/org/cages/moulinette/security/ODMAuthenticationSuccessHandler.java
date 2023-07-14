package org.cages.moulinette.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.cages.moulinette.enumeration.EnumRole;
import org.cages.moulinette.model.HistoriqueDeConnexion;
import org.cages.moulinette.model.UserInfos;
import org.cages.moulinette.service.IHistoriqueCnxService;
import org.cages.moulinette.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class ODMAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private IHistoriqueCnxService historiqueCnxService;
	
	@Autowired
	private IUserService userService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) auth.getPrincipal();
		UserInfos userInfos = userService.findUserByIdentifiant(user.getUsername());
		historiqueCnxService.save(new HistoriqueDeConnexion(new Date(), user.getUsername(), userInfos));
		if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(EnumRole.TBADM.getCode()))) {
			response.sendRedirect(request.getContextPath() + "/dashboardAdmin.xhtml");
		} else if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(EnumRole.TBCHS.getCode()))) {
			response.sendRedirect(request.getContextPath() + "/dashboardChs.xhtml");
		} else if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(EnumRole.TBGS.getCode()))) {
			response.sendRedirect(request.getContextPath() + "/dashboardGs.xhtml");
		} else if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(EnumRole.TBSGG.getCode()))) {
			response.sendRedirect(request.getContextPath() + "/dashboardSgg.xhtml");
		} else if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(EnumRole.TBSG.getCode()))) {
				response.sendRedirect(request.getContextPath() + "/dashboardSg.xhtml");
		} else {
			response.sendRedirect(request.getContextPath() + "/login.xhtml");
		}
		clearAuthenticationAttributes(request);
	}

	protected final void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		}
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}
}
