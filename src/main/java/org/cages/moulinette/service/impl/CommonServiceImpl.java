package org.cages.moulinette.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage.Severity;

import org.cages.moulinette.dto.CamionDTO;
import org.cages.moulinette.dto.CampagneAgricoleDTO;
import org.cages.moulinette.dto.CollaborateurDTO;
import org.cages.moulinette.dto.CoupleDTO;
import org.cages.moulinette.dto.EngraisDTO;
import org.cages.moulinette.dto.FournisseurDTO;
import org.cages.moulinette.dto.InfosCommunesDTO;
import org.cages.moulinette.dto.IntrantDTO;
import org.cages.moulinette.dto.IntrantTypeDTO;
import org.cages.moulinette.dto.LitigesDTO;
import org.cages.moulinette.dto.MagasinDTO;
import org.cages.moulinette.dto.PersonneDTO;
import org.cages.moulinette.dto.PointDeVenteDTO;
import org.cages.moulinette.dto.ProduitDTO;
import org.cages.moulinette.dto.ProgrammeAgricolDTO;
import org.cages.moulinette.dto.QuotaMiseEnplaceDTO;
import org.cages.moulinette.dto.ServiceDTO;
import org.cages.moulinette.dto.VentesDTO;
import org.cages.moulinette.exceptions.EntityDBDAOException;
import org.cages.moulinette.mailer.model.GenericModel;
import org.cages.moulinette.model.Commune;
import org.cages.moulinette.model.Departement;
import org.cages.moulinette.model.Fournisseur;
import org.cages.moulinette.model.Intrant;
import org.cages.moulinette.model.PointDeVente;
import org.cages.moulinette.model.Quota;
import org.cages.moulinette.model.Region;
import org.cages.moulinette.repository.CommuneRepository;
import org.cages.moulinette.repository.CompagnieRepository;
import org.cages.moulinette.repository.DepartementRepository;
import org.cages.moulinette.repository.DestinationRepository;
import org.cages.moulinette.repository.EtatOrdreDeMissionRepository;
import org.cages.moulinette.repository.FonctionRepository;
import org.cages.moulinette.repository.GradeRepository;
import org.cages.moulinette.repository.PersonnePhysiqueRepository;
import org.cages.moulinette.repository.RegionRepository;
import org.cages.moulinette.repository.SousServiceRefRepo;
import org.cages.moulinette.service.ICommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Service("commonServices")
public class CommonServiceImpl implements ICommonService {

//	@Autowired
//	private CommonRepo commonTepo;
//

	@Autowired
	private RegionRepository regionRepo;
	
	@Autowired
	private DepartementRepository departementRepo;
	
	@Autowired
	private CommuneRepository communeRepo;
	
	@Autowired
	private CompagnieRepository compagniesRepo;
	
	@Autowired
	private SousServiceRefRepo sousServiceRepo;

	@Autowired
	private DestinationRepository destinationRepo;

	@Autowired
	private PersonnePhysiqueRepository pepRepo;
	
	@Autowired
	private FonctionRepository fonctionRepo;

	@Autowired
	private GradeRepository gradeRepo;

	
	@Autowired
	private EtatOrdreDeMissionRepository etatOrdreDeMissionRepo;

	private ServiceDTO srvtmp;

		
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void sendMessage(String msg) {
		
		
	    kafkaTemplate.send("testcgos1", msg);
	    
	    kafkaTemplate.send("testcgos1", "DR16" , "PARIS");

	    kafkaTemplate.send("testcgos1", "DR01", "DRCGOS LYON");
	}

	
	
	
	@Override
	public List<CampagneAgricoleDTO> loadAllCampagneAgricole() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GenericModel getEntityModelById(Class<?> modele, Long idEntitity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CoupleDTO> loadPays() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CoupleDTO getPays(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	CoupleDTO dto;
	@Override
	public List<CoupleDTO> loadRegion() {
		List<Region> ll = regionRepo.findAll();
		
		
		List<CoupleDTO> dtoR  = new ArrayList<>();
		
		ll.forEach( r ->{
			dto = new CoupleDTO();
			dto.setIdGenerique(r.getId());
			dto.setValeurGenerique(r.getLibelle());
			dtoR.add(dto);
		});
		return dtoR;
	}


	@Override
	public List<CoupleDTO> loadAllDepartementOfRegion(Long idRegion) {
	Set<Departement> ll = departementRepo.findByRegion(regionRepo.findOne(idRegion));
		
		List<CoupleDTO> ldept  = new ArrayList<>();
		
		ll.forEach( r ->{
			dto = new CoupleDTO();
			dto.setIdGenerique(r.getId());
			dto.setValeurGenerique(r.getLibelle());
			ldept.add(dto);
		});
		return ldept;
	}

	@Override
	public List<CoupleDTO> loadAllCommunOfdepartement(Long idDepartement) {
		List<Commune> ll = communeRepo.findByDepartement(departementRepo.findOne(idDepartement));
		
		List<CoupleDTO> ldept  = new ArrayList<>();
		
		ll.forEach( r ->{
			dto = new CoupleDTO();
			dto.setIdGenerique(r.getId());
			dto.setValeurGenerique(r.getLibelle());
			dto.setDepartement(r.getDepartement().getLibelle());
			ldept.add(dto);
		});
		return ldept;
	}
	
	
	@Override
	public List<CoupleDTO> loadAllDepartement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CoupleDTO> loadAllCommun() {
		// TODO Auto-generated method stub
		return null;
	}

	


	@Override
	public List<CoupleDTO> loadProfil() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CoupleDTO getProfil(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CoupleDTO> loadRegionbyPays(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CoupleDTO> loadPointDeCollecte() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CoupleDTO> loadPointDeVente() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateProduit(ProduitDTO pdo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<IntrantTypeDTO> loadAllTypeIntrants() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createIntrant(IntrantDTO idto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<PointDeVenteDTO> loadAllPointDeVente(boolean refreshList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PointDeVenteDTO> loadAllPointDeVenteByCommuneId(Long idCommne) throws EntityDBDAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkExistingPointDeVenteByName(String libelle, Long idCommune) throws EntityDBDAOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createPointdeVente(PointDeVenteDTO pv) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createMagasin(MagasinDTO dto) throws EntityDBDAOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<CoupleDTO> loadAllProgramme() throws EntityDBDAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CoupleDTO> loadAllProgramme(int statut) throws EntityDBDAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CoupleDTO> loadAllStock() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EngraisDTO> loadAllEngraisDTO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteEntityModele(Long idEntity, Class<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deletePointdeVente(PointDeVenteDTO pdto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updatePointdeVente(PointDeVenteDTO pointDeVente) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PointDeVenteDTO loadPointDeVenteById(Long idPv) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CoupleDTO> loadCamionsBychauffeur(Long idChauffeur) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CoupleDTO> loadchauffeurByTranporteur(Long idtransporter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CoupleDTO> loadAllTransporteur() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PersonneDTO> loadAllChauffeur() throws EntityDBDAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PersonneDTO loadChauffeurByIdChauffeur(Long idChauffeur) throws EntityDBDAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CoupleDTO> loadAllCamion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CollaborateurDTO> loadAllCollaborateurDTOsPersonne() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CoupleDTO loadTransnporteurbyId(Long transporteurid) throws EntityDBDAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CoupleDTO loadCamionById(Long camionid) throws EntityDBDAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VentesDTO> loadAllVentesForManager() throws EntityDBDAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InfosCommunesDTO getDetailsCommune(String nomCommuune) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InfosCommunesDTO getDetailsCommuneById(Long idCommune) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProduitDTO> loadStockProduitByIdPointdeVenteMagasinier(Long idStockPointDeVente)
			throws EntityDBDAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String loadAllProductInJsonFormat(int maxRecords) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CoupleDTO> loadAllProduitByIdTypeProduit(Long subventionProduit) throws EntityDBDAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CollaborateurDTO> loadAllCollaborateurDTOs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PersonneDTO> loadUtilisateurFromPointDeCollecte(Long idPointDeCollecte) throws EntityDBDAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void showMessage(Severity severity, String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<IntrantTypeDTO> loadReferentielTypeIntrant() throws EntityDBDAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createPointdeCollecte(PointDeVenteDTO pv) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<CamionDTO> loadAllCamionDTO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CoupleDTO> loadAllBLOfSelectedProgramme(Long idProgramme) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Quota> loadListQuotabyIdIntrant(Long idIntrant) throws EntityDBDAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Quota> loadListQuotabyIdIntrantAndDepartement(Long idIntrant, Long idDepartement)
			throws EntityDBDAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateQuotaMEP(QuotaMiseEnplaceDTO listQuotaMiseEnplaceSelectedQuota) throws EntityDBDAOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<CoupleDTO> loadAllCamionByIdTransporteur(Long idRegion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CoupleDTO> loadAllChauffeurByIdTransporteur(Long idCamion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createFournisseur(FournisseurDTO f) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<FournisseurDTO> loadAllFornisseurs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FournisseurDTO> loadFournisseurByProduit(Long idProduit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Intrant verifierStockRestantAupresFournisseur(Long idIntrantAMettreEnplace, Long fournisseurId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProduitDTO diminuerStockFournissuerApresMEP(Long idIntrantAMettreEnplace, Long fournisseurId,
			Double quantiteAdeduire) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String loadCachedData(String listOfCamionFromDBInJSON, String listOfChauffeurFromDBInJSON,
			String listOfTransporteurFromDBInJSON) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LitigesDTO> loadAllLitiges() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProgrammeAgricolDTO> loadAllProgrammeDTO() throws EntityDBDAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CoupleDTO> loadAllACampagne() throws EntityDBDAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String loadAllCommunAndDepartement() throws EntityDBDAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String loadAllIntrantforAutoCoplete() throws EntityDBDAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Intrant loadIntrantByName(String intrantName) throws EntityDBDAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String loadAllFornisseursForAutoComplete() throws EntityDBDAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Fournisseur loadFournisseursByName(String intrantName) throws EntityDBDAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String loadAllProgrammeforAutoCoplete() throws EntityDBDAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long loadFournisseuridByName(String intrantName) throws EntityDBDAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long loadIntrantIdByName(String intrantName) throws EntityDBDAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long loadIProgrammeIdByName(String intrantName) throws EntityDBDAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long loadIPoitDeVenteIdByName(String intrantName) throws EntityDBDAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer loadAllFornisseursForAutoCompleteSize() throws EntityDBDAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createPointdeVenteCommune(Long idCommune, String nomCommune) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String loadAllDepartementAutoCoplete() throws EntityDBDAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String loadAllTransporteurforAutoCoplete() throws EntityDBDAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long loadITransporeurByName(String intrantName) throws EntityDBDAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long loadIDepartementByName(String intrantName) throws EntityDBDAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MagasinDTO> loadReferentielMagasin() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CoupleDTO> loadAllPointdeVentesOfDepartemnt(Long idDepartement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CoupleDTO> loadAffectationsByIdUser(Long idUser) throws EntityDBDAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PointDeVente> loadAllIntrantFromAffectations(Long id) throws EntityDBDAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProduitDTO> loadStockProduitForOL() throws EntityDBDAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MagasinDTO> loadReferentielMagasinByDepartement(Long idDepartement) throws EntityDBDAOException {
		// TODO Auto-generated method stub
		return null;
	}

}
