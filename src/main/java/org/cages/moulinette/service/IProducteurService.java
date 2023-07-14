package org.cages.moulinette.service;

import java.util.Date;
import java.util.List;

import org.cages.moulinette.dto.EnrollementDTO;
import org.cages.moulinette.dto.ProducteurDTO;
import org.cages.moulinette.exceptions.EntityDBDAOException;
import org.cages.moulinette.exceptions.EntityPersisitanteException;
import org.cages.moulinette.model.PersonnePhysique;
import org.cages.moulinette.model.Producteur;

public interface IProducteurService {
	
	Producteur createProducteur(String typeProducteur, List<PersonnePhysique> personnePhysiques, ProducteurDTO producateurAcrer, Date dateImmatriculation);
	
	ProducteurDTO createProducteur(EnrollementDTO infosEnrollement) throws EntityPersisitanteException;

	List<ProducteurDTO> loadListProducteur() throws EntityPersisitanteException;

	ProducteurDTO loadProducteurById(Long idProducteur) throws EntityPersisitanteException;

	ProducteurDTO loadProducteurByNumero(String numprd) throws EntityPersisitanteException ;


}
