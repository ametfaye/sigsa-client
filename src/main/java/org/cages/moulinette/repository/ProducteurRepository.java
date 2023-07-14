package org.cages.moulinette.repository;

import java.util.List;

import org.cages.moulinette.model.Producteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("producteurRepository")
public interface ProducteurRepository extends JpaRepository<Producteur, Long> {

	Producteur findByNumNinea(String numNinea);
	Producteur findByNumeroProd(String numeroProd);
	
	

	@Query(value = "SELECT * FROM public.producteur ORDER BY prod_id DESC limit  ?1 ", nativeQuery = true)
	List<Producteur> loadLastListProducteur(Integer record);
}
