package org.cages.moulinette.repository;

import org.cages.moulinette.model.StatutDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("statutDocumentRepository")
public interface StatutDocumentRepository extends JpaRepository<StatutDocument, Long> {

	StatutDocument findByCode(String code);
}
