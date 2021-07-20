package ua.zakharvalko.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.zakharvalko.domain.user.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
