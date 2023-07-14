package org.cages.moulinette.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "module_applicatif")
@NamedQuery(name = "ModuleApplicatif.findAll", query = "SELECT m FROM ModuleApplicatif m")
public class ModuleApplicatif {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_module_applicatif")
	@SequenceGenerator(name = "seq_module_applicatif", allocationSize = 1)
	private long id;

	@Column(name = "mda_code")
	private String mdaCode;

	@Column(name = "mda_libelle")
	private String mdaLibelle;

	@Column(name = "mda_si_actif")
	private Integer mdaSiActif;

	@OneToMany(mappedBy = "moduleApplicatif")
	private List<ParametreModule> parametreModules;

}
