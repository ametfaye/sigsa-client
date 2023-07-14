package sn.awi.pgca.business.service;

import sn.awi.pgca.business.exception.BoiteANumeroException;

public interface IBoiteANumero {

	public String getNumeroQuittance() throws BoiteANumeroException;
	
	public String getNumeroOrdre() throws BoiteANumeroException;
	
	public int getNumeroSequenceCodeProfil() throws BoiteANumeroException;
	
	public String genenerNumeroRccm(String codePays, String codeRegion, String codeFormalite)  throws BoiteANumeroException; 
	
	public String genenerNumeroInscription(String codePays, String codeRegion, String codeFormalite)  throws BoiteANumeroException;
	
	public String genenerNumeroFormalite(String codeFormalite, String numeroOrdre)  throws BoiteANumeroException; 
}
