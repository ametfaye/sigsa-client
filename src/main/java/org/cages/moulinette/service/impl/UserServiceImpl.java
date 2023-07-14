package org.cages.moulinette.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.cages.moulinette.dto.UserInfosDTO;
import org.cages.moulinette.model.Agent;
import org.cages.moulinette.model.PersonnePhysique;
import org.cages.moulinette.model.Role;
import org.cages.moulinette.model.UserInfos;
import org.cages.moulinette.repository.PersonnePhysiqueRepository;
import org.cages.moulinette.repository.RoleRepository;
import org.cages.moulinette.repository.UserRepository;
import org.cages.moulinette.service.IUserService;
import org.cages.moulinette.utils.CONSTANTES;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private PersonnePhysiqueRepository personnePhysiqueRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public UserInfosDTO findUserByMatricule(String identifiant) {

		UserInfos user = userRepository.findOneByIdentifiant(identifiant);
		
		if (user == null)
			return null;

		UserInfosDTO dtoUser = new UserInfosDTO();

		dtoUser.setIdUser(user.getId());
		dtoUser.setEmail(user.getAgent().getPersonnePhysique().getEmail());
		dtoUser.setIdentifiant(user.getIdentifiant());
		dtoUser.setPrenom(user.getAgent().getPersonnePhysique().getPrenom());
		dtoUser.setNom(user.getAgent().getPersonnePhysique().getNom());
		dtoUser.setSousService(user.getAgent().getSousService() != null ? user.getAgent().getSousService().getLibelle() : "Sous Service inconnu");
		dtoUser.setService(user.getAgent().getSousService() != null ? user.getAgent().getSousService().getService().getLibelle() : "Service inconnu");
		dtoUser.setEntitePublique(user.getAgent().getSousService().getService().getEntite().getLibelle());
		dtoUser.setIdSousService(user.getAgent().getSousService().getSsrvId());
		dtoUser.setIdService(user.getAgent().getSousService().getService().getSrvId());
		dtoUser.setTelephone(user.getAgent().getPersonnePhysique().getTelephone());
		
		return dtoUser;
	}
	
	@Override
	public UserInfos findUserByIdentifiant(String identifiant) {
		return userRepository.findOneByIdentifiant(identifiant);
	}

	@Override
	public UserInfos changerPassword(Long idUser, String newPassword) {

		UserInfos u = userRepository.findOne(idUser);
		if (u != null) {
			if (!bCryptPasswordEncoder.encode(newPassword).equals(u.getPassword()))
				u.setPassword(bCryptPasswordEncoder.encode(newPassword));
			userRepository.save(u);
		} else
			return null;
		return u;
	}

	@Override
	public List<UserInfosDTO> loadAllUsersDTO(Long idUser) {
		List<UserInfos> listeUser = userRepository.findAllNotDeleted(idUser);

		List<UserInfosDTO> listDTOS = new ArrayList<>();

		if (listeUser == null)
			return null;

		listeUser.stream().forEachOrdered(user -> {
			UserInfosDTO dtoUser = new UserInfosDTO();

			dtoUser.setIdUser(user.getId());
			dtoUser.setEmail(user.getAgent().getPersonnePhysique().getEmail());
			dtoUser.setIdentifiant(user.getIdentifiant());
			dtoUser.setPrenom(user.getAgent().getPersonnePhysique().getPrenom());
			dtoUser.setNom(user.getAgent().getPersonnePhysique().getNom());
			dtoUser.setActive(user.getActive());
			dtoUser.setStatut(user.getActive() == 1 ? "Actif" : "Inactif");
			dtoUser.setClassCss(user.getActive() == 1 ? CONSTANTES.badgeSUCESS : CONSTANTES.badgeDANGER);
			dtoUser.setEntitePublique(user.getAgent().getSousService().getService().getEntite().getLibelle());
			dtoUser.setService(user.getAgent().getSousService().getLibelle());
			Set<Role> roles = user.getRoles();
			List<String> r = roles.stream().map(Role::getLibelle).collect(Collectors.toList());
			dtoUser.setFonction(String.join(", ", r));
			listDTOS.add(dtoUser);
		});

		return listDTOS;
	}

	@Override
	public void createUser(Agent agentBeneficiare, String userPassword, List<String> selectedRoles) {
		List<Role> roles = new ArrayList<>();
		selectedRoles.stream().forEach(role -> {
			roles.add(roleRepository.findByRole(role));	
		});
		UserInfos user = new UserInfos();
		user.setIdentifiant(agentBeneficiare.getMatricule());
		user.setPassword(bCryptPasswordEncoder.encode(userPassword));
		user.setActive(1);
		user.setDeleted(0);
		user.setRoles(new HashSet<Role>(roles));
		user.setDateCreation(new Date());
		user.setDateModification(new Date());
		user.setAgent(agentBeneficiare);
		userRepository.save(user);
	}

	@Override
	public void delete(Long idUser) {
		UserInfos userInfos = userRepository.findOne(idUser);
		if (userInfos != null) {
			userInfos.setDeleted(1);
			userInfos.setDateSuppression(new Date());
			userRepository.save(userInfos);
		}
	}

	@Override
	public UserInfos findUserById(Long idUser) {
		return userRepository.findOne(idUser);
	}

	@Override
	public void disableEnableUser(UserInfos existingUser, int statut) {
		existingUser.setActive(statut);
		userRepository.save(existingUser);
	}

	@Override
	public void update(UserInfos existingUser, String password, UserInfosDTO userToUpdate) {
		List<Role> roles = new ArrayList<>();
		userToUpdate.getRoles().stream().forEach(roleToUpdate -> {
			if (roleToUpdate.isSelected()) {
				roles.add(new Role(roleToUpdate.getId(), roleToUpdate.getCode(), roleToUpdate.getLibelle(), roleToUpdate.getDescription()));	
			}
		});
		existingUser.setPassword(bCryptPasswordEncoder.encode(password));
		PersonnePhysique personnePhysique = existingUser.getAgent().getPersonnePhysique();
		personnePhysique.setTelephone(userToUpdate.getTelephone());
		personnePhysique.setEmail(userToUpdate.getEmail());
		personnePhysiqueRepository.save(personnePhysique);
		existingUser.setRoles(new HashSet<Role>(roles));
		userRepository.save(existingUser);
	}

	@Override
	public int countActifsUsers() {
		return userRepository.countActifsUsers();
	}

	@Override
	public int countInactifsUsers() {
		return userRepository.countInactifsUsers();
	}

	@Override
	public UserInfosDTO findUserByEmail(String userEmail) {
	UserInfos user = userRepository.findOneByEmail(userEmail);
		
		if (user == null)
			return null;

		return MapUserInfosToDto(user);
	}

	
	private UserInfosDTO MapUserInfosToDto(UserInfos user) {
		UserInfosDTO dtoUser = new UserInfosDTO();

		dtoUser.setIdUser(user.getId());
		dtoUser.setEmail(user.getAgent().getPersonnePhysique().getEmail());
		dtoUser.setIdentifiant(user.getIdentifiant());
		dtoUser.setPrenom(user.getAgent().getPersonnePhysique().getPrenom());
		dtoUser.setNom(user.getAgent().getPersonnePhysique().getNom());

		/*if (null != user.getAgent().getSousService()) {
			dtoUser.setIdSousService(user.getAgent().getSousService().getSsrvId());
			dtoUser.setSousService(
					user.getAgent().getSousService() != null ? user.getAgent().getSousService().getLibelle()
							: "Sous Service inconnu");
			dtoUser.setIdService(user.getAgent().getSousService().getService().getSrvId());
			dtoUser.setService(user.getAgent().getSousService().getService() != null
					? user.getAgent().getSousService().getService().getLibelle()
					: "Service inconnu");
			dtoUser.setEntitePublique(user.getAgent().getSousService().getService().getEntite().getLibelle());
			dtoUser.setEntitePubliqueCode(user.getAgent().getSousService().getService().getEntite().getCode());
		}
		if (user.getAgent().getService() != null) {
			dtoUser.setEntitePublique(user.getAgent().getService().getEntite().getLibelle());
			dtoUser.setEntitePubliqueCode(user.getAgent().getService().getEntite().getCode());
			dtoUser.setIdService(user.getAgent().getService().getSrvId());
			dtoUser.setService(user.getAgent().getService() != null ? user.getAgent().getService().getLibelle()
					: "Service inconnu");
			
			dtoUser.setServiceCode(user.getAgent().getService() != null ? user.getAgent().getService().getCode()
					: "Service inconnu");
		}
		dtoUser.setMdpPersonnalise(user.getMdpPersonnalise());
		dtoUser.setTelephone(user.getAgent().getPersonnePhysique().getTelephone());*/
		return dtoUser;
	}
}
