package ua.zakharvalko.springbootdemo.service;

import ua.zakharvalko.springbootdemo.domain.User;

import java.util.List;

public interface UserService {

    User saveOrUpdate(User user);
    void delete(Integer id);
    User getById(Integer id);
    List<User> getAll();
}
