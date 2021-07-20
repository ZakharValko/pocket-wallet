package ua.zakharvalko.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.zakharvalko.domain.role.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
