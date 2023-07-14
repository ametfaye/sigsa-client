package org.cages.moulinette.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.cages.moulinette.dto.RoleDTO;
import org.cages.moulinette.model.Role;
import org.cages.moulinette.repository.RoleRepository;
import org.cages.moulinette.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("roleService")
public class RoleServiceImpl implements IRoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}

	@Override
	public List<RoleDTO> getAllRolesDtos() {
		List<RoleDTO> roleDTOs = new ArrayList<>();
		List<Role> roles = getAllRoles();
		if (roles != null && !roles.isEmpty()) {
			roles.forEach(role -> {
				roleDTOs.add(new RoleDTO(role.getId(), role.getRole(), role.getLibelle(), role.getDescription()));
			});
		}
		return roleDTOs;
	}

	@Override
	public void createRole(Role role) {
		roleRepository.save(role);
	}

	@Override
	public void deleteRole(int id) {
		roleRepository.delete(id);
	}

	@Override
	public void updateRole(Role role) {
		roleRepository.save(role);
	}

	@Override
	public Role getRoleById(int id) {
		return roleRepository.findOne(id);
	}

	@Override
	public Role getRoleByCode(String code) {
		return roleRepository.findByRole(code);
	}

}
