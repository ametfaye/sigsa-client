package sn.awi.pgca.dataMapping;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Named;

import sn.awi.pgca.dataModel.Adresse;
import sn.awi.pgca.dataModel.Contact;
import sn.awi.pgca.dataModel.Personne;
import sn.awi.pgca.dataModel.PointDeCollecte;
import sn.awi.pgca.dataModel.Profil;
import sn.awi.pgca.dataModel.Utilisateur;
import sn.awi.pgca.web.dto.AdresseDTO;
import sn.awi.pgca.web.dto.ContactDTO;
import sn.awi.pgca.web.dto.EntiteJuridiqueDTO;
import sn.awi.pgca.web.dto.MandataireDTO;
import sn.awi.pgca.web.dto.PersonneDTO;
import sn.awi.pgca.web.dto.ProfilDTO;
import sn.awi.pgca.web.dto.UtilisateurDTO;

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
	public ModelFactory() {
		super();
	}

	public void createProfil(Profil profil, ProfilDTO profildto) {
		profil.setId((Long) profildto.getId());
		profil.setCode(profildto.getCode());
		profil.setLibelle(profildto.getLibelle());
	}

	public void createUtilisateur(Utilisateur utilisateur, UtilisateurDTO utilisateurdto) {
		//utilisateur.setId((Long) utilisateurdto.getId());
		utilisateur.setCodeutilisateur(utilisateurdto.getTel());
		utilisateur.setMotdepasse(utilisateurdto.getPassword());
		utilisateur.setEmail(utilisateurdto.getEmail());
		Date now = new Date();
		utilisateur.setDateCreation(new java.sql.Timestamp(now.getTime()));
	
		utilisateur.setNombredeConnexion(0);
	}

	
	public void createPersonne(Personne personne, UtilisateurDTO utilisateurdto) {

		personne.setNom(utilisateurdto.getNom());
		personne.setPrenom(utilisateurdto.getPrenom());		
		personne.setCivilite('1');
	}

	public void convert2Personne(Personne personne, MandataireDTO mandataireDTO) {
		personne.setNom(mandataireDTO.getNom());
		personne.setPrenom(mandataireDTO.getPrenom());
		personne.setCivilite((mandataireDTO.getCivilite() != null && mandataireDTO.getCivilite().length() > 0 ? mandataireDTO.getCivilite().charAt(0) : '1'));
	}

	public void convert2Personne(Personne personne, PersonneDTO personneDTO) {
		personne.setNom(personneDTO.getNom());
		personne.setPrenom(personneDTO.getPrenom());
		personne.setLieudenaissance(personneDTO.getLieunaissance());
		personne.setDatenaissance(personneDTO.getDateNais());
		personne.setSituationmat(personneDTO.getSituationmat());
		personne.setNationalite(personneDTO.getNationalite());
		personne.setCivilite((personneDTO.getCivilite() != null && personneDTO.getCivilite().length() > 0 ? personneDTO.getCivilite().charAt(0) : '1'));
	}

	
	public void createContact(Contact contact, EntiteJuridiqueDTO entiteJdto) {
		contact.setCourriel(entiteJdto.getContact().getCourriel());
		contact.setSite_web(entiteJdto.getContact().getSite());
		contact.setFixe(entiteJdto.getContact().getFixe());
		contact.setMobile(entiteJdto.getContact().getMobile());
		contact.setFax(entiteJdto.getContact().getFax());
		contact.setLibelle(entiteJdto.getContact().getLibelle());

	}

	/*********************
	 * Entite Juridique 3 methodes creations contacts , adresse et entité
	 * juridiques
	 *******************/
	public void createEntiteJuridique(PointDeCollecte enju, EntiteJuridiqueDTO entiteJdto) {

		
		enju.setSuperficie(new Integer(entiteJdto.getSuperficie()));
		enju.setLibelle(entiteJdto.getLibelle());

		enju.setLibelle(entiteJdto.getLibelle());
	}

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
