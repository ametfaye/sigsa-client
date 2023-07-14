package sn.awi.pgca.business.service;

import java.util.List;

import sn.awi.pgca.business.exception.EntityDBDAOException;
import sn.awi.pgca.business.exception.ProfilException;
import sn.awi.pgca.web.dto.ActionDTO;
import sn.awi.pgca.web.dto.ProfilDTO;

/**
 * Interface offrant tous les services li�s � la gestion des profils.
 * cr�ation de profil
 * param�trage de droit d'acc�s au menu ou sous-menu � un profil
 * param�trage d'actions possible pour un profil
 *
 * @author AWA Consulting
 */
public interface IProfilService {
	
	/**
	 * methode de creation de profil 
	 * */
	
	 public boolean creerProfil(ProfilDTO profilDTO) throws ProfilException;

	
		/***
		 * methode pour recuperer un profil a partir de son code
		 * @param  code   du profil a recuperer 
		 * @return retourne le profil s'il existe en base 
		 * @exception léve une exception {@link ProfilException}  si le code est null
		 * **************/
	 public ProfilDTO recupProfil(String code) throws ProfilException;
	
	 public boolean removeProfil(Long code) throws ProfilException;
	 
	 public List<ProfilDTO> recupererAllProfil() throws ProfilException;

		/***
		 * methode pour recuperer le dernier code profil depuis la table de referentiel ref-numero
		 * @param  Pas de params
		 * @return retourne le profil s'il existe en base 
		 * @exception léve une exception {@link ProfilException}  si le code est null
		 * **************/
	 public String getCodeLastProfil () throws ProfilException;


	 
	 public ProfilDTO getProfilById(Long id) throws ProfilException ;
	 
}
