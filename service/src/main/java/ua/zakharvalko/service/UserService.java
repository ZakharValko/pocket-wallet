package ua.zakharvalko.service;

import ua.zakharvalko.domain.user.User;

import java.util.List;

public interface UserService {

    User addUser(User user);
    void deleteUser(Integer id);
    User getById(Integer id);
    User editUser(User user);
    List<User> getAll();
}
