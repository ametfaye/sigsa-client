package org.cages.moulinette.repository;

import java.util.List;

import org.cages.moulinette.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("serviceRepo")
public interface ServiceReferentialRepository extends JpaRepository<Service, Long> {

	@Query(value = "SELECT * FROM services WHERE ref_id = ?1 limit 200", nativeQuery = true)
	public List<Service> findByEntitePublicRefId(long id);

	public Service findByCode(String code);
}
