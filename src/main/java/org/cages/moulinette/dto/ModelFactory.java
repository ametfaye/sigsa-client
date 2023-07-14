package org.cages.moulinette.dto;

import java.io.Serializable;

import javax.inject.Named;

import org.cages.moulinette.model.Adresse;
import org.cages.moulinette.model.Contact;

/**
 * Factory construisant les entit�s du mod�le � partir d'objet DTO.
 * 
 * @author AWA Consulting
 */

@Named
public class ModelFactory implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4046473790726867891L;

	/**
	 * Logger.
	 */
	// private static final Log LOG = LogFactory.getLog(ModelFactory.class);

	/**
	 * Constructeur par d�faut.
	 */

	

	public void convertContactDTO2Contact(Contact ctc, ContactDTO ctcdto) {
		ctc.setCourriel(ctcdto.getCourriel());
		ctc.setFixe(ctcdto.getFixe());
		ctc.setLibelle(ctcdto.getLibelle());
		ctc.setMobile(ctcdto.getMobile());
		ctc.setSite_web(ctcdto.getSite());
	}

	public void convertAdresseDTO2Adresse(Adresse addr, AdresseDTO addrdto) {
		addr.setCodepostal(addrdto.getCodepostal());
		addr.setQuartier(addrdto.getQuartier());
		addr.setLibelle(addrdto.getLibelle());
		addr.setVille(addrdto.getVille());
	}

	
}
