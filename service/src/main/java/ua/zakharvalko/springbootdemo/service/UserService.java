package ua.zakharvalko.springbootdemo.service;

import ua.zakharvalko.springbootdemo.domain.User;

import java.util.List;

public interface UserService {

    User addUser(User user);
    void deleteUser(Integer id);
    User getById(Integer id);
    User editUser(User user);
    List<User> getAll();
}
