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
@Table(name = "com_prm_parametre")
public class ParametreGlobaux implements Serializable, GenericModel {

	private static final long		serialVersionUID									= -2511283390231492857L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "com_prm_id")
	private Long								id;

	@Column(length = 50)
	private String							code_param;

	@Column(length = 50)
	private String							value_param;
	
	@ManyToOne
	@JoinColumn(name = "pays_id", nullable = false)
	private Pays pays;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode_param() {
		return code_param;
	}

	public void setCode_param(String code_param) {
		this.code_param = code_param;
	}

	public String getValue_param() {
		return value_param;
	}

	public void setValue_param(String value_param) {
		this.value_param = value_param;
	}

	public Pays getPays() {
		return pays;
	}

	public void setPays(Pays pays) {
		this.pays = pays;
	}

}
