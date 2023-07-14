package org.cages.moulinette.dto;

import org.cages.moulinette.utils.CONSTANTES;
import org.cages.moulinette.utils.OdmUtils;
import org.cages.moulinette.utils.UtilString;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AgentDTO {

	private String matricule, titre, nom, prenom, lieuNaissance, civilite;
	private String sexe;

	private String etablissement; /* ENTITE PUBLIQUE */
	private Long etablissementId;

	private Long serviceId; /* SERVICE */
	private String serviceLibelle;
	
	private Long sousServiceId;
	private String sousServiceLibelle;
	
	private String groupe;
	private Long groupeId;

	private String fonction;
	private Long fonctionId;
	
	private String corps, indice, grade, echelon, hierarchie, reisdence, telephone, email;

	private boolean selected;
	private boolean selectedChefM;
	private Long aId;
	
	public AgentDTO(String matricule, String nom, String prenom, String fonction, String indice, String grade, String groupe) {
		super();
		this.matricule = matricule;
		this.nom = nom;
		this.prenom = prenom;
		this.fonction = fonction;
		this.indice = indice;
		this.grade = grade;
		this.groupe = groupe;
	}
	
	public AgentDTO(String matricule, String nom, String prenom, Long serviceId) {
		super();
		this.matricule = matricule;
		this.nom = nom;
		this.prenom = prenom;
		this.serviceId = serviceId;
	}
	
	public AgentDTO(String m, String t, String n, String p, String ln, String s) {
		matricule = m;
		titre = t;
		nom = n;
		prenom = p;
		lieuNaissance = ln;
		sexe = s;
	}

	public void chargerDonneesEtablissement(String etb, Long etbId, String s, Long srvId) {
		etablissement = etb;
		etablissementId = etbId;
		serviceLibelle = s;
		serviceId = srvId;
	}

	public void chargerDonneesFonction(String f, String c, String i, String g, String e, String h) {
		fonction = f;
		corps = c;
		indice = i;
		grade = g;
		echelon = e;
		hierarchie = h;
	}

	public AgentDTO(String matricule, String nom, String prenom, String indice, String corps, String echelon,
			String hierarchie, String sexe, Long fctId, Long grId, Long redId, Long srvId, Long ssrvId, String email, String telephone) {
		super();
		this.matricule = matricule;
		this.nom = nom;
		this.prenom = prenom;
		this.indice = indice;
		this.corps = corps;
		this.echelon = echelon;
		this.hierarchie = hierarchie;
		this.sexe = sexe;
		this.fonctionId = fctId;
		this.groupeId = grId;
		this.etablissementId = redId;
		this.serviceId = srvId;
		this.sousServiceId = ssrvId;
		this.email = email;
		this.telephone = telephone;
	}

	public AgentDTO(Long aId ,String matricule, String nom, String prenom, String fonction, boolean selected) {
		super();
		this.aId = aId;
		this.matricule = matricule;
		this.nom = nom;
		this.prenom = prenom;
		this.fonction = fonction;
		this.selected = selected;
	}
	
	

	
	public String checkEmptyFields() {
		if (OdmUtils.isNullOrEmpty(matricule))
			return CONSTANTES.MATRICULE_OBLIGATOIRE;
		if (OdmUtils.isNullOrEmpty(nom))
			return CONSTANTES.NOM_OBLIGATOIRE;
		if (OdmUtils.isNullOrEmpty(email))
			return CONSTANTES.EMAIL_OBLIGATOIRE;
		
		if (OdmUtils.isNullOrEmpty(hierarchie))
			return CONSTANTES.HIERRARCHIE_OBLIGATOIRE;
		if (OdmUtils.isNullOrEmpty(echelon))
			return CONSTANTES.ECHALON_OBLIGATOIRE;
		if (OdmUtils.isNullOrEmpty(corps))
			return CONSTANTES.CORPS_OBLIGATOIRE;
		if (OdmUtils.isNullOrEmpty(serviceId + ""))
			return CONSTANTES.SERVICCE_OBLIGATOIRE;
		if (OdmUtils.isNullOrEmpty(etablissementId + ""))
			return CONSTANTES.ENTITEP_OBLIGATOIRE;
		if (OdmUtils.isNullOrEmpty(fonctionId + ""))
			return CONSTANTES.FONCTION_OBLIGATOIRE;
		return  CONSTANTES.NO_ERROR;
	}
	
	
		public String checkFieldsValidities() {
			if (!OdmUtils.isValidateEmailAdresse(email))
				return  CONSTANTES.EMAIL_INVALIDE;
			
			return CONSTANTES.NO_ERROR;
	
	}	
}
