package ua.zakharvalko.springbootdemo.service;

import ua.zakharvalko.springbootdemo.domain.Category;

import java.util.List;

public interface CategoryService {

    Category addCategory(Category category);
    void deleteCategory(Integer id);
    Category editCategory(Category category);
    Category getById(Integer id);
    List<Category> getAllCategories();

}
