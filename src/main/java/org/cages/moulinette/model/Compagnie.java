package org.cages.moulinette.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Compagnie")
@Setter
@Getter
@NoArgsConstructor
public class Compagnie {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long comId;

	@Column
	private String code;

	@Column
	private String libelle;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "dest_deservies", joinColumns = @JoinColumn(name = "com_id"), inverseJoinColumns = @JoinColumn(name = "dest_id"))
	private Set<Destination> destinationsDeservies;

}
