package sn.awi.pgca.utils;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import sn.awi.pgca.business.service.mock.ParametreParPays;
import sn.awi.pgca.web.bean.ConstantPGCA;

public class RecupValuesInSession implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 5706621449373104220L;

	public static Object getSessionDataByTag(String tag) throws Exception {
		if (tag == null)
			throw new Exception("Error d'acces de session le tag est");

		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

		if (request != null) {
			HttpSession session = request.getSession(false);

			if (session != null) {
//				System.out.println("Session is not null -> try to get attribute ->" + tag + "from session");
				if (session.getAttribute(tag) != null) {
//					System.out.println("Nom User Connecté " + (String) session.getAttribute(tag));
					return session.getAttribute(tag);
				}
			}
		}
		return null;
	}

	public static String getEntityJuridiqueDataByTag() throws Exception {
		return (String)getSessionDataByTag(ConstantPGCA.SESSION_ENTITE_JURIDIQUE);
	}

	public static String getRegionDataByTag() throws Exception {
		return (String)getSessionDataByTag(ConstantPGCA.SESSION_REGION); 
	}
	
	public static String getCodeEntityJuridiqueDataByTag() throws Exception {
		return (String)getSessionDataByTag(ConstantPGCA.SESSION_CODE_3_L_ENJU);
	}
	
	public static String getCodeRegionDataByTag() throws Exception {
		return (String)getSessionDataByTag(ConstantPGCA.SESSION_CODE_REGION);
	}
	
	public static String getCodePaysDataByTag() throws Exception {
		return (String)getSessionDataByTag(ConstantPGCA.SESSION_CODE_PAYS);
	}
	
	
}
