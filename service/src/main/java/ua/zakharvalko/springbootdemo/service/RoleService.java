package ua.zakharvalko.springbootdemo.service;

import ua.zakharvalko.springbootdemo.domain.Role;

import java.util.List;

public interface RoleService {

    Role addRole(Role role);
    void deleteRole(Integer id);
    Role editRole(Role role);
    Role getById(Integer id);
    List<Role> getAllRoles();
}
