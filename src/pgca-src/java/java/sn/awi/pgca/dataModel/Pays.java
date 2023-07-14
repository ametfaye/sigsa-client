package sn.awi.pgca.dataModel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="pays")
public class Pays implements Serializable, GenericModel {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="pays_id")
	private Long id;

	@Column(length=100)
	private String libelle;

	@Column(length=10)
	private String code_pays;

	private boolean est_espace_ohada;

	@Column(length=10)
	private String code_monetaire;
	
	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getCode_pays() {
		return code_pays;
	}

	public void setCode_pays(String code_pays) {
		this.code_pays = code_pays;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Long getId() {
		return id;
	}

	public boolean isEst_espace_ohada() {
		return est_espace_ohada;
	}

	public String getCode_monetaire() {
		return code_monetaire;
	}

	public void setEst_espace_ohada(boolean est_espace_ohada) {
		this.est_espace_ohada = est_espace_ohada;
	}

	public void setCode_monetaire(String code_monetaire) {
		this.code_monetaire = code_monetaire;
	}
	


}
