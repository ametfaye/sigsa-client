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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "prfl_profil")
public class Profil implements Serializable, GenericModel {

	private static final long serialVersionUID = 4740612708609554436L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "prfl_id")
	private Long id;

	@Column(length = 25)
	private String code;

	@Column(length = 100)
	private String libelle;


//
//	
//	
//	@OneToOne
//	@JoinColumn(name = "pdc_id", nullable = true)
//	private PointDeCollecte pdc_id;
	

	public Long getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}


	public void setId(Long id) {
		this.id = id;
	}

//	public PointDeCollecte getPdc_id() {
//		return pdc_id;
//	}
//
//	public void setPdc_id(PointDeCollecte pdc_id) {
//		this.pdc_id = pdc_id;
//	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getLibelle() {
		return libelle;
	}

}
