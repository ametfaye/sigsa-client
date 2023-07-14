package org.cages.moulinette.service.impl;

import java.util.List;

import org.cages.moulinette.model.Contact;
import org.cages.moulinette.repository.ContactRepository;
import org.cages.moulinette.service.IContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("contactService")
public class ContactServiceImpl implements IContactService {

	@Autowired
	private ContactRepository contactRepository;

	@Override
	public List<Contact> getAllContacts() {
		return contactRepository.findAll();
	}

	@Override
	public void createContact(Contact role) {
		// TODO Auto-generated method stub
	}

	@Override
	public void deleteContact(long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateContact(Contact role) {
		// TODO Auto-generated method stub

	}

	@Override
	public Contact getContactById(long id) {
		return contactRepository.findOne(id);
	}

	@Override
	public Contact getContactByCode(String code) {
		// TODO Auto-generated method stub
		return null;
	}

}
