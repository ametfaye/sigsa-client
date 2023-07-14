package org.cages.moulinette.repository;

import org.cages.moulinette.model.UniteDeMesure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("uniteDeMesureRepository")
public interface UniteDeMesureRepository extends JpaRepository<UniteDeMesure, Long> {

}
