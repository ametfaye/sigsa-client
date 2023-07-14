package org.cages.moulinette.repository;

import java.util.List;

import org.cages.moulinette.model.ModuleApplicatif;
import org.cages.moulinette.model.ParametreModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ParametreModuleRepository extends JpaRepository<ParametreModule, Long> {

	public List<ParametreModule> findByPamCode(final String pamCode);

	public ParametreModule findFirstByPamCode(final String pamCode);

	public ParametreModule findFirstByPamCodeAndModuleApplicatif(final String pamCode, final ModuleApplicatif moduleApplicatif);

	@Query(value = "SELECT * FROM parametre_module pam "
			+ " join module_applicatif mda on mda.id = pam.module_applicatif_id"
	 		+ " WHERE mda.mda_code = 'ODM' limit 200", nativeQuery = true)
	public List<ParametreModule> getParametresModulesMission();

	@Query(value = "SELECT * FROM parametre_module pam "
			+ " join module_applicatif mda on mda.id = pam.module_applicatif_id"
	 		+ " WHERE pam.srv_id = ?1 and mda.mda_code = 'ODM' and pam.pam_code = 'MAX_DUREE_MISSION' limit 1", nativeQuery = true)
	public ParametreModule getDureeMaximumMission(long srvId);

	@Query(value = "SELECT * FROM parametre_module pam "
			+ " join module_applicatif mda on mda.id = pam.module_applicatif_id"
	 		+ " WHERE pam.srv_id = ?1 and mda.mda_code = 'ODM' and pam.pam_code = 'MAX_DELAI_EDITION' limit 1", nativeQuery = true)
	public ParametreModule getDelaiMaximumMission(long srvId);
	
	@Query(value = "SELECT * FROM parametre_module pam "
			+ " join module_applicatif mda on mda.id = pam.module_applicatif_id"
	 		+ " WHERE pam.srv_id = ?1 and mda.mda_code = 'ODM' and pam.pam_code = 'MAX_PARTICIPANT' limit 1", nativeQuery = true)
	public ParametreModule getMaxParticipant(long srvId);
	
	@Query(value = "SELECT * FROM parametre_module pam "
			+ " join module_applicatif mda on mda.id = pam.module_applicatif_id"
	 		+ " WHERE pam.srv_id is null and mda.mda_code = 'ODM' and pam.pam_code = 'MAX_DUREE_MISSION' limit 1", nativeQuery = true)
	public ParametreModule getDureeMaximumMission();

	@Query(value = "SELECT * FROM parametre_module pam "
			+ " join module_applicatif mda on mda.id = pam.module_applicatif_id"
	 		+ " WHERE pam.srv_id is null and mda.mda_code = 'ODM' and pam.pam_code = 'MAX_DELAI_EDITION' limit 1", nativeQuery = true)
	public ParametreModule getDelaiMaximumMission();
	
	@Query(value = "SELECT * FROM parametre_module pam "
			+ " join module_applicatif mda on mda.id = pam.module_applicatif_id"
	 		+ " WHERE pam.srv_id is null and mda.mda_code = 'ODM' and pam.pam_code = 'MAX_PARTICIPANT' limit 1", nativeQuery = true)
	public ParametreModule getMaxParticipant();

}
