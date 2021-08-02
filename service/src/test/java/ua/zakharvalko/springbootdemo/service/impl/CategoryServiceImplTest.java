package ua.zakharvalko.springbootdemo.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ua.zakharvalko.springbootdemo.dao.CategoryRepository;
import ua.zakharvalko.springbootdemo.domain.Category;
import ua.zakharvalko.springbootdemo.service.CategoryService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = CategoryServiceImpl.class)
@RunWith(SpringRunner.class)
class CategoryServiceImplTest {

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @Test
    public void shouldReturnCategoryWhenSaved() {
        Category category = Category.builder().id(1).build();

        when(categoryRepository.saveAndFlush(category)).thenReturn(category);

        Category added = categoryService.addCategory(category);

        assertEquals(category.getId(), added.getId());
        verify(categoryRepository).saveAndFlush(category);
    }

    @Test
    public void shouldDeleteCategory() {
        Category category = Category.builder().id(1).build();

        when(categoryRepository.getById(category.getId())).thenReturn(category);
        categoryService.deleteCategory(category.getId());
        verify(categoryRepository).deleteById(category.getId());
    }

    @Test
    public void shouldEditCategory() {
        Category oldCategory = Category.builder().id(1).build();
        oldCategory.setTitle("Old");
        Category newCategory = Category.builder().id(1).build();
        newCategory.setTitle("New");

        given(categoryRepository.getById(oldCategory.getId())).willReturn(oldCategory);
        categoryService.editCategory(newCategory);

        verify(categoryRepository).saveAndFlush(newCategory);
    }

    @Test
    public void shouldReturnCategoryById() {
        Category category = Category.builder().id(1).build();
        when(categoryRepository.getById(1)).thenReturn(category);

        Category actual = categoryService.getById(1);

        assertEquals(category.getId(), actual.getId());
        verify(categoryRepository).getById(1);
    }

    @Test
    public void shouldReturnAllCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category());
        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> actual = categoryService.getAllCategories();

        assertEquals(categories, actual);
        verify(categoryRepository).findAll();
    }
}