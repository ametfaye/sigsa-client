package sn.awi.pgca.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class FormatterUtils {


	
	private static DecimalFormat decimalFormat = new DecimalFormat();
	
	private static DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
	
	public static void main(String[] args) {
		Currency currency = Currency.getInstance("XOF");
//		decimalFormat.setCurrency(currency);
//		decimalFormat.setGroupingSize(3);
//		decimalFormat.setGroupingUsed(true);
		decimalFormat.setDecimalSeparatorAlwaysShown(false);
		decimalFormatSymbols.setGroupingSeparator(' ');
		decimalFormatSymbols.setCurrency(currency);
		decimalFormatSymbols.setMonetaryDecimalSeparator('.');
		decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);
		double d = 15000000;
		System.out.println(currency);
		Locale locale = new Locale("", "SN");
		NumberFormat deFormat = NumberFormat.getCurrencyInstance(locale);
		System.out.println(decimalFormat.format(d) + currency.getCurrencyCode());
		System.out.println(deFormat.format(d));
//		String[] locales = Locale.getISOCountries();
//		System.out.println("sénégal "+locale);
//		for (String countryCode : locales) {
//	 
//			Locale obj = new Locale("", countryCode);
//	 
//			System.out.println("Country Code = " + obj.getCountry() 
//				+ ", Country Name = " + obj.getDisplayCountry()
//				+ ", langue = " + obj.getLanguage() + ", " + obj.getISO3Language());
//	 
//		}
		System.out.println("\u036F");
		System.out.println(Normalizer.normalize("Sénéweb à ndakarù", Normalizer.Form.NFD).replaceAll("[\u0300-\u036F]", ""));
	}
}
