package sn.awi.pgca.dataMapping;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.jfree.util.Log;

import sn.awi.pgca.web.dto.CoupleDTO;

@Named("genericFactory")
public class GenericsFactory {

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	/******
	 * Methode generique de recuperation de tous type d'objets par Reflection
	 * JAVA Cette methode utilise la reflection Java pour recuperer la valeur
	 * des attributs keyNAme & valueName de n'importe quel classe JAVA pour en
	 * créer un CoupleDTO : 
	 * 
	 * @param listOfGenericsObject
	 * @param keyNAme 
	 * @param valueName
	 * @return
	 */
	public List<CoupleDTO> createGenericCoupleDTO(List<?> listOfGenericsObject, String keyNAme, String valueName) {

		Field key;
		Field val;

		List<CoupleDTO> listCoupleDTOGenerics = new ArrayList<CoupleDTO>();
		for (Object instance : listOfGenericsObject) {

			try {
				try {
					key = instance.getClass().getDeclaredField(keyNAme);
					key.setAccessible(true);
					Long keyData = (Long) key.get(instance);

					val = instance.getClass().getDeclaredField(valueName);
					val.setAccessible(true);
					String valData = val.get(instance).toString();

					CoupleDTO coupleDTO = new CoupleDTO(keyData, valData);
					listCoupleDTOGenerics.add(coupleDTO);
				} catch (IllegalArgumentException e) {
					Log.error("Argument reflection JAVA  par la JVM:  Id ou Libelle ");
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					Log.error("L'acces a l'attribut par  par reflection JAVA est internet par la JVM:  Id ou Libelle ");
					e.printStackTrace();
				}

			} catch (NoSuchFieldException e) {
				Log.error("L'attribut a recuperer par reflection JAVA n'existe pas :  Id ou Libelle seulement  < "
						+ keyNAme + " et " + valueName + "> donné en parametre");
				e.printStackTrace();
			} catch (SecurityException e) {
				Log.error("L'acces a l'attribut par  par reflection JAVA est internet par la JVM:  Id ou Libelle ");
				e.printStackTrace();
			}

		}
		return listCoupleDTOGenerics;
	}
	
	

}
