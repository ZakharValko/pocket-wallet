package ua.zakharvalko.springbootdemo.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import ua.zakharvalko.springbootdemo.domain.Category;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@MybatisTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    private Category category;

    @BeforeEach
    public void setUp() {
        Category first = Category.builder()
                .id(1)
                .title("First")
                .build();
        Category second = Category.builder()
                .id(2)
                .title("Second")
                .build();
        categoryRepository.save(first);
        categoryRepository.save(second);
    }

    @Test
    void shouldSaveCategory() {
        Category saved = Category.builder().id(3).build();
        categoryRepository.save(saved);
        Category fromDb = categoryRepository.getById(3);
        assertEquals(saved.getId(), fromDb.getId());
    }

    @Test
    void shouldGetCategoryById() {
        category = categoryRepository.getById(1);
        assertThat(categoryRepository.getById(category.getId())).isEqualTo(category);
    }

    @Test
    void shouldGetAllCategories() {
        List<Category> categories = categoryRepository.getAll();
        assertNotNull(categories);
        assertThat(categories).hasSize(2);
    }

    @Test
    void shouldEditCategory() {
        Category newCategory = Category.builder()
                .id(1)
                .title("New title")
                .build();
        categoryRepository.update(newCategory);
        Category editedCategory = categoryRepository.getById(1);
        assertEquals("New title", editedCategory.getTitle());
    }

    @Test
    void shouldDeleteCategory() {
        categoryRepository.delete(1);
        List<Category> categories = categoryRepository.getAll();
        assertThat(categories).hasSize(1);
    }
}