package sn.awi.pgca.web.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "dashboardMB")
@RequestScoped
public class DashboardManagedBean implements Serializable {

	private static final long serialVersionUID = 8578508939330121865L;

	private String dashboardGuichetierId = "0";

	public String getDashboardGuichetierId() {
		return dashboardGuichetierId;
	}

	public void setDashboardGuichetierId(String dashboardGuichetierId) {
		this.dashboardGuichetierId = dashboardGuichetierId;
	}

	public String switchToDashboardDefault() {
		dashboardGuichetierId = "0";
		return dashboardGuichetierId;
	}

	public String switchToDashboardToSurete() {
		dashboardGuichetierId = "1";
		return dashboardGuichetierId;
	}

	public String switchToDashboardGUGuichetier() {
		dashboardGuichetierId = "0";
		return dashboardGuichetierId;
	}

	public String switchToDashboardGUGreffier() {
		dashboardGuichetierId = "1";
		return dashboardGuichetierId;
	}

	public String switchToDashboardGUAgentSaisie() {
		dashboardGuichetierId = "2";
		return dashboardGuichetierId;
	}

	public String switchToDashboardGUGreffierEnChef() {
		dashboardGuichetierId = "3";
		return dashboardGuichetierId;
	}

	public String switchToDashboardGUSurete() {
		dashboardGuichetierId = "4";
		return dashboardGuichetierId;
	}
}
