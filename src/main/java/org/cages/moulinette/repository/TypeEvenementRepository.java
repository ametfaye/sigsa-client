package org.cages.moulinette.repository;

import org.cages.moulinette.model.TypeEvenement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeEvenementRepository extends JpaRepository<TypeEvenement, Long>  {

	public TypeEvenement findByCodeTypeEvenement(String code);

}
