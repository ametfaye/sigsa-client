package org.cages.moulinette.enumeration;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum EnumOdmEve {
	ODM_CRME("CM"), 
	ODM_TRCP("TCS"), 
	ODM_TRSGG("TSGG"), 
	ODM_ACME("AM"), 
	ODM_REME("RM"), 
	ODM_ACMSGG("AMSGG"),
	ODM_REMESGG("RMSGG"),
	ODM_SIMSG("SMSG"),
	ODM_ACMSG("AMSG"),
	ODM_REMESG("RMSG"),
	ODM_AJPME("AP"), 
	ODM_REPME("SP"), 
	ODM_SOTME("ST"), 
	ODM_ENTME("ET"),
	ODM_PUBME("PM"),
	GENERATION_ODM("GODM"),
	GENERATION_AST("GAST"),
	SIGNATURE_ODM("SODM"),
	SIGNATURE_AST("SAST");
	
	private String odm;

	private EnumOdmEve(String code) {
		this.odm = code;
	}

	public String getCode() {
		return odm;
	}

	public static String[] allowedRoles() {

		return (String[]) Stream.of(EnumOdmEve.values()).map(e -> e.getCode()).collect(Collectors.toList()).toArray();
	}

}
