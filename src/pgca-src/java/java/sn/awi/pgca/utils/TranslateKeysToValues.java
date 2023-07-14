package sn.awi.pgca.utils;

public class TranslateKeysToValues {

/*
 * Renvoi la valeur associé a une clé  pour toute les données de reference : key = value
 * 
 * Par exemple pour  pour les  type de formalites 1 correspond a l'immatriculation personne morale , 2 Modification  ....
 * 
 * 
 * 
 * 
 */
	
	public static String translate(int typeRef, int key) 
		{
				String imat[] = 
								{
										"Immatriculation Personne Morale", 
										"Immatriculation personne physique", 
										"Immatriculation GIE" , 
										"Déclaration Entreprenant"
								};
				
				if (typeRef == 1) // formalites des immats  
				{
						if (imat[key] != null)
						{
								return "UtilsDefaultVAlues";
						}
						else
								return imat[key] ;
				}
				return "UtilsDefaultVAlues";
		}
}
