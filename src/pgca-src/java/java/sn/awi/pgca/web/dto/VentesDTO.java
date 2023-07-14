package sn.awi.pgca.web.dto;

public class VentesDTO {
	
	private static final long serialVersionUID = 4740612708609554436L;
	private Long ventes_id;
	private String zoneVente;
	private String libelleProduitVentes;
	private Long idstockVente;
	private Long idPointDeVente;
	private String stockLibelle; 
	private Long idvendeur;
	private String	 vendeurLibelle;
	private int quantiteProduitVendu;	
	private String client;
	private String adresseclient;
	private String telclient;
	private float montantVente;
	
	private Long idProduitAssocie;

	
	private float montantEncaisse;
	
	private float montantEncaisseParCheque;
	private float montantEncaisseParEspece;
	private float montantEncaisseParBonDeSubvention;
	private float montantEncaisseBLP;
	private String numeroBLP;


	
	private String moyenPaiement;
	
	private String											dateVente;
	
	private float prixUnitaire;


	public Long getVentes_id() {
		return ventes_id;
	}

	public void setVentes_id(Long ventes_id) {
		this.ventes_id = ventes_id;
	}

	public Long getIdstockVente() {
		return idstockVente;
	}

	public void setIdstockVente(Long idstockVente) {
		this.idstockVente = idstockVente;
	}

	public String getStockLibelle() {
		return stockLibelle;
	}

	public void setStockLibelle(String stockLibelle) {
		this.stockLibelle = stockLibelle;
	}

	public Long getIdvendeur() {
		return idvendeur;
	}

	public void setIdvendeur(Long idvendeur) {
		this.idvendeur = idvendeur;
	}

	public String getVendeurLibelle() {
		return vendeurLibelle;
	}

	public void setVendeurLibelle(String vendeurLibelle) {
		this.vendeurLibelle = vendeurLibelle;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public float getMontantVente() {
		return montantVente;
	}

	public void setMontantVente(float montantVente) {
		this.montantVente = montantVente;
	}

	public String getMoyenPaiement() {
		return moyenPaiement;
	}



	public Long getIdPointDeVente() {
		return idPointDeVente;
	}

	public void setIdPointDeVente(Long idPointDeVente) {
		this.idPointDeVente = idPointDeVente;
	}

	public void setMoyenPaiement(String moyenPaiement) {
		this.moyenPaiement = moyenPaiement;
	}

	public String getDateVente() {
		return dateVente;
	}

	public void setDateVente(String dateVente) {
		this.dateVente = dateVente;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getLibelleProduitVentes() {
		return libelleProduitVentes;
	}

	public void setLibelleProduitVentes(String libelleProduitVentes) {
		this.libelleProduitVentes = libelleProduitVentes;
	}

	public int getQuantiteProduitVendu() {
		return quantiteProduitVendu;
	}

	public void setQuantiteProduitVendu(int quantiteProduitVendu) {
		this.quantiteProduitVendu = quantiteProduitVendu;
	}

	public float getMontantEncaisse() {
		return montantEncaisse;
	}

	public void setMontantEncaisse(float montantEncaisse) {
		this.montantEncaisse = montantEncaisse;
	}

	public String getAdresseclient() {
		return adresseclient;
	}

	public void setAdresseclient(String adresseclient) {
		this.adresseclient = adresseclient;
	}

	public String getTelclient() {
		return telclient;
	}

	public void setTelclient(String telclient) {
		this.telclient = telclient;
	}

	public String getZoneVente() {
		return zoneVente;
	}

	public void setZoneVente(String zoneVente) {
		this.zoneVente = zoneVente;
	}

	public float getMontantEncaisseParCheque() {
		return montantEncaisseParCheque;
	}

	public void setMontantEncaisseParCheque(float montantEncaisseParCheque) {
		this.montantEncaisseParCheque = montantEncaisseParCheque;
	}

	public float getMontantEncaisseParEspece() {
		return montantEncaisseParEspece;
	}

	public void setMontantEncaisseParEspece(float montantEncaisseParEspece) {
		this.montantEncaisseParEspece = montantEncaisseParEspece;
	}

	public float getMontantEncaisseParBonDeSubvention() {
		return montantEncaisseParBonDeSubvention;
	}

	public void setMontantEncaisseParBonDeSubvention(float montantEncaisseParBonDeSubvention) {
		this.montantEncaisseParBonDeSubvention = montantEncaisseParBonDeSubvention;
	}

	public Long getIdProduitAssocie() {
		return idProduitAssocie;
	}

	public void setIdProduitAssocie(Long idProduitAssocie) {
		this.idProduitAssocie = idProduitAssocie;
	}

	public float getPrixUnitaire() {
		return prixUnitaire;
	}

	public void setPrixUnitaire(float prixUnitaire) {
		this.prixUnitaire = prixUnitaire;
	}

	public float getMontantEncaisseBLP() {
		return montantEncaisseBLP;
	}

	public String getNumeroBLP() {
		return numeroBLP;
	}

	public void setMontantEncaisseBLP(float montantEncaisseBLP) {
		this.montantEncaisseBLP = montantEncaisseBLP;
	}

	public void setNumeroBLP(String numeroBLP) {
		this.numeroBLP = numeroBLP;
	}

}
