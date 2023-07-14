package org.cages.moulinette.service;

import java.util.List;

import org.cages.moulinette.dto.CategorieIntrantDTO;
import org.cages.moulinette.model.CategorieIntrant;

public interface ICategorieIntrantService {

	List<CategorieIntrantDTO> getAllCategoriesIntrants();

	CategorieIntrant getCategorieIntrantById(long id);

	void deleteCategorieIntrant(long id);

	void updateCategorieIntrant(CategorieIntrant categorieIntrant);

	CategorieIntrant getCategorieIntrantByCode(String code);

	void createCategorieIntrant(CategorieIntrant categorieIntrant);

}
