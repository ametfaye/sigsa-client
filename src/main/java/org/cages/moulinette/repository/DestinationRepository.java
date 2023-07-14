package org.cages.moulinette.repository;

import java.util.List;

import org.cages.moulinette.model.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("DestinationRepository")
public interface DestinationRepository extends JpaRepository<Destination, Long> {
	
	Destination findByDestinationLibelle(String dest);

	Destination findByDstId(Long dstId);
	
	@Query(value = "SELECT * FROM destination dest "
			+ " join regn_region reg on dest.regn_id = reg.regn_id "
			+ " join pays p  on p.pays_id = reg.pays_id "
			+ " where p.code_pays = 'SN'", nativeQuery = true)
    List<Destination> findDestinationSN();
	
	@Query(value = "SELECT * FROM destination dest "
			+ " join regn_region reg on dest.regn_id = reg.regn_id "
			+ " join pays p  on p.pays_id = reg.pays_id "
			+ " where p.code_pays <> 'SN'", nativeQuery = true)
    List<Destination> findDestinationHorsSN();
}
