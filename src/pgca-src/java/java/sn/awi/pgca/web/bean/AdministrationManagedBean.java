package sn.awi.pgca.web.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.LogFactory;

import sn.awi.pgca.business.service.IAdministrationService;
import sn.awi.pgca.web.dto.GedTypeDocumentDTO;

@ManagedBean(name = "adminMB")
@RequestScoped
public class AdministrationManagedBean implements Serializable {
	

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -465585234130026791L;

	private static final org.apache.commons.logging.Log	LOG								= LogFactory.getLog(AdministrationManagedBean.class);

	private long																				idSelectedTypePJ;

	@ManagedProperty(value = "#{adminService}")
	private IAdministrationService											adminService;

	private GedTypeDocumentDTO													typePJDTO					= new GedTypeDocumentDTO();
	List<GedTypeDocumentDTO>														typePJDTOs				= null;

	private GedTypeDocumentDTO													selectedTypePJDTO	= new GedTypeDocumentDTO();

	public AdministrationManagedBean() {
	}

	public String initCreateTypePJ() {
		return "creerTypePieceJointe";
	}




	// methode utilitaire pour l'affichage des messages
	public String showMessage(boolean status, String val, String error) {
		if (status) {
			// FacesMessage msg = new FacesMessage(val + " enregistré ");
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "MAJ Type pièce jointe : ", "Le type de pièce jointe  " + val + " a été enregistré.");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return ConstantPGCA.SUCCESS;
		} else {
			// FacesMessage msg = new FacesMessage("echec enregistrement." +
			// val);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "MAJ Type pièce jointe : ", "Echec de l'enregistrement : " + error + ".");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return ConstantPGCA.ERROR;
		}
	}

	public String Cancel() {
		return ConstantPGCA.RETURN;
	}

	public long getIdSelectedTypePJ() {
		return idSelectedTypePJ;
	}

	public void setIdSelectedTypePJ(long idSelectedTypePJ) {
		this.idSelectedTypePJ = idSelectedTypePJ;
	}

	public GedTypeDocumentDTO getTypePJDTO() {
		return typePJDTO;
	}

	public void setTypePJDTO(GedTypeDocumentDTO typePJDTO) {
		this.typePJDTO = typePJDTO;
	}

	public List<GedTypeDocumentDTO> getTypePJDTOs() {
		return typePJDTOs;
	}

	public void setTypePJDTOs(List<GedTypeDocumentDTO> typePJDTOs) {
		this.typePJDTOs = typePJDTOs;
	}

	public GedTypeDocumentDTO getSelectedTypePJDTO() {
		return selectedTypePJDTO;
	}

	public void setSelectedTypePJDTO(GedTypeDocumentDTO selectedTypePJDTO) {
		this.selectedTypePJDTO = selectedTypePJDTO;
	}

	public IAdministrationService getAdminService() {
		return adminService;
	}

	public void setAdminService(IAdministrationService adminService) {
		this.adminService = adminService;
	}

}
