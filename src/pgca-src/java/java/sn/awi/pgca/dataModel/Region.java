package sn.awi.pgca.dataModel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="regn_region")
public class Region implements Serializable, GenericModel {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -7643121102813291538L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="regn_id")
	private Long id;

	@Column(length=100)
	private String libelle;
	
	@ManyToOne
	@JoinColumn(name = "pays_id", nullable = false)
	private Pays pays;
	
	@Column(length=100)
	private String code_region;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public Pays getPays() {
		return pays;
	}

	public void setPays(Pays pays) {
		this.pays = pays;
	}

	public String getCode_region() {
		return code_region;
	}

	public void setCode_region(String code_region) {
		this.code_region = code_region;
	}


}
