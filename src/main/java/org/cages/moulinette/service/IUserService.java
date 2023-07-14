package org.cages.moulinette.service;

import java.util.List;

import org.cages.moulinette.dto.UserInfosDTO;
import org.cages.moulinette.model.Agent;
import org.cages.moulinette.model.UserInfos;

public interface IUserService {

	public UserInfosDTO findUserByMatricule(String identifiant);
	
	public UserInfos findUserById(Long idUser);

	public List<UserInfosDTO> loadAllUsersDTO(Long idUser);

	public UserInfos changerPassword(Long idUser, String newPassword);
	
	public void delete(Long idUser);

	public void disableEnableUser(UserInfos existingUser, int statut);

	public void createUser(Agent agentBeneficiare, String userPassword, List<String> selectedRoles);

	public int countActifsUsers();

	public int countInactifsUsers();

	public UserInfos findUserByIdentifiant(String identifiant);

	public void update(UserInfos existingUser, String updateUserPassword, UserInfosDTO userToUpdate);
	
	public UserInfosDTO findUserByEmail(String userEmail);

}

