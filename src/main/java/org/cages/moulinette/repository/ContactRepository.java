package org.cages.moulinette.repository;

import org.cages.moulinette.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("ContactRepository")
public interface ContactRepository extends JpaRepository<Contact, Long> {

}
