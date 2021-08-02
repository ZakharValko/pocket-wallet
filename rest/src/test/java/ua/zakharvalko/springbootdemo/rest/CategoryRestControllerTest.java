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

import static org.mockito.Mockito.when;
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
        when(categoryService.addCategory(category)).thenReturn(category);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/categories/")
                        .content(asJsonString(category))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isCreated())
                .andExpect(content().json("{}"));;

    }

    @Test
    void shouldDeleteCategory() throws Exception {
        Category category = Category.builder().id(1).build();
        when(categoryService.getById(1)).thenReturn(category);
        mockMvc.perform( MockMvcRequestBuilders.delete("/api/categories/{id}", 1) )
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));;
    }

    @Test
    void shouldEditCategory() throws Exception {
        Category oldCategory = Category.builder().id(1).title("Old title").build();
        Category newCategory = Category.builder().id(1).title("New title").build();
        when(categoryService.editCategory(oldCategory)).thenReturn(newCategory);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/categories/")
                .content(asJsonString(newCategory))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));
    }

    @Test
    void shouldReturnCategoryById() throws Exception {
        Category category = Category.builder().id(1).build();
        when(categoryService.getById(1)).thenReturn(category);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/categories/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));;
    }

    @Test
    void shouldReturnAllCategories() throws Exception {
        List<Category> categories = Arrays.asList(
                Category.builder().id(1).build(),
                Category.builder().id(2).build()
        );
        when(categoryService.getAllCategories()).thenReturn(categories);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/categories/"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}, {}]"));;
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}