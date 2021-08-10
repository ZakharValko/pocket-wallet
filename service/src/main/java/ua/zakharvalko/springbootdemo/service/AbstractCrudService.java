package ua.zakharvalko.springbootdemo.service;

import java.util.List;

public interface AbstractCrudService<T> {

    T saveOrUpdate(T entity);
    void delete(Integer id);
    T getById(Integer id);
    List<T> getAll();
}
