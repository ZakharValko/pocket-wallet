package ua.zakharvalko.springbootdemo.service;

import java.util.List;

public interface AbstractService<T> {

    void save(T entity);
    void update(T entity);
    void delete(Integer id);
    T getById(Integer id);
    List<T> getAll();

}
