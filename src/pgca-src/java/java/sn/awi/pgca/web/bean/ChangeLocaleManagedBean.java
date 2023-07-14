package sn.awi.pgca.web.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="changeLocaleMB")
@SessionScoped
public class ChangeLocaleManagedBean implements Serializable {

	private static final long serialVersionUID = 5999273633868374889L;
	
	// la locale des pages
	private String locale = "fr";

	public ChangeLocaleManagedBean() {
	}

	public String setFrenchLocale() {
		locale = "fr";
		return null;
	}

	public String setEnglishLocale() {
		locale = "en";
		return null;
	}

	public String getLocale() {
		return locale;
	}
}