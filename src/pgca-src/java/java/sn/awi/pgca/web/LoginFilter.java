package sn.awi.pgca.web;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sn.awi.pgca.web.bean.ConstantPGCA;

public class LoginFilter implements Filter, Serializable {
    private static long maxAge = 86400 * 30; // 30 days in seconds

	/**
	 * 
	 */
	private static final long serialVersionUID = -5772401352183482029L;
	/**
	 * Logger.
	 */
	private static final Log logger = LogFactory.getLog(LoginFilter.class);

	public void init(final FilterConfig filterConfig) throws ServletException {
        System.out.println("PGCA  : MISE EN CACHE DES RESSOURCES STATICS :///////////////");

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		
		  HttpServletResponse httpResponse = (HttpServletResponse) response;
	        String uri = ((HttpServletRequest) request).getRequestURI();
//	        if (uri.contains(".js") || uri.contains(".css") || uri.contains(".svg") || uri.contains(".gif")
//	                || uri.contains(".woff") || uri.contains(".png")) {
//	            httpResponse.setHeader("Cache-Control", "max-age=" + maxAge);
//	        }
	        
	        
		HttpServletRequest req = (HttpServletRequest) request;

		HttpSession session = req.getSession(false);
		String nomUser = (session != null) ? (String) session.getAttribute(ConstantPGCA.SESSION_NOM_UTILISATEUR) : null;
		Boolean userAuthorise = (session != null) ? true : null;
		boolean bUserAuthorise = (userAuthorise != null ? userAuthorise.booleanValue() : false);

		logger.warn(nomUser + " a accèdè à la page " + req.getRequestURI() + " " + " avec autorisation   <"
				+ bUserAuthorise + ">");
		
		
		if (req.getRequestURI().endsWith("/connexion.xhtml")
				|| req.getRequestURI().endsWith("/modifierPassword.xhtml")) {
			chain.doFilter(request, response);
		} else if (nomUser == null || nomUser.isEmpty()) {
			logger.warn(
					"Tentative d'ouverture d'une page nécessitant une connexion, redirection vers la page de login ! "
							+ req.getRequestURI());
			String loginURL = req.getContextPath() + "/iu/connexion.xhtml";
			HttpServletResponse resp = (HttpServletResponse) response;
			resp.sendRedirect(loginURL);
		} else if (!bUserAuthorise) {
			logger.warn(
					"Tentative d'ouverture d'une page nécessitant une modifcation de mot de passe, redirection vers la page de modification ! ");
			String loginURL = req.getContextPath() + "/iu/modifierPassword.xhtml";
			HttpServletResponse resp = (HttpServletResponse) response;
			resp.sendRedirect(loginURL);
		} else {
			logger.info("Authentification OK ::::  User :" + nomUser);
			chain.doFilter(request, response);
		}
	}


	public void destroy() {
	}

}
