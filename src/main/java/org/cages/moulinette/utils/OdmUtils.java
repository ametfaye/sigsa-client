package org.cages.moulinette.utils;

import java.util.ArrayList;
import java.util.List;

import org.cages.moulinette.dto.AgentDTO;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OdmUtils {

	public static List<AgentDTO> mocklist = new ArrayList<>();
	
	public List<AgentDTO> loadALLMock () {
		AgentDTO adto  = new AgentDTO();
		mocklist = new ArrayList<>();
		
		adto = new AgentDTO("731552C" , "MME" , "AW", "NDEYE MAGATTE",  "NIAMEY", "F");
		adto.chargerDonneesEtablissement("MINISTERE DES FINANCES ET DU BUDGET", 1L , "DAGE" , 1L);
		adto.chargerDonneesFonction("CONSULTANT CYBERSÉCURITÉ", "INGENIEURS INFORMATICIENS" , "213538" , "5CL", "02", "SG21");
		mocklist.add(adto);
	
		adto = new AgentDTO("612915F" , "MME" , "TOURE", "FATOU",  "DAKAR", "F");
		adto.chargerDonneesEtablissement("MINISTERE DES FINANCES ET DU BUDGET", 2L , "DIR. TRAITEMENT AUTOMATIQUE INFORMATION" , 2L);
		adto.chargerDonneesFonction("612915F", "INGENIEURS INFORMATICIENS" , "262085" , "3CL", "02", "SG21");
		mocklist.add(adto);
		
		adto = new AgentDTO("668974B" , "MME" , "FALL", "NDEYE SODA",  "DAKAR", "F");
		adto.chargerDonneesEtablissement("MINISTERE DES FINANCES ET DU BUDGET", 1L , "DIR. TRAITEMENT AUTOMATIQUE INFORMATION" , 1L);
		adto.chargerDonneesFonction("668974B", "INGENIEURS INFORMATICIENS" , "200617" , "5CL", "01", "SG21");
		mocklist.add(adto);
		
		adto = new AgentDTO("679031J" , "MR" , "KANE", "IDRISSA",  "DAKAR", "M");
		adto.chargerDonneesEtablissement("MINISTERE DES FINANCES ET DU BUDGET", 1L , "DIR. TRAITEMENT AUTOMATIQUE INFORMATION" , 1L);
		adto.chargerDonneesFonction("668974B", "INGENIEURS INFORMATICIENS" , "200617" , "5CL", "01", "SG21");
		mocklist.add(adto);
		
		adto = new AgentDTO("662448F" , "MME" , "FAYE", "NDEYE MAMY",  "DAKAR", "M");
		adto.chargerDonneesEtablissement("MINISTERE DES FINANCES ET DU BUDGET", 4L , "SYSTEME INTEGRE DE GESTION DE L'INFORMATION FINANCIRE (SIGIF)" , 4L);
		adto.chargerDonneesFonction("668974B", "INGENIEURS INFORMATICIENS" , "213538" , "5CL", "02", "SG21");
		mocklist.add(adto);
		
		adto = new AgentDTO("634448H" , "MR" , "MBACKE", "CHEIKH",  "DAKAR", "M");
		adto.chargerDonneesEtablissement("MINISTERE De L'INTERIEUR", 4L , "SERVICE SUIV BUDGETAIRE" , 2L);
		adto.chargerDonneesFonction("634448H", "INGENIEURS ECONMIQUES" , "213535" , "6CL", "04", "SG41");
		mocklist.add(adto);
		
		
		return mocklist;
	}
	
	public static  AgentDTO  loadALLMockByMatricule (String mat) {
		return mocklist.stream().filter(x -> mat.equals(x.getMatricule())).findAny().orElse(null); 
	}
	
	
	public static boolean    isNullOrEmpty (String str) {
		return  str == null || str.trim().length() == 0 ? true : false;
	}
	
	public static boolean    isValidateEmailAdresse (String email) {
		String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
		return email.matches(regexPattern) ? true : false;
	}


}
