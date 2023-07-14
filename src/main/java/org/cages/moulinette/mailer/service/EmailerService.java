package org.cages.moulinette.mailer.service;

import org.cages.moulinette.mailer.model.MessageEmail;

public interface EmailerService {

	public void envoyer(MessageEmail messageEmail);

}