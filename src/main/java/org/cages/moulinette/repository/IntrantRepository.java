package org.cages.moulinette.repository;

import java.util.List;

import org.cages.moulinette.model.CategorieIntrant;
import org.cages.moulinette.model.Intrant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("intrantRepository")
public interface IntrantRepository extends JpaRepository<Intrant, Long> {
	
	List<Intrant> findByCategorieIntrant(CategorieIntrant categorieIntrant);
}
