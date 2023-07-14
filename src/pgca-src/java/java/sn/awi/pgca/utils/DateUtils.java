package sn.awi.pgca.utils;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public abstract class DateUtils implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 398044415515120209L;

	/**
	 * 
	 */

	private static final SimpleDateFormat	dateFormat			= new SimpleDateFormat("dd/MM/yyyy");

	private static final SimpleDateFormat	dateFormatYYYYMMDD			= new SimpleDateFormat("yyyy-MM-dd");

	private static final SimpleDateFormat	dateTimeFormat	= new SimpleDateFormat("dd-MM-yyyy hh:mm");

	public static String formatDate(Date date) {
		// Calendar calendar = Calendar.getInstance();
		// calendar.setTime(date);
		// String mois = ((calendar.get(Calendar.MONTH) + 1) < 10 ? "0" : "") +
		// String.valueOf(calendar.get(Calendar.MONTH) + 1);
		// String jour = (calendar.get(Calendar.DAY_OF_MONTH) < 10 ? "0" : "") +
		// String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
		// String annee = String.valueOf(calendar.get(Calendar.YEAR)).substring(2);
		// String retour = jour + "/" + mois + "/" + annee;
		return dateFormat.format(date);
	}

	public static String formatDateAAAAMMDD(Date date) {
		return dateFormatYYYYMMDD.format(date);
	}

	public static Date parseDateAAAAMMDD(String date) throws ParseException{
		return dateFormatYYYYMMDD.parse(date);
	}
	
	public static void initHourMinSec(Calendar cal) {
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
	}

	public static Date constructDate(String date, String heure, String minute) throws ParseException {
		Calendar cal = new GregorianCalendar();
		Date d = dateFormat.parse(date);
		cal.setTime(d);
		initHourMinSec(cal);
		if (heure != null && heure.length() > 0)
			cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(heure));
		if (minute != null && minute.length() > 0)
			cal.set(Calendar.MINUTE, Integer.parseInt(minute));

		// if (heure!=null)
		// d.setHours(Integer.parseInt(heure));
		// if (minute!=null)
		// d.setMinutes(Integer.parseInt(minute));

		return cal.getTime();
	}

	public static String formatDate2(Date date) {
		return dateFormat.format(date);
	}

	public static String formatDateTime(Date date) {
		return dateTimeFormat.format(date);
	}

	public static int nbJours(Date d1, Date d2) {
		GregorianCalendar dd1 = new GregorianCalendar();
		dd1.setTime(d1);
		GregorianCalendar dd2 = new GregorianCalendar();
		dd2.setTime(d2);

		int yearFut = dd1.get(Calendar.YEAR);
		int yearPast = dd2.get(Calendar.YEAR);
		int nbJours = dd1.get(Calendar.DAY_OF_YEAR) - dd2.get(Calendar.DAY_OF_YEAR);

		if (yearFut != yearPast) {
			for (int k = yearPast; k < yearFut; k++) {
				if (dd1.isLeapYear(k))
					nbJours += 366;
				else
					nbJours += 365;
			}
			for (int k = yearFut; k < yearPast; k++) {
				if (dd1.isLeapYear(k))
					nbJours -= 366;
				else
					nbJours -= 365;
			}
		}
		return nbJours;
	}
	
	public static int calculAge(Calendar maintenant, Calendar dateNais) {
    int age = maintenant.get(Calendar.YEAR) - dateNais.get(Calendar.YEAR);
    if ((dateNais.get(Calendar.MONTH) > maintenant.get(Calendar.MONTH))
    || (dateNais.get(Calendar.MONTH) == maintenant.get(Calendar.MONTH) && dateNais.get(Calendar.DAY_OF_MONTH) > maintenant.get(Calendar.DAY_OF_MONTH))) {
      age--;
    }
    return age;
	}
}
