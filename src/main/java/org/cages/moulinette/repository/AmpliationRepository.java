package org.cages.moulinette.repository;

import org.cages.moulinette.model.Ampliation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("ampliationRepository")
public interface AmpliationRepository extends JpaRepository<Ampliation, Long> {

	public Ampliation findByCode(String code);
}
