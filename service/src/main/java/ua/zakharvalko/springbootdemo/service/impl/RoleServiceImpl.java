package ua.zakharvalko.springbootdemo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.zakharvalko.springbootdemo.dao.RoleRepository;
import ua.zakharvalko.springbootdemo.domain.Role;
import ua.zakharvalko.springbootdemo.service.RoleService;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role saveOrUpdate(Role role) {
        return roleRepository.saveAndFlush(role);
    }

    @Override
    public void delete(Integer id) {
        roleRepository.deleteById(id);
    }

    @Override
    public Role getById(Integer id) {
        return roleRepository.getById(id);
    }

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }
}
