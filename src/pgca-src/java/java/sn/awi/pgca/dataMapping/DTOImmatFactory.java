package sn.awi.pgca.dataMapping;

import javax.inject.Inject;
import javax.inject.Named;

import sn.awi.pgca.dataModel.Personne;
import sn.awi.pgca.utils.DateUtils;
import sn.awi.pgca.web.dto.PersonneDTO;

@Named("dtoImmatFactory")
public class DTOImmatFactory {

	@Inject
	private DTOFactory dtoFactory;

	

	private void chargerPersonne(PersonneDTO personneDTO, Personne pers) {
		chargerPersonneGeneric(personneDTO, pers);
		/*if (pers.get() != null) {
			personneDTO.getAdresse().setId(pers.getDerniereAdresse().getId().longValue()+"");
			personneDTO.getAdresse().setLibelle(pers.getDerniereAdresse().getLibelle());
			personneDTO.getAdresse().setVille(pers.getDerniereAdresse().getVille());
			personneDTO.getAdresse().setQuartier(pers.getDerniereAdresse().getQuartier());
		} 
		if (pers.getDocumentIdentification()!=null) {
			personneDTO.setIdDocumentIdentification(pers.getDocumentIdentification().getId());
			personneDTO.setNumeroDocumentIdentification(pers.getDocumentIdentification().getNumero());
			personneDTO.setTypeDocumentIdentification(pers.getDocumentIdentification().getTypedocument()+"");
			personneDTO.setDateDebutDocumentIdentification(pers.getDocumentIdentification().getDatedebut());
			personneDTO.setDateFinDocumentIdentification(pers.getDocumentIdentification().getDatefin());
		}*/
	}

	private void chargerPersonneGeneric(PersonneDTO personneDTO, Personne pers) {
		personneDTO.setNumerotmp(pers.getId().longValue() + "");
		personneDTO.setId(pers.getId());
		personneDTO.setCivilite(pers.getCivilite() + "");
		personneDTO.setNom(pers.getNom());
		personneDTO.setPrenom(pers.getPrenom());
		personneDTO.setNationalite(pers.getNationalite());
		personneDTO.setDateNais(pers.getDatenaissance());
		personneDTO.setLieunaissance(pers.getLieudenaissance());
		personneDTO.setSituationmat(pers.getSituationmat());
		if (pers.getDatenaissance() != null) {
			personneDTO.setStrDateNais2(DateUtils.formatDate(pers.getDatenaissance()));
		}
	}

	public DTOFactory getDtoFactory() {
		return dtoFactory;
	}

	public void setDtoFactory(DTOFactory dtoFactory) {
		this.dtoFactory = dtoFactory;
	}
}
