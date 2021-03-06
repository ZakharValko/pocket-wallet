package ua.zakharvalko.springbootdemo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ua.zakharvalko.springbootdemo.dao.AbstractRepository;
import ua.zakharvalko.springbootdemo.service.AbstractService;

import java.util.List;

public abstract class AbstractServiceImpl<T extends Object, R extends AbstractRepository<T>> implements AbstractService<T> {

    protected final R repository;

    @Autowired
    public AbstractServiceImpl(R repository){
        this.repository = repository;
    }

    @Override
    public T saveOrUpdate(T entity) {
        return repository.saveAndFlush(entity);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public T getById(Integer id) {
        return repository.getById(id);
    }

    @Override
    public List<T> getAll() {
        return repository.findAll();
    }
}
