package sn.awi.pgca.web.dto;

import java.io.Serializable;

public class EngraisDTO implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 7838560630950854563L;

	private Long	idEngrais;
	
	private Double quantite; 
	
	private String libelleProduit;
	private String libelleProgramme;
	private String libelleCampagne;
	private String provenanceProduit;

	private Long idPointStock;
	private String nomPointStock;
	
	private Long idTypeEngrais;
	private String typeEngraislibelle;

	public Long getIdEngrais() {
		return idEngrais;
	}

	public void setIdEngrais(Long idEngrais) {
		this.idEngrais = idEngrais;
	}

	public Double getQuantite() {
		return quantite;
	}

	public void setQuantite(Double quantite) {
		this.quantite = quantite;
	}

	public String getLibelleProduit() {
		return libelleProduit;
	}

	public void setLibelleProduit(String libelleProduit) {
		this.libelleProduit = libelleProduit;
	}

	public Long getIdPointStock() {
		return idPointStock;
	}

	public void setIdPointStock(Long idPointStock) {
		this.idPointStock = idPointStock;
	}

	public Long getIdTypeEngrais() {
		return idTypeEngrais;
	}

	public void setIdTypeEngrais(Long idTypeEngrais) {
		this.idTypeEngrais = idTypeEngrais;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getNomPointStock() {
		return nomPointStock;
	}

	public void setNomPointStock(String nomPointStock) {
		this.nomPointStock = nomPointStock;
	}

	public String getTypeEngraislibelle() {
		return typeEngraislibelle;
	}

	public void setTypeEngraislibelle(String typeEngraislibelle) {
		this.typeEngraislibelle = typeEngraislibelle;
	}

	public String getLibelleProgramme() {
		return libelleProgramme;
	}

	public void setLibelleProgramme(String libelleProgramme) {
		this.libelleProgramme = libelleProgramme;
	}

	public String getProvenanceProduit() {
		return provenanceProduit;
	}

	public void setProvenanceProduit(String provenanceProduit) {
		this.provenanceProduit = provenanceProduit;
	}

	public String getLibelleCampagne() {
		return libelleCampagne;
	}

	public void setLibelleCampagne(String libelleCampagne) {
		this.libelleCampagne = libelleCampagne;
	}

	

}
