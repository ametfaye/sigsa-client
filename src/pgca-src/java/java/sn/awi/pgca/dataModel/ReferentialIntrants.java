package sn.awi.pgca.dataModel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="pgca_referentielIntrant")
public class ReferentialIntrants implements Serializable, GenericModel, Cloneable {
	
	private static final long serialVersionUID = 4740612708609554436L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long refIntrantId;
	
	@OneToOne
	@JoinColumn(name = "typeIntrantId", nullable = false) 
	private ReferentialTypeIntrants typeIntrantId;
	@Column(length=150)
	private String libelle;
	
	@Column(length=250)
	private String descriptifIntrant;
	
	
	@Column(length=250)
	private String uniteDeMesure;
	
	
	@Column(length=250)
	private String pictoIntrant;
	

	@Override
	public Long getId() {
		return refIntrantId;
	}


	public Long getRefIntrantId() {
		return refIntrantId;
	}


	public void setRefIntrantId(Long refIntrantId) {
		this.refIntrantId = refIntrantId;
	}



	public String getDescriptifIntrant() {
		return descriptifIntrant;
	}


	public void setDescriptifIntrant(String descriptifIntrant) {
		this.descriptifIntrant = descriptifIntrant;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public ReferentialTypeIntrants getTypeIntrantId() {
		return typeIntrantId;
	}


	public void setTypeIntrantId(ReferentialTypeIntrants typeIntrantId) {
		this.typeIntrantId = typeIntrantId;
	}


	public String getLibelle() {
		return libelle;
	}


	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}


	public String getUniteDeMesure() {
		return uniteDeMesure;
	}


	public void setUniteDeMesure(String uniteDeMesure) {
		this.uniteDeMesure = uniteDeMesure;
	}
	
	
}
