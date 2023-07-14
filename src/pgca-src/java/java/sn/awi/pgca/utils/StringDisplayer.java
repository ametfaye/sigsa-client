package sn.awi.pgca.utils;

public class StringDisplayer {

	/**
	 * Resume une chaine en affichant les n premiers caracteres puis met trois
	 * points a la fin
	 * 
	 * Ex : Je suis un senegalis, le Senegal est situé a l'ouest de l'Afrique ->
	 * Je suis un senegalis ...
	 * 
	 * @param string
	 *          text : la chaine a resumer
	 * 
	 * @param int taille : le nombre de caractere a afficher
	 * 
	 * @return String la chaine modifi�e
	 */

	public static String cutString(String text, int taille) {
		if (text == null) {
			System.out.println("Le texte a resumé est null");
			return null;
		}

		if (taille == 0) {
			System.out.println("Le taille est null , on prend ");
			return null;
		}

		if (text.length() > taille)
			return text.substring(0, taille).concat("...");
		return text;
	}

}
