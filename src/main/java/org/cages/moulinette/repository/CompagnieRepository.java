package org.cages.moulinette.repository;

import org.cages.moulinette.model.Compagnie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("CompagnieRepository")
public interface CompagnieRepository extends JpaRepository<Compagnie, Long> {
}
