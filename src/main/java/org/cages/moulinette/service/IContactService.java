package org.cages.moulinette.service;

import java.util.List;

import org.cages.moulinette.model.Contact;

public interface IContactService {
	
	public List<Contact> getAllContacts();

	public void createContact(Contact role);
	
	public void deleteContact(long id);

	public void updateContact(Contact role);

	public Contact getContactById(long id);
	
	public Contact getContactByCode(String code);

}
