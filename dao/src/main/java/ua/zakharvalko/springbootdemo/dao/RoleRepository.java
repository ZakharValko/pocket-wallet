package ua.zakharvalko.springbootdemo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.zakharvalko.springbootdemo.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
