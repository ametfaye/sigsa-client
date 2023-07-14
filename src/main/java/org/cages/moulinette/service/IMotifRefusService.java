package org.cages.moulinette.service;

import java.util.List;

import org.cages.moulinette.model.MotifRefus;

public interface IMotifRefusService {

	public List<MotifRefus> getAllMotifs();

	public MotifRefus findMotifRefus(String code);

}
