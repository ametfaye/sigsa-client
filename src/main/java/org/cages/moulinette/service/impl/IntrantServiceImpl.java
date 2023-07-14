package org.cages.moulinette.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.cages.moulinette.dto.CategorieIntrantDTO;
import org.cages.moulinette.dto.IntrantDTO;
import org.cages.moulinette.model.CategorieIntrant;
import org.cages.moulinette.model.Intrant;
import org.cages.moulinette.model.VarieteIntrant;
import org.cages.moulinette.repository.CategorieIntrantRepository;
import org.cages.moulinette.repository.IntrantRepository;
import org.cages.moulinette.repository.VarieteIntrantRepository;
import org.cages.moulinette.service.IntrantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("intrantService")
public class IntrantServiceImpl implements IntrantService {

	@Autowired
	private IntrantRepository intrantRepository;
	
	@Autowired
	CategorieIntrantRepository categorieIntrantRepository;
	
	@Autowired
	VarieteIntrantRepository varieteIntrantRepository;
	

	@Override
	public void updateIntrant(Intrant intrant) {
		intrantRepository.save(intrant);
	}

	@Override
	public Intrant getIntrantById(long id) {
		return intrantRepository.findOne(id);
	}

	@Override
	public void createIntrant(Intrant intrant) {
		intrantRepository.save(intrant);
	}

	@Override
	public List<IntrantDTO> getAllIntrants() {
		List<IntrantDTO> intrantDTOs = new ArrayList<>();
		List<VarieteIntrant> vIntrants = varieteIntrantRepository.findAll();
		
		for (VarieteIntrant intrant : vIntrants) {
			IntrantDTO c = new IntrantDTO();
			
			c.setLibelle(intrant.getLibelle());
			c.setCategorie(intrant.getCategorie().getLibelle());
			c.setType(intrant.getCategorie().getType().getLibelle());
			intrantDTOs.add(c);
		}
		return intrantDTOs;
	}

	@Override
	public void deleteIntrant(long id) {
		intrantRepository.delete(id);
	}

	@Override
	public List<IntrantDTO> getIntrantsByCategorie(Long idCategorie) {
		List<IntrantDTO> intrantDTOs = new ArrayList<>();
		CategorieIntrant ca =  categorieIntrantRepository.findOne(idCategorie);
		if(ca == null)
			return null;
		
		List<Intrant> intrants = intrantRepository.findByCategorieIntrant(ca);
		
		for (Intrant intrant : intrants) {
			IntrantDTO c = new IntrantDTO(intrant.getId(), intrant.getLibelle(), intrant.getQuantite(),
					intrant.getPictoIntrant());
			intrantDTOs.add(c);
		}
		return intrantDTOs;
	}

}
