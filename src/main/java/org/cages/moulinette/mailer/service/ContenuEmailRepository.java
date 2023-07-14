package org.cages.moulinette.mailer.service;

import org.cages.moulinette.exceptions.EmailTemplateException;
import org.cages.moulinette.mailer.model.ContenuEmail;

public interface ContenuEmailRepository {

	ContenuEmail ficheOdm(String objet, String templateName, String civilite, String nom, String prenom, long odmId,
			String dest, String etat, String debutOdm, String finOdm) throws EmailTemplateException;

	ContenuEmail autorisationDeSortie(String objet, String templateName, String civilite, String nom, String prenom)
			throws EmailTemplateException;

}
