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
@Table(name="pgca_client")
public class Client implements Serializable, GenericModel {
	
	

	private static final long serialVersionUID = 4740612708609554436L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name ="cli_id")  
	private Long client_id;
	
	

	@Column(length = 225)
	private String libelle;



	@OneToOne
	@JoinColumn(name = "pers_id", nullable = true)
	private Personne personne;



	@Override
	public Long getId() {
		return 	client_id;
	}



	public Long getClient_id() {
		return client_id;
	}



	public void setClient_id(Long client_id) {
		this.client_id = client_id;
	}



	public String getLibelle() {
		return libelle;
	}



	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}



	public Personne getPersonne() {
		return personne;
	}



	public void setPersonne(Personne personne) {
		this.personne = personne;
	}
	


}
