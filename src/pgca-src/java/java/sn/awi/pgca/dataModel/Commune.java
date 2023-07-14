package sn.awi.pgca.dataModel;

import java.io.Serializable;
import java.util.Set;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Classe modèle Communes.
 * 
 * @author agileway
 */
@Entity
@Table(name = "commune")
public class Commune implements Serializable, GenericModel {

	/**
	 * serialVersionUID.
	 */
	private static final long		serialVersionUID	= 7463480948119163028L;

	/**
	 * Id commune.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "commune_id")
	private Long					id;

	/**
	 * Code commune.
	 */
	@Column(name = "code")
	private String					code;

	/**
	 * Libelle commune.
	 */
	@Column(name = "libelle")
	private String					libelle;
	

	/**
	 * Libelle commune.
	 */
	@Column(name = "description", length=12000)
	private String					description;

	/**
	 * Relation avec le département.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "departement_id", nullable = false)
	private Departement				departement;



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Departement getDepartement() {
		return departement;
	}

	public void setDepartement(Departement departement) {
		this.departement = departement;
	}


}


