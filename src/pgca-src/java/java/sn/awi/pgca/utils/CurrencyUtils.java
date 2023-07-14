package sn.awi.pgca.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class CurrencyUtils {
	private static DecimalFormat				decimalFormat					= new DecimalFormat();
	private static DecimalFormatSymbols	decimalFormatSymbols	= new DecimalFormatSymbols();

	private static String								FRANC_CFA							= "F CFA";

	static {
		decimalFormat.setGroupingSize(3);
		decimalFormat.setGroupingUsed(true);
		decimalFormat.setDecimalSeparatorAlwaysShown(false);
		decimalFormatSymbols.setGroupingSeparator(' ');
		decimalFormatSymbols.setCurrencySymbol("XOF");
		decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);
	}

	public static String formatMontant(double d) {
		return decimalFormat.format(d) + " " + FRANC_CFA;
	}

	public static String formatMontantSansMonnaie(double d) {
		return decimalFormat.format(d);
	}
}
