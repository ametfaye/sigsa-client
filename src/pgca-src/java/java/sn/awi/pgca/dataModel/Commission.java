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
@Table(name="pgca_commission")
public class Commission implements Serializable, GenericModel, Cloneable {
	
	private static final long serialVersionUID = 4740612708609554436L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long id_com;

	@OneToOne
	@JoinColumn(name = "pgca_idprog", nullable = false)
	private ProgrammeAgricol programmeConcerne;
	
	
	public ProgrammeAgricol getProgrammeConcerne() {
		return programmeConcerne;
	}

	public void setProgrammeConcerne(ProgrammeAgricol programmeConcerne) {
		this.programmeConcerne = programmeConcerne;
	}

	@Column(length=50)
	private String libelleCommission;
	
	@Column
	private int taux;
	
	@Column
	private float montantCom;

	@Override
	public Long getId() {
		return id_com;
	}

	public Long getId_com() {
		return id_com;
	}

	public void setId_com(Long id_com) {
		this.id_com = id_com;
	}

	public String getLibelleCommission() {
		return libelleCommission;
	}

	public void setLibelleCommission(String libelleCommission) {
		this.libelleCommission = libelleCommission;
	}

	public int getTaux() {
		return taux;
	}

	public void setTaux(int taux) {
		this.taux = taux;
	}

	public float getMontantCom() {
		return montantCom;
	}

	public void setMontantCom(float montantCom) {
		this.montantCom = montantCom;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
