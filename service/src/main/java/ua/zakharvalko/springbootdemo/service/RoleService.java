package ua.zakharvalko.springbootdemo.service;

import ua.zakharvalko.springbootdemo.domain.Role;

import java.util.List;

public interface RoleService {

    Role saveOrUpdate(Role role);
    void delete(Integer id);
    Role getById(Integer id);
    List<Role> getAll();
}