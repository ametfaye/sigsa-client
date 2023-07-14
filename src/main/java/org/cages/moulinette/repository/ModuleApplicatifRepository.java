package org.cages.moulinette.repository;

import java.util.List;

import org.cages.moulinette.model.ModuleApplicatif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleApplicatifRepository extends JpaRepository<ModuleApplicatif, Long> {

	public ModuleApplicatif findFirstByMdaCode(final String mdaCode);

	public List<ModuleApplicatif> findByMdaCode(final String mdaCode);

}
