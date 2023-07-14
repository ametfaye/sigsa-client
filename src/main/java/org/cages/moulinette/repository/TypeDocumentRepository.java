package org.cages.moulinette.repository;

import org.cages.moulinette.model.TypeDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("typeDocumentRepository")
public interface TypeDocumentRepository extends JpaRepository<TypeDocument, Long> {
	
	TypeDocument findByCodeDoc(String code);
}
