package sn.awi.pgca.web.dto;

import java.io.Serializable;
import java.util.List;

public class EntiteJuridiqueDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4350660425120902595L;

	private Long id;

	private String libelle;

	private String superficie;
	private String superficieLibre; 
	private String superficieOccupe; 
	private String gerant; 
	

	private String idType;
	private String idTypePrincipal; 
	private String idRegion; 
	
	private String idPays;
	private String labelRegion;
	private String labelPays;
	private String labelType;
	private String adresseId;
	private String contactId;
	private String code3l;
	private ContactDTO contact;
	private AdresseDTO adresse;
	private ParamTarificationDTO tarificationDTO;
	private ParamTarificationDTO tarificationDTO2;
	private List<ParamTarificationDTO> tarificationDTOs;

	public EntiteJuridiqueDTO() {
		contact = new ContactDTO();
		adresse = new AdresseDTO();
		tarificationDTO = new ParamTarificationDTO();
		tarificationDTO2 = new ParamTarificationDTO();
	}

	public String getIdType() {
		return idType;
	}

	public String getIdRegion() {
		return idRegion;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public void setIdRegion(String idRegion) {
		this.idRegion = idRegion;
	}

	public ContactDTO getContact() {
		return contact;
	}

	public void setContact(ContactDTO contact) {
		this.contact = contact;
	}

	public AdresseDTO getAdresse() {
		return adresse;
	}

	public void setAdresse(AdresseDTO adresse) {
		this.adresse = adresse;
	}

	public Long getId() {
		return id;
	}

	public String getSuperficie() {
		return superficie;
	}

	public void setSuperficie(String superficie) {
		this.superficie = superficie;
	}

	public String getSuperficieLibre() {
		return superficieLibre;
	}

	public void setSuperficieLibre(String superficieLibre) {
		this.superficieLibre = superficieLibre;
	}

	public String getSuperficieOccupe() {
		return superficieOccupe;
	}

	public void setSuperficieOccupe(String superficieOccupe) {
		this.superficieOccupe = superficieOccupe;
	}

	public String getGerant() {
		return gerant;
	}

	public void setGerant(String gerant) {
		this.gerant = gerant;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLibelle() {
		return libelle;
	}

	public String getLabelType() {
		return labelType;
	}

	public void setLabelType(String labelType) {
		this.labelType = labelType;
	}

	public String getLabelRegion() {
		return labelRegion;
	}

	public void setLabelRegion(String labelRegion) {
		this.labelRegion = labelRegion;
	}

	public String getIdTypePrincipal() {
		return idTypePrincipal;
	}

	public void setIdTypePrincipal(String idTypePrincipal) {
		this.idTypePrincipal = idTypePrincipal;
	}

	public String getLabelPays() {
		return labelPays;
	}

	public void setLabelPays(String labelPays) {
		this.labelPays = labelPays;
	}

	public String getIdPays() {
		return idPays;
	}

	public void setIdPays(String idPays) {
		this.idPays = idPays;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getAdresseId() {
		return adresseId;
	}

	public void setAdresseId(String adresseId) {
		this.adresseId = adresseId;
	}

	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	public String getCode3l() {
		return code3l;
	}

	public void setCode3l(String code3l) {
		this.code3l = code3l;
	}

	public List<ParamTarificationDTO> getTarificationDTOs() {
		return tarificationDTOs;
	}

	public void setTarificationDTOs(List<ParamTarificationDTO> lTarificationDTOs) {
		this.tarificationDTOs = lTarificationDTOs;
	}

	public ParamTarificationDTO getTarificationDTO2() {
		return tarificationDTO2;
	}

	public void setTarificationDTO2(ParamTarificationDTO tarificationDTO2) {
		this.tarificationDTO2 = tarificationDTO2;
	}

	public ParamTarificationDTO getTarificationDTO() {
		return tarificationDTO;
	}

	public void setTarificationDTO(ParamTarificationDTO tarificationDTO) {
		this.tarificationDTO = tarificationDTO;
	}

}
