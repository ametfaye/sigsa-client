package org.cages.moulinette.managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.cages.moulinette.dto.RoleDTO;
import org.cages.moulinette.dto.UserInfosDTO;
import org.cages.moulinette.model.Role;
import org.cages.moulinette.service.IRoleService;
import org.cages.moulinette.service.IUserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;

@ManagedBean(name = "RoleController")
@SessionScoped
@Getter
@Setter
public class RoleController {
	
	private String roleCodeUpdate;
	private String roleDescriptionUpdate;
	private String roleLibelleUpdate;
	
	private String createRoleErreurMsg;
	private String createRoleSuccesMsg;

	private UserInfosDTO connectedUserDetails = new UserInfosDTO();
	private RoleDTO newRoleDto = new RoleDTO();
	private RoleDTO updateRoleDto = new RoleDTO();
	List<Role> roles = new ArrayList<>();

	@ManagedProperty(value = "#{userService}")
	private IUserService userService;

	@ManagedProperty(value = "#{roleService}")
	private IRoleService roleService;

	@PostConstruct
	private void init() {
		initRoleFields();
		resetErrorMsgs();
		connectedUserDetails = retrievedetailsConnectedUser();
		loadRolesList();
	}

	public UserInfosDTO retrievedetailsConnectedUser() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userService.findUserByMatricule(user.getUsername());
	}

	public void loadRolesList() {
		roles = roleService.getAllRoles();
	}

	public String deleteRole(int roleId) {
		Role role = roleService.getRoleById(roleId);
		if (role != null) {
			roleService.deleteRole(role.getId());	
			setCreateRoleSuccesMsg("Le rôle "+role.getLibelle()+" est supprimé avec succès");
		}
		loadRolesList();
		return "adminRoleList.xhtml?faces-redirect=true";
	}
	
	public String updateRole(int roleId) {
		Role role = roleService.getRoleById(roleId);
		if (role != null) {
			role.setRole(updateRoleDto.getCode());
			role.setLibelle(updateRoleDto.getLibelle());
			role.setDescription(updateRoleDto.getDescription());
			roleService.updateRole(role);
			setCreateRoleSuccesMsg("Le rôle "+role.getLibelle()+" est modifié avec succès");
		}
		loadRolesList();
		updateRoleDto = new RoleDTO();
		return "adminRoleList.xhtml?faces-redirect=true";
	}

	public String createRole() {
		setCreateRoleErreurMsg(null);
		if ("".equals(newRoleDto.getCode())) {
			setCreateRoleErreurMsg("Le code est obligatoire");
			return "adminRoleEdition.xhtml?faces-redirect=true";
		}
		if ("".equals(newRoleDto.getLibelle())) {
			setCreateRoleErreurMsg("Le libellé est obligatoire");
			return "adminRoleEdition.xhtml?faces-redirect=true";
		}
		if ("".equals(newRoleDto.getDescription())) {
			setCreateRoleErreurMsg("La description est obligatoire");
			return "adminRoleEdition.xhtml?faces-redirect=true";
		}
		Role role = roleService.getRoleByCode(newRoleDto.getCode());
		if (role != null) {
			setCreateRoleErreurMsg("Le rôle "+newRoleDto.getCode()+" existe déjà !");
			return "adminRoleEdition.xhtml?faces-redirect=true";
		} else {
			roleService.createRole(new Role(newRoleDto.getCode(), newRoleDto.getLibelle(), newRoleDto.getDescription()));
			setCreateRoleSuccesMsg("Le rôle "+newRoleDto.getLibelle()+" a été crée avec succès");
		}
		loadRolesList();
		newRoleDto = new RoleDTO();
		return "adminRoleList.xhtml?faces-redirect=true";
	}
	
	private void initRoleFields() {
		newRoleDto = new RoleDTO();
		updateRoleDto = new RoleDTO();
	}

	public void resetErrorMsgs() {
		setCreateRoleErreurMsg(null);
		setCreateRoleSuccesMsg(null);
	}

}
