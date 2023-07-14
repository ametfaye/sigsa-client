package org.cages.moulinette.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "destination")
@Setter
@Getter
@NoArgsConstructor
public class Destination {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long dstId;

	@Column
	private String destCode;

	@Column
	private String destinationLibelle;

	@Column
	private BigDecimal longitude;

	@Column
	private BigDecimal latitude;

	@ManyToOne
	@JoinColumn(name = "regn_id", nullable = true)
	private Region region;

}
