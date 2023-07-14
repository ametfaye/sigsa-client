package org.cages.moulinette.service;

import java.util.List;

import org.cages.moulinette.dto.IntrantDTO;
import org.cages.moulinette.model.Intrant;

public interface IntrantService {

	void updateIntrant(Intrant categorieIntrant);

	Intrant getIntrantById(long id);

	void createIntrant(Intrant intrant);

	List<IntrantDTO> getAllIntrants();
	
	List<IntrantDTO> getIntrantsByCategorie(Long idCategorie);


	void deleteIntrant(long id);

}
