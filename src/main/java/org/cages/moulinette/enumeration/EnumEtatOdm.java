package org.cages.moulinette.enumeration;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum EnumEtatOdm {
	EA("EA"),
	TSG("TSG"),
	SSG("SSG"),
	TSGG("TSGG"),
	ASG("ASG"),
	RSG("RSG"),
	ASGG("ASGG"),
	RSGG("RSGG"),
	SSGG("SSGG"),
	BE("BE"),
	AD("AD"),
	ECH("ECH"),
	RD("RD"),
	TRANSMISCP("TCP"),
	TRANSMISSGG("TSGG");

	private String etatOdm;

	private EnumEtatOdm(String code) {
		this.etatOdm = code;
	}

	public String getCode() {
		return etatOdm;
	}

	public static String[] allowedRoles() {

		return (String[]) Stream.of(EnumEtatOdm.values()).map(e -> e.getCode()).collect(Collectors.toList()).toArray();
	}

}
