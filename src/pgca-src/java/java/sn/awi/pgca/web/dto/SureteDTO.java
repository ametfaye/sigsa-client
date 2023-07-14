package sn.awi.pgca.web.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SureteDTO implements Serializable {

	private static final long	serialVersionUID	= -2048543641001911902L;
	private Long							id;

	private String						nature;
	private Date							dateSurete;
	private int								dureeInscription;
	private char							natureInscription = 'D';
	private boolean						pacteCommissoire;
	private double						montantMaxGaranti;
	private double						montantPrincipal;
	private double						tauxInteret;
	private String						conditionsExigibilite;

	private List<CreanceDTO>	creancesActuelles;

	private CreanceDTO				creanceActuelleDTO;
	private CreanceDTO				creanceActuelleDTO1;

	private List<CreanceDTO>	creancesFutures;
	private CreanceDTO				creanceFutureDTO;
	private CreanceDTO				creanceFutureDTO1;

	private List<BienDTO>			biens;
	private BienDTO						bienDTO;
	private BienDTO						bienDTO1;

	private boolean						bienDeplacable;

	private boolean						bienAlienable;

	private AdresseDTO				bienLocalisationFuture;

	public SureteDTO() {
		creancesActuelles = new ArrayList<CreanceDTO>();
		creanceActuelleDTO = new CreanceDTO();
		creanceActuelleDTO1 = new CreanceDTO();

		creancesFutures = new ArrayList<CreanceDTO>();
		creanceFutureDTO = new CreanceDTO();
		creanceFutureDTO1 = new CreanceDTO();

		biens = new ArrayList<BienDTO>();
		bienDTO = new BienDTO();
		bienDTO1 = new BienDTO();
		bienLocalisationFuture = new AdresseDTO();
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public Date getDateSurete() {
		return dateSurete;
	}

	public void setDateSurete(Date dateSurete) {
		this.dateSurete = dateSurete;
	}

	public int getDureeInscription() {
		return dureeInscription;
	}

	public void setDureeInscription(int dureeInscription) {
		this.dureeInscription = dureeInscription;
	}

	public char getNatureInscription() {
		return natureInscription;
	}

	public void setNatureInscription(char natureInscription) {
		this.natureInscription = natureInscription;
	}

	public boolean isPacteCommissoire() {
		return pacteCommissoire;
	}

	public void setPacteCommissoire(boolean pacteCommissoire) {
		this.pacteCommissoire = pacteCommissoire;
	}

	public double getMontantMaxGaranti() {
		return montantMaxGaranti;
	}

	public void setMontantMaxGaranti(double montantMaxGaranti) {
		this.montantMaxGaranti = montantMaxGaranti;
	}

	public List<CreanceDTO> getCreancesActuelles() {
		return creancesActuelles;
	}

	public void setCreancesActuelles(List<CreanceDTO> creancesActuelles) {
		this.creancesActuelles = creancesActuelles;
	}

	public List<CreanceDTO> getCreancesFutures() {
		return creancesFutures;
	}

	public void setCreancesFutures(List<CreanceDTO> creancesFutures) {
		this.creancesFutures = creancesFutures;
	}

	public List<BienDTO> getBiens() {
		return biens;
	}

	public void setBiens(List<BienDTO> biens) {
		this.biens = biens;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CreanceDTO getCreanceActuelleDTO() {
		return creanceActuelleDTO;
	}

	public void setCreanceActuelleDTO(CreanceDTO creanceActuelleDTO) {
		this.creanceActuelleDTO = creanceActuelleDTO;
	}

	public CreanceDTO getCreanceActuelleDTO1() {
		return creanceActuelleDTO1;
	}

	public void setCreanceActuelleDTO1(CreanceDTO creanceActuelleDTO1) {
		this.creanceActuelleDTO1 = creanceActuelleDTO1;
	}

	public CreanceDTO getCreanceFutureDTO() {
		return creanceFutureDTO;
	}

	public void setCreanceFutureDTO(CreanceDTO creanceFutureDTO) {
		this.creanceFutureDTO = creanceFutureDTO;
	}

	public CreanceDTO getCreanceFutureDTO1() {
		return creanceFutureDTO1;
	}

	public void setCreanceFutureDTO1(CreanceDTO creanceFutureDTO1) {
		this.creanceFutureDTO1 = creanceFutureDTO1;
	}

	public BienDTO getBienDTO() {
		return bienDTO;
	}

	public void setBienDTO(BienDTO bienDTO) {
		this.bienDTO = bienDTO;
	}

	public BienDTO getBienDTO1() {
		return bienDTO1;
	}

	public void setBienDTO1(BienDTO bienDTO1) {
		this.bienDTO1 = bienDTO1;
	}

	public String getConditionsExigibilite() {
		return conditionsExigibilite;
	}

	public void setConditionsExigibilite(String conditionsExigibilite) {
		this.conditionsExigibilite = conditionsExigibilite;
	}

	public boolean isBienDeplacable() {
		return bienDeplacable;
	}

	public void setBienDeplacable(boolean bienDeplacable) {
		this.bienDeplacable = bienDeplacable;
	}

	public boolean isBienAlienable() {
		return bienAlienable;
	}

	public void setBienAlienable(boolean bienAlienable) {
		this.bienAlienable = bienAlienable;
	}

	public AdresseDTO getBienLocalisationFuture() {
		return bienLocalisationFuture;
	}

	public void setBienLocalisationFuture(AdresseDTO bienLocalisationFuture) {
		this.bienLocalisationFuture = bienLocalisationFuture;
	}

	public double getMontantPrincipal() {
		return montantPrincipal;
	}

	public void setMontantPrincipal(double montantPrincipal) {
		this.montantPrincipal = montantPrincipal;
	}

	public double getTauxInteret() {
		return tauxInteret;
	}

	public void setTauxInteret(double tauxInteret) {
		this.tauxInteret = tauxInteret;
	}

}
