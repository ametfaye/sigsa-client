package org.cages.moulinette.repository;

import java.util.List;

import org.cages.moulinette.model.CategorieIntrant;
import org.cages.moulinette.model.VarieteIntrant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("VarieteIntrantRepository")
public interface VarieteIntrantRepository extends JpaRepository<VarieteIntrant, Long> {
	
	List<VarieteIntrant> findByCategorie(CategorieIntrant categorie);
	
}
