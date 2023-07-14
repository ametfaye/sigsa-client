package sn.awi.pgca.dataModel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="pgca_typeProduit")
public class TypeProduit implements Serializable, GenericModel, Cloneable {
	
	private static final long serialVersionUID = 4740612708609554436L;
	

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="typeProduit_id") 
	private Long typeProduit_id;

	@Column(length=120)
	private String libelle;


	public Long getTypeProduit_id() {
		return typeProduit_id;
	}

	public void setTypeProduit_id(Long typeProduit_id) {
		this.typeProduit_id = typeProduit_id;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return typeProduit_id;
	}
	
		
}
