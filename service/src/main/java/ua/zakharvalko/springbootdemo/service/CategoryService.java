package ua.zakharvalko.springbootdemo.service;

import ua.zakharvalko.springbootdemo.domain.Category;

import java.util.List;

public interface CategoryService {

    Category addCategory(Category category);
    void deleteCategory(Integer id);
    Category getById(Integer id);
    Category editCategory(Category category);
    List<Category> getAllCategories();

}
