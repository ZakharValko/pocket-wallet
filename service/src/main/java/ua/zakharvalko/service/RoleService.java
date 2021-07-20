package ua.zakharvalko.service;

import ua.zakharvalko.domain.role.Role;

import java.util.List;

public interface RoleService {

    Role addRole(Role role);
    void deleteRole(Integer id);
    Role getById(Integer id);
    List<Role> getAllRoles();
}
