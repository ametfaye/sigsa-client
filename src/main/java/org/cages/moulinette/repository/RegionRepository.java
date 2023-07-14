package org.cages.moulinette.repository;

import org.cages.moulinette.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("RegionRepo")
public interface RegionRepository extends JpaRepository<Region, Long> {

}
