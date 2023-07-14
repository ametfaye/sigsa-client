package sn.awi.pgca.web.dto;

import java.io.Serializable;
import java.util.List;

public class PorteDocumentDTO implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -2178376851061230706L;

	private String identite;
	
	private String numerorccm;
	
	private String entitejuridique;
	
	private List<GedAbstractDocumentDTO> documents;

	public String getIdentite() {
		return identite;
	}

	public void setIdentite(String identite) {
		this.identite = identite;
	}

	public String getNumerorccm() {
		return numerorccm;
	}

	public void setNumerorccm(String numerorccm) {
		this.numerorccm = numerorccm;
	}

	public String getEntitejuridique() {
		return entitejuridique;
	}

	public void setEntitejuridique(String entitejuridique) {
		this.entitejuridique = entitejuridique;
	}

	public List<GedAbstractDocumentDTO> getDocuments() {
		return documents;
	}

	public void setDocuments(List<GedAbstractDocumentDTO> documents) {
		this.documents = documents;
	}
	
}
