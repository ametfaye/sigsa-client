package org.cages.moulinette.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.cages.moulinette.mailer.model.GenericModel;

@Entity
@Table(name="pgca_MiseEnPlaceEffectuee")
public abstract class MiseEnPlace implements Serializable, GenericModel, Cloneable {
	
	private static final long serialVersionUID = 4740612708609554436L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long id_MEPeff;




	
	@Column
	private String bl_Port;
	
	@Column
	private String blManuel; // documents saisie manuellement sur le terrain
	@Column
	private String lvManuel;
	
	
	
	@Column
	private double quantiteAmettreEnplace ; 
	

	
	
	@Column
	private Date dateMiseEnplaceEffective;
	
	@Column
	private String destinataire;
	
	@Column
	private String pointdeVenteLibelle;
	
	
	

	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
