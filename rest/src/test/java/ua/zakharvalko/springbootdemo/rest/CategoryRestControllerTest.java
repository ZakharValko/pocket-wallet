package ua.zakharvalko.springbootdemo.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.zakharvalko.springbootdemo.SpringBootDemoApplication;
import ua.zakharvalko.springbootdemo.domain.Category;
import ua.zakharvalko.springbootdemo.service.CategoryService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest({CategoryRestController.class})
@ContextConfiguration(classes = SpringBootDemoApplication.class)
class CategoryRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Test
    void shouldAddCategory() throws Exception {
        Category category = Category.builder().id(1).build();
        categoryService.save(category);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/categories/")
                        .content(asJsonString(category))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isCreated())
                .andExpect(content().json("{}"));

        verify(categoryService).save(any(Category.class));
        verifyNoMoreInteractions(categoryService);

    }

    @Test
    void shouldDeleteCategory() throws Exception {
        Category category = Category.builder().id(1).build();
        when(categoryService.getById(1)).thenReturn(category);
        mockMvc.perform( MockMvcRequestBuilders.delete("/api/categories/{id}", 1) )
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));

        verify(categoryService).getById(1);
        verify(categoryService).delete(1);
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    void shouldEditCategory() throws Exception {
        Category oldCategory = Category.builder().id(1).title("Old title").build();
        Category newCategory = Category.builder().id(1).title("New title").build();
        categoryService.update(oldCategory);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/categories/")
                .content(asJsonString(newCategory))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));

        verify(categoryService).save(any(Category.class));
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    void shouldReturnCategoryById() throws Exception {
        Category category = Category.builder().id(1).build();
        when(categoryService.getById(1)).thenReturn(category);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/categories/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));

        verify(categoryService).getById(1);
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    void shouldReturnAllCategories() throws Exception {
        List<Category> categories = Arrays.asList(
                Category.builder().id(1).build(),
                Category.builder().id(2).build()
        );
        when(categoryService.getAll()).thenReturn(categories);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/categories/"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}, {}]"));

        verify(categoryService).getAll();
        verifyNoMoreInteractions(categoryService);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}