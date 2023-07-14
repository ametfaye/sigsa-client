package org.cages.moulinette.repository;

import org.cages.moulinette.model.CategorieIntrant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("categorieIntrantRepository")
public interface CategorieIntrantRepository extends JpaRepository<CategorieIntrant, Long> {

	CategorieIntrant findByCode(String code);

}
