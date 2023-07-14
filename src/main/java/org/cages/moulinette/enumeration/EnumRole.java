package org.cages.moulinette.enumeration;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum EnumRole {
	
	ADMIN("ADMIN"),
	EDIODM("EDIODM"),
	UPODM("UPODM"),
	ADPART("ADPART"),
	TRODM("TRODM"),
	ERAODM("ERAODM"),
	GFODM("GFODM"),
	VRCHS("VRCHS"),
	ARSG("ARSG"),
	ARSGG("ARSGG"),
	VRSGG("VRSGG"),
	GENODM("GENODM"),
	GENAST("GENAST"),
	SVDOC("SVDOC"),
	PUBODM("PUBODM"),
	EASTMAIL("EASTMAIL"),
	EODMMAIL("EODMMAIL"), 
	TBADM ("TBADM"),
	TBCHS ("TBCHS"),
	TBGS ("TBGS"),
	TBSGG ("TBSGG"),
	TBSG ("TBSG"),
	SIGNAST ("SIGNAST"),
	SIGNODM ("SIGNODM");

	private String role;

	private EnumRole(String code) {
		this.role = code;
	}

	public String getCode() {
		return role;
	}

	public static String[] allowedRoles() {

		return (String[]) Stream.of(EnumRole.values()).map(e -> e.getCode()).collect(Collectors.toList()).toArray();
	}
}
