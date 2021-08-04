package ua.zakharvalko.springbootdemo.service;

import ua.zakharvalko.springbootdemo.domain.Category;

import java.util.List;

public interface CategoryService {

    Category saveOrUpdate(Category category);
    void delete(Integer id);
    Category getById(Integer id);
    List<Category> getAll();

}
