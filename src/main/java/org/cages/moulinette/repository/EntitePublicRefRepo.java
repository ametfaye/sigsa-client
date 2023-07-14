package org.cages.moulinette.repository;

import org.cages.moulinette.model.EntitePublicRef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("reporRefPublic")
public interface EntitePublicRefRepo extends JpaRepository<EntitePublicRef, Long> {

	EntitePublicRef findByCode(String code);

}
