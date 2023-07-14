package org.cages.moulinette.service.impl;

import java.security.SecureRandom;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.cages.moulinette.dto.EnrollementDTO;
import org.cages.moulinette.dto.ProducteurDTO;
import org.cages.moulinette.dto.factory.FactoryBean;
import org.cages.moulinette.exceptions.EntityPersisitanteException;
import org.cages.moulinette.model.Adresse;
import org.cages.moulinette.model.BienAgricoleProducteur;
import org.cages.moulinette.model.Commune;
import org.cages.moulinette.model.Contact;
import org.cages.moulinette.model.Departement;
import org.cages.moulinette.model.PersonnePhysique;
import org.cages.moulinette.model.Producteur;
import org.cages.moulinette.repository.AdresseRepository;
import org.cages.moulinette.repository.BienAgricoleRepository;
import org.cages.moulinette.repository.CommuneRepository;
import org.cages.moulinette.repository.ContactRepository;
import org.cages.moulinette.repository.DepartementRepository;
import org.cages.moulinette.repository.PersonnePhysiqueRepository;
import org.cages.moulinette.repository.ProducteurRepository;
import org.cages.moulinette.service.IProducteurService;
import org.cages.moulinette.utils.CONSTANTES;
import org.cages.moulinette.utils.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("producteurService")
public class ProducteurServiceImpl implements IProducteurService {

	@Autowired
	private PersonnePhysiqueRepository repoPersonnes;

	@Autowired
	private AdresseRepository repoAdresse;

	@Autowired
	private BienAgricoleRepository repoBienAgricole;

	@Autowired
	private ContactRepository repoContact;

	@Autowired
	private ProducteurRepository producteurRepository;

	@Autowired
	private DepartementRepository departementRepository;

	@Autowired
	private CommuneRepository communeRepository;

	@Override
	public Producteur createProducteur(String typeProducteur, List<PersonnePhysique> personnePhysiques,
			ProducteurDTO producateurAcrer, Date dateImmatriculation) {
		String easy = RandomString.digits + "abcdefhijkprstuvwx";
		RandomString numProd = new RandomString(10, new SecureRandom(), easy);
		String libelle = "" ; //producateurAcrer.getNomCommercial();
		if ("PersonnePhysique".equals(typeProducteur)) {
			libelle = producateurAcrer.getPrenom() + " " + producateurAcrer.getNom();
		}
		Producteur producteur = new Producteur(numProd.nextString(), producateurAcrer.getNumNinea(),
				producateurAcrer.getAdresse(), libelle, producateurAcrer.getDescriptif(), producateurAcrer.getEmail(),
				"", dateImmatriculation, new HashSet<PersonnePhysique>(personnePhysiques));
		return producteurRepository.save(producteur);
	}

	@Override
	public ProducteurDTO createProducteur(EnrollementDTO infosEnrollement) throws EntityPersisitanteException {

		Adresse a = createAdresse(infosEnrollement);
		repoAdresse.save(a);

		Contact c = FactoryBean.createContactl(infosEnrollement);
		repoContact.save(c);

		PersonnePhysique p = FactoryBean.createPersonne(infosEnrollement);
		Set<PersonnePhysique> peps = new HashSet();
		peps.add(p);
		repoPersonnes.save(peps);

		Producteur newProducteur = createProducteur(infosEnrollement, peps, c, a);

		BienAgricoleProducteur bien = FactoryBean.createBienAgricol(infosEnrollement);
		bien.setAdresse(a);
		bien.setProprietaire(newProducteur);
		Set<BienAgricoleProducteur> biens = new HashSet();
		biens.add(bien);
		repoBienAgricole.save(biens);

		newProducteur.setBiensAgricoles(biens);
		producteurRepository.save(newProducteur);

		return FactoryBean.producteurAsProducteurDTO(newProducteur);
	}

	
	public Producteur createProducteur(EnrollementDTO infosEnrollement, Set<PersonnePhysique> peps, Contact c,
			Adresse a) {
		Producteur newProducteur = new Producteur();
		newProducteur.setPersonnePhysiques(peps);
		newProducteur.setAdresseComplete(a);
		newProducteur.setContact(c);
		newProducteur.setLibelle(infosEnrollement.getLibelle());
		newProducteur.setNumNinea(infosEnrollement.getNinea());
		newProducteur.setTypeProd(infosEnrollement.getCategorie());

		java.util.Date d1 = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(d1.getTime());

		newProducteur.setDateImmatriculation(sqlDate);
		String numeroNationalNUP = FactoryBean.generateNumeroNUP(infosEnrollement);
		// SI ne N° existe , on rappelle la methode pour générer un new
		Producteur p = producteurRepository.findByNumeroProd(numeroNationalNUP.toString().toUpperCase());
		if (p != null)
			createProducteur(infosEnrollement, peps, c, a);

		newProducteur.setNumeroProd(numeroNationalNUP.toUpperCase());
		producteurRepository.save(newProducteur);
		return newProducteur;
	}

	public Adresse createAdresse(EnrollementDTO infosEnrollement) {
		Departement d = departementRepository.findOne(infosEnrollement.getIdDepartement());
		Commune c = communeRepository.findOne(infosEnrollement.getIdCommune());

		if (d == null || c == null)
			return null;

		Adresse a = new Adresse();
		a.setCommune(c);
		a.setDepartement(d);
		a.setRegion(d.getRegion());
		a.setQuartier(infosEnrollement.getAdresse());
		a.setLibelle(infosEnrollement.getAdresse());
		return a;
	}

	
	
	/*** DTO  READ ***/
	@Override
	public List<ProducteurDTO> loadListProducteur() throws EntityPersisitanteException {
		List<Producteur> lp = producteurRepository.loadLastListProducteur(CONSTANTES.NB_PRODUCTEUR);
		return FactoryBean.createListProducteur(lp);
	}
	
	
	
	
	
	@Override
	public ProducteurDTO loadProducteurById(Long idProducteur) throws EntityPersisitanteException {
		Producteur p = producteurRepository.findOne(idProducteur);
		return FactoryBean.producteurAsProducteurDTO(p);
	}

	@Override
	public ProducteurDTO loadProducteurByNumero(String numprd) throws EntityPersisitanteException {
		Producteur p = producteurRepository.findByNumeroProd(numprd);
		return FactoryBean.producteurAsProducteurDTO(p);
	}
	
	
}
