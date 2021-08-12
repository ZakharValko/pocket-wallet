package ua.zakharvalko.springbootdemo.dao;

import org.springframework.stereotype.Repository;
import ua.zakharvalko.springbootdemo.domain.Role;

@Repository
public interface RoleRepository extends AbstractRepository<Role> {
}
