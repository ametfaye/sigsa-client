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
@Table(name="pgca_transporteur")
public class Transporteur implements Serializable, GenericModel {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idtransporteur")
	private Long idtransporteur;

	@Column(length=100, nullable = false)
	private String libelle;
	
	@OneToOne
	@JoinColumn(name = "adresse_id", nullable = true)
	private Adresse adresse; 
	
	@OneToOne
	@JoinColumn(name = "cont_id", nullable = true)
	private Contact contact;

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getIdtransporteur() {
		return idtransporteur;
	}

	public void setIdtransporteur(Long idtransporteur) {
		this.idtransporteur = idtransporteur;
	}

}
