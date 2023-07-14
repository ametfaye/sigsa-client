package org.cages.moulinette.service;

import java.util.List;

import org.cages.moulinette.dto.RoleDTO;
import org.cages.moulinette.model.Role;

public interface IRoleService {
	
	public List<Role> getAllRoles();
	
	public List<RoleDTO> getAllRolesDtos();

	public void createRole(Role role);
	
	public void deleteRole(int id);

	public void updateRole(Role role);

	public Role getRoleById(int id);
	
	public Role getRoleByCode(String code);

}
