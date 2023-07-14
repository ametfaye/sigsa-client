package sn.awi.pgca.business.service;

import java.io.File;

import sn.awi.pgca.dataModel.Utilisateur;

public interface Inotification {

	
	
	/*****  Envoi mail en mode HTML ****/
	
	public boolean sendEmailWithAttachement(String subject, String message, File attachement);
	
	public boolean sendEmailWithoutAttachement(String adresseDestiinataire, String subject, String message);

	
	/***** RAPPORT AUTOMATQIUE PARAMETRAGE PAR l'ADMIN ****/

	boolean envoyerRapportDactivites();  //  manager
	
	
	boolean envoyerRapportDeMisesEnPlace();  // agents de saisis

	boolean envoyerConfirmation(Utilisateur utilisateurDTO);

	
}
