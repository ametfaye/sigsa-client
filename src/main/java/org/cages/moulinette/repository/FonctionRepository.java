package org.cages.moulinette.repository;

import org.cages.moulinette.model.Fonction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("fonctionRepository")
public interface FonctionRepository extends JpaRepository<Fonction, Long> {
}
