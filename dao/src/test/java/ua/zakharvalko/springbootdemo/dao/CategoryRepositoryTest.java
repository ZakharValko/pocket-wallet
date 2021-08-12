package ua.zakharvalko.springbootdemo.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import ua.zakharvalko.springbootdemo.domain.Category;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    private Category category;

    @BeforeEach
    public void setUp() {
        List<Category> categories = new ArrayList<>();
        Category first = Category.builder()
                .id(1)
                .title("First")
                .build();
        Category second = Category.builder()
                .id(2)
                .title("Second")
                .build();
        categories.add(first);
        categories.add(second);

        categoryRepository.saveAll(categories);
    }

    @Test
    void shouldSaveCategory() {
        Category saved = categoryRepository.saveAndFlush(Category.builder().id(3).build());
        Category fromDb = categoryRepository.getById(3);
        assertEquals(saved, fromDb);
    }

    @Test
    void shouldGetCategoryById() {
        category = categoryRepository.getById(1);
        assertThat(categoryRepository.getById(category.getId())).isEqualTo(category);
    }

    @Test
    void shouldGetAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        assertNotNull(categories);
        assertThat(categories).hasSize(2);
    }

    @Test
    void shouldEditCategory() {
        Category newCategory = Category.builder()
                .id(1)
                .title("New title")
                .build();
        categoryRepository.saveAndFlush(newCategory);
        Category editedCategory = categoryRepository.getById(1);
        assertEquals("New title", editedCategory.getTitle());
    }

    @Test
    void shouldDeleteCategory() {
        categoryRepository.deleteById(1);
        List<Category> categories = categoryRepository.findAll();
        assertThat(categories).hasSize(1);
    }
}