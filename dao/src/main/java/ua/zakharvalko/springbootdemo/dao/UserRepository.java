package ua.zakharvalko.springbootdemo.dao;

import org.springframework.stereotype.Repository;
import ua.zakharvalko.springbootdemo.domain.User;

@Repository
public interface UserRepository extends AbstractRepository<User> {
    User findByUsername(String username);
}
