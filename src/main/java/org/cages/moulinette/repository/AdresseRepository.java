package org.cages.moulinette.repository;

import org.cages.moulinette.model.Adresse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("AdresseRepository")
public interface AdresseRepository extends JpaRepository<Adresse, Long> {

}
