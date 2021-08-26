package ua.zakharvalko.springbootdemo.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AbstractRepository<T> {

    void save(T entity);
    void update(T entity);
    void delete(Integer id);
    T getById(Integer id);
    List<T> getAll();

}
