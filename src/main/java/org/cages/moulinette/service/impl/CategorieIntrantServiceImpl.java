package org.cages.moulinette.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.cages.moulinette.dto.CategorieIntrantDTO;
import org.cages.moulinette.model.CategorieIntrant;
import org.cages.moulinette.repository.CategorieIntrantRepository;
import org.cages.moulinette.service.ICategorieIntrantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("categorieIntrantService")
public class CategorieIntrantServiceImpl implements ICategorieIntrantService {

	@Autowired
	private CategorieIntrantRepository categorieIntrantRepository;

	@Override
	public List<CategorieIntrantDTO> getAllCategoriesIntrants() {
		List<CategorieIntrantDTO> categorieIntrantDTOs = new ArrayList<>();
		List<CategorieIntrant> categorieIntrants = categorieIntrantRepository.findAll();
		for (CategorieIntrant categorieIntrant : categorieIntrants) {
			CategorieIntrantDTO c = new CategorieIntrantDTO(categorieIntrant.getId(),categorieIntrant.getCode(),
					categorieIntrant.getLibelle(), categorieIntrant.getPictoCategorieIntrant());
			c.setLibelleType(categorieIntrant.getType().getLibelle());
			categorieIntrantDTOs.add(c);
		}
		return categorieIntrantDTOs;
	}

	@Override
	public CategorieIntrant getCategorieIntrantById(long id) {
		return categorieIntrantRepository.findOne(id);
	}

	@Override
	public void deleteCategorieIntrant(long id) {
		categorieIntrantRepository.delete(id);
	}

	@Override
	public void updateCategorieIntrant(CategorieIntrant categorieIntrant) {
		categorieIntrantRepository.save(categorieIntrant);
	}

	@Override
	public CategorieIntrant getCategorieIntrantByCode(String code) {
		return categorieIntrantRepository.findByCode(code);
	}

	@Override
	public void createCategorieIntrant(CategorieIntrant categorieIntrant) {
		categorieIntrantRepository.save(categorieIntrant);
	}

}
