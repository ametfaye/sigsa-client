package sn.awi.pgca.business.service;

import sn.awi.pgca.business.exception.ConnectionException;
import sn.awi.pgca.business.exception.EntityDBDAOException;
import sn.awi.pgca.dataModel.Utilisateur;
import sn.awi.pgca.web.dto.ConnectionDTO;
import sn.awi.pgca.web.dto.ThemeDTO;


public interface IConnectionService {
    
    public boolean validate(ConnectionDTO connectionDTO) throws ConnectionException;
    public void validateAdmin(ConnectionDTO connectionDTO) throws ConnectionException;
    
	
	public Utilisateur getUserByCode(String codeUtilisateur)throws ConnectionException;
	
	
	public String getUserType(String codeProfil)throws ConnectionException;
	public ThemeDTO changethemene(Long idUser, ThemeDTO theme)throws ConnectionException, EntityDBDAOException;

	
    
	public void seDeconnecter() throws ConnectionException;
	
	public void updatePassword(ConnectionDTO connectionDTO)throws ConnectionException;
}
