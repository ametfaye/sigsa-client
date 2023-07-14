package sn.awi.pgca.dataModel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="pgca_referentielTypeIntrant")
public class ReferentialTypeIntrants implements Serializable, GenericModel, Cloneable {
	
	private static final long serialVersionUID = 4740612708609554436L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long typeIntrantId;  

	@Column(length=120)
	private String libelle;
	
	@Column
	private int uniteDeMesure;  // 1 en Tonnage, 2 : litre,  3 par Piece , 4 par flacon , 
	

	@Column(length=150)
	private String pictoIntrant;  // Photo illustrant le type
	
	public Long getTypeIntrantId() {
		return typeIntrantId;
	}

	public void setTypeIntrantId(Long typeIntrantId) {
		this.typeIntrantId = typeIntrantId;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getUniteDeMesure() {
		return uniteDeMesure;
	}

	public void setUniteDeMesure(int uniteDeMesure) {
		this.uniteDeMesure = uniteDeMesure;
	}

	public String getPictoIntrant() {
		return pictoIntrant;
	}

	public void setPictoIntrant(String pictoIntrant) {
		this.pictoIntrant = pictoIntrant;
	}



		
}
