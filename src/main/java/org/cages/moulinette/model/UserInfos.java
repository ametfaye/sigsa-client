package org.cages.moulinette.model;

import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Transient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "UserInfos")
@Setter
@Getter
@NoArgsConstructor
public class UserInfos {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private Long id;

	@Column(name = "identifiant")
	private String identifiant;

	@Column(name = "password")
	@Length(min = 5, message = "Le mot de passe doit avoir au minimum 5  carcatères")
	@NotEmpty(message = "Please provide your password")
	@Transient
	private String password;

	@Column(name = "active")
	private int active;

	@Column(name = "deleted")
	private int deleted;

	@Column
	private Date dateCreation;

	@Column
	private Date dateModification;

	@Column
	private Date dateSuppression;

	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

	@OneToOne
	@JoinColumn(name = "a_id", nullable = true)
	private Agent agent;

	public UserInfos(String email, String identifiant, String password, String prenom, String nom, int active,
			Set<Role> roles, Agent agent) {
		super();
		this.identifiant = identifiant;
		this.password = password;
		this.active = active;
		this.roles = roles;
		this.agent = agent;
	}

}
