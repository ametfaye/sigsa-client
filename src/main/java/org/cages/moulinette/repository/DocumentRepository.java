package org.cages.moulinette.repository;

import java.util.List;

import org.cages.moulinette.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

	@Query(value = "SELECT * FROM document doc "
			+ "join type_document tdoc on doc.doc_type = tdoc.td_id "
			+ " where doc.odm_id = ?1 "
			+ " and tdoc.code_doc = ?2 limit 200", nativeQuery = true)
	List<Document> findByOdmIdAndType(Long odmId, String type);

	@Query(value = "SELECT * FROM document doc "
			+ "join type_document tdoc on doc.doc_type = tdoc.td_id "
			+ " where doc.odm_id = ?1 "
			+ " and doc.agent_id = ?2 "
			+ " and tdoc.code_doc = ?3 limit 1", nativeQuery = true)
	Document findByOdmIdAndAgentIdAndType(Long odmId, Long aId, String type);

}
