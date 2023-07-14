package sn.awi.pgca.web.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MiseEnplaceDTOCommune implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3311897253615919905L;

	private Long idMiseEnplaceAeffectuer;
	
	private Long idCommune;
	private String nomCommune;
	private String pointdeVente;
	private Long pointdeVenteId;
	private String pointdeVenteDepartement;
	private Long   pointdeVenteDepartementId;
	private String pointdeVenteCommune;
	private Long   pointdeVenteCommuneId;
	private String pointdeVenteRegion;
	private Long   pointdeVenteRegionId;
	private String pointdeVenteAdresse;
	private String pointdeVenteGerant;
	private String pointdeVenteGerantTel;

	
	private String nbPointDeVente;
	
	private double totalCumul;
	
	private double totalQuoto;
	
	private double totalReliquat;
	
	private double totalMiseEnPlace;
	
	
	List<MiseEnplaceDTOPointDeVente> listDesPointDeVente ;

	public Long getIdCommune() {
		return idCommune;
	}

	public void setIdCommune(Long idCommune) {
		this.idCommune = idCommune;
	}

	public String getNomCommune() {
		return nomCommune;
	}

	public void setNomCommune(String nomCommune) {
		this.nomCommune = nomCommune;
	}

	public String getNbPointDeVente() {
		return nbPointDeVente;
	}

	public void setNbPointDeVente(String nbPointDeVente) {
		this.nbPointDeVente = nbPointDeVente;
	}

	public List<MiseEnplaceDTOPointDeVente> getListDesPointDeVente() {
		if(null == listDesPointDeVente)
			return new ArrayList<MiseEnplaceDTOPointDeVente>();
		
		return listDesPointDeVente;
	}

	public double getTotalCumul() {
		return totalCumul;
	}

	public void setTotalCumul(double totalCumul) {
		this.totalCumul = totalCumul;
	}

	public double getTotalQuoto() {
		return totalQuoto;
	}

	public void setTotalQuoto(double totalQuoto) {
		this.totalQuoto = totalQuoto;
	}

	public double getTotalMiseEnPlace() {
		return totalMiseEnPlace;
	}

	public void setTotalMiseEnPlace(double totalMiseEnPlace) {
		this.totalMiseEnPlace = totalMiseEnPlace;
	}

	public void setListDesPointDeVente(List<MiseEnplaceDTOPointDeVente> listDesPointDeVente) {
		this.listDesPointDeVente = listDesPointDeVente;
	}

	public String getPointdeVenteDepartement() {
		return pointdeVenteDepartement;
	}

	public void setPointdeVenteDepartement(String pointdeVenteDepartement) {
		this.pointdeVenteDepartement = pointdeVenteDepartement;
	}

	public Long getPointdeVenteDepartementId() {
		return pointdeVenteDepartementId;
	}

	public Long getIdMiseEnplaceAeffectuer() {
		return idMiseEnplaceAeffectuer;
	}

	public void setIdMiseEnplaceAeffectuer(Long idMiseEnplaceAeffectuer) {
		this.idMiseEnplaceAeffectuer = idMiseEnplaceAeffectuer;
	}

	public void setPointdeVenteDepartementId(Long pointdeVenteDepartementId) {
		this.pointdeVenteDepartementId = pointdeVenteDepartementId;
	}

	public double getTotalReliquat() {
		return totalReliquat;
	}

	public void setTotalReliquat(double totalReliquat) {
		this.totalReliquat = totalReliquat;
	}

	public String getPointdeVenteCommune() {
		return pointdeVenteCommune;
	}

	public void setPointdeVenteCommune(String pointdeVenteCommune) {
		this.pointdeVenteCommune = pointdeVenteCommune;
	}

	public Long getPointdeVenteCommuneId() {
		return pointdeVenteCommuneId;
	}

	public void setPointdeVenteCommuneId(Long pointdeVenteCommuneId) {
		this.pointdeVenteCommuneId = pointdeVenteCommuneId;
	}

	public String getPointdeVenteAdresse() {
		return pointdeVenteAdresse;
	}

	public void setPointdeVenteAdresse(String pointdeVenteAdresse) {
		this.pointdeVenteAdresse = pointdeVenteAdresse;
	}

	public String getPointdeVenteGerant() {
		return pointdeVenteGerant;
	}

	public void setPointdeVenteGerant(String pointdeVenteGerant) {
		this.pointdeVenteGerant = pointdeVenteGerant;
	}

	public String getPointdeVenteGerantTel() {
		return pointdeVenteGerantTel;
	}

	public void setPointdeVenteGerantTel(String pointdeVenteGerantTel) {
		this.pointdeVenteGerantTel = pointdeVenteGerantTel;
	}

	public String getPointdeVente() {
		return pointdeVente;
	}

	public String getPointdeVenteRegion() {
		return pointdeVenteRegion;
	}

	public void setPointdeVenteRegion(String pointdeVenteRegion) {
		this.pointdeVenteRegion = pointdeVenteRegion;
	}

	public Long getPointdeVenteRegionId() {
		return pointdeVenteRegionId;
	}

	public void setPointdeVenteRegionId(Long pointdeVenteRegionId) {
		this.pointdeVenteRegionId = pointdeVenteRegionId;
	}

	public void setPointdeVente(String pointdeVente) {
		this.pointdeVente = pointdeVente;
	}

	public Long getPointdeVenteId() {
		return pointdeVenteId;
	}

	public void setPointdeVenteId(Long pointdeVenteId) {
		this.pointdeVenteId = pointdeVenteId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
