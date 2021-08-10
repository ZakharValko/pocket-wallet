package ua.zakharvalko.springbootdemo.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.zakharvalko.springbootdemo.service.AbstractCrudService;

import java.util.List;

public abstract class AbstractServiceImpl<T> implements AbstractCrudService<T> {

    private JpaRepository<T, Integer> repository;

    abstract JpaRepository<T, Integer> getRepository();

    @Override
    public T saveOrUpdate(T entity) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public T getById(Integer id) {
        return null;
    }

    @Override
    public List<T> getAll() {
        return null;
    }
}
