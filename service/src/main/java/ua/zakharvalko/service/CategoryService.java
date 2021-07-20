package ua.zakharvalko.service;

import ua.zakharvalko.domain.category.Category;

import java.util.List;

public interface CategoryService {

    Category addCategory(Category category);
    void deleteCategory(Integer id);
    Category getById(Integer id);
    Category editCategory(Category category);
    List<Category> getAllCategories();

}
