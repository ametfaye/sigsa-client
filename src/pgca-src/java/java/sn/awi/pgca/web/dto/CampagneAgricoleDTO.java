package sn.awi.pgca.web.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class CampagneAgricoleDTO implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 4740612708609554436L;
	
	private Long											idCampagne;
	private String 											libelleCampagne;
	private String 											descriptionlibelleCampagne;

	private String 											statutCampagne;
	private Date											dateOuverture;
	private Date											dateFermeture;

	private int 											nombreIntrant ;
	private int 											nombreAgentCollecte ;
	private int 											nombreChequeImpaye ;
	
	private int 											montantTotalcollecteAlloue ;
	private int 											montantChequeImapye ;
	private int 											montantChequeDejapaye ;
	
	private boolean											programmeCollecte;
	private boolean											programmePhyto;
	private boolean											programmeArachide;
	private boolean											programmeAutres;
	
	
	
	
	private Set<ProduitDTO> produitsdelaCapagne;
	private Set<PointDeVenteDTO> pointdeVentesdelaCampagne;
	public Long getIdCampagne() {
		return idCampagne;
	}
	public void setIdCampagne(Long idCampagne) {
		this.idCampagne = idCampagne;
	}
	public String getLibelleCampagne() {
		return libelleCampagne;
	}
	public void setLibelleCampagne(String libelleCampagne) {
		this.libelleCampagne = libelleCampagne;
	}
	public String getStatutCampagne() {
		return statutCampagne;
	}
	public void setStatutCampagne(String statutCampagne) {
		this.statutCampagne = statutCampagne;
	}
	public Date getDateOuverture() {
		return dateOuverture;
	}
	public void setDateOuverture(Date dateOuverture) {
		this.dateOuverture = dateOuverture;
	}
	public Date getDateFermeture() {
		return dateFermeture;
	}
	public String getDescriptionlibelleCampagne() {
		return descriptionlibelleCampagne;
	}
	public void setDescriptionlibelleCampagne(String descriptionlibelleCampagne) {
		this.descriptionlibelleCampagne = descriptionlibelleCampagne;
	}
	public void setDateFermeture(Date dateFermeture) {
		this.dateFermeture = dateFermeture;
	}
	public int getNombreIntrant() {
		return nombreIntrant;
	}
	public void setNombreIntrant(int nombreIntrant) {
		this.nombreIntrant = nombreIntrant;
	}
	public int getNombreAgentCollecte() {
		return nombreAgentCollecte;
	}
	public void setNombreAgentCollecte(int nombreAgentCollecte) {
		this.nombreAgentCollecte = nombreAgentCollecte;
	}
	public int getNombreChequeImpaye() {
		return nombreChequeImpaye;
	}
	public void setNombreChequeImpaye(int nombreChequeImpaye) {
		this.nombreChequeImpaye = nombreChequeImpaye;
	}
	public int getMontantTotalcollecteAlloue() {
		return montantTotalcollecteAlloue;
	}
	public void setMontantTotalcollecteAlloue(int montantTotalcollecteAlloue) {
		this.montantTotalcollecteAlloue = montantTotalcollecteAlloue;
	}
	public int getMontantChequeImapye() {
		return montantChequeImapye;
	}
	public void setMontantChequeImapye(int montantChequeImapye) {
		this.montantChequeImapye = montantChequeImapye;
	}
	public int getMontantChequeDejapaye() {
		return montantChequeDejapaye;
	}
	public void setMontantChequeDejapaye(int montantChequeDejapaye) {
		this.montantChequeDejapaye = montantChequeDejapaye;
	}
	public Set<ProduitDTO> getProduitsdelaCapagne() {
		return produitsdelaCapagne;
	}
	public void setProduitsdelaCapagne(Set<ProduitDTO> produitsdelaCapagne) {
		this.produitsdelaCapagne = produitsdelaCapagne;
	}
	public Set<PointDeVenteDTO> getPointdeVentesdelaCampagne() {
		return pointdeVentesdelaCampagne;
	}
	public void setPointdeVentesdelaCampagne(Set<PointDeVenteDTO> pointdeVentesdelaCampagne) {
		this.pointdeVentesdelaCampagne = pointdeVentesdelaCampagne;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public boolean isProgrammeCollecte() {
		return programmeCollecte;
	}
	public void setProgrammeCollecte(boolean programmeCollecte) {
		this.programmeCollecte = programmeCollecte;
	}
	public boolean isProgrammePhyto() {
		return programmePhyto;
	}
	public void setProgrammePhyto(boolean programmePhyto) {
		this.programmePhyto = programmePhyto;
	}
	public boolean isProgrammeArachide() {
		return programmeArachide;
	}
	public void setProgrammeArachide(boolean programmeArachide) {
		this.programmeArachide = programmeArachide;
	}
	public boolean isProgrammeAutres() {
		return programmeAutres;
	}
	public void setProgrammeAutres(boolean programmeAutres) {
		this.programmeAutres = programmeAutres;
	}
	
	
}
