package sn.awi.pgca.business.service;

import java.util.List;

import sn.awi.pgca.business.exception.UtilisateurException;
import sn.awi.pgca.web.dto.UtilisateurDTO;

/**
 * 
 * @author Agrit IT Consulting
 */
public interface IUtilisateurService {
	/**
	 * methode de creation des utilisateurs
	 * */

	boolean verouillerUtilisateur(Long id) throws UtilisateurException;

	boolean deverouillerUtilisateur(Long id) throws UtilisateurException;;

	public boolean creerUtilisateur(UtilisateurDTO utilisateurDTO) throws UtilisateurException;

	public boolean modifierUtilisateur(UtilisateurDTO utilisateurDTO) throws UtilisateurException, Exception;

	public boolean removeUtilisateur(Long id) throws UtilisateurException;

	public List<UtilisateurDTO> recupererAllUtilisateur() throws UtilisateurException;

	public UtilisateurDTO recupUtilisateur(String code) throws UtilisateurException;

	public UtilisateurDTO recupUtilisateur(Long id) throws UtilisateurException;

	public void updateMotDePasse(UtilisateurDTO utilisateurDTO)throws UtilisateurException;

	public void genererChangePasswordDocument(UtilisateurDTO utilisateurDTO)throws UtilisateurException;

	boolean reinitialiserMDP(Long id) throws UtilisateurException;


}
