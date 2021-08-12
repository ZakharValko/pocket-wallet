package ua.zakharvalko.springbootdemo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.zakharvalko.springbootdemo.dao.RoleRepository;
import ua.zakharvalko.springbootdemo.domain.Role;
import ua.zakharvalko.springbootdemo.service.RoleService;

@Service
public class RoleServiceImpl extends AbstractServiceImpl<Role, RoleRepository> implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository repository) {
        super(repository);
    }
}
