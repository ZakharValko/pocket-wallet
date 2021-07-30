package ua.zakharvalko.springbootdemo.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.zakharvalko.springbootdemo.domain.User;
import ua.zakharvalko.springbootdemo.service.UserService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest({UserRestController.class})
class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void shouldAddUser() throws Exception {
        User user = User.builder().id(1).build();
        when(userService.addUser(user)).thenReturn(user);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/users/")
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isCreated())
                .andExpect(content().json("{}"));;
    }

    @Test
    void shouldReturnUserById() throws Exception {
        User user = User.builder().id(1).build();
        when(userService.getById(1)).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));;
    }

    @Test
    void shouldEditUser() throws Exception {
        User oldUser = User.builder().id(1).firstName("Alex").build();
        User newUser = User.builder().id(1).firstName("John").build();
        when(userService.editUser(oldUser)).thenReturn(newUser);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/")
                .content(asJsonString(newUser))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isOk())
                .andExpect(content().json("{}, {}"));;
    }

    @Test
    void shouldDeleteUser() throws Exception {
        User user = User.builder().id(1).build();
        when(userService.getById(1)).thenReturn(user);
        mockMvc.perform( MockMvcRequestBuilders.delete("/api/users/{id}", 1) )
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));;
    }

    @Test
    void shouldReturnAllUsers() throws Exception {
        List<User> users = Arrays.asList(
                User.builder().id(1).build(),
                User.builder().id(2).build()
        );
        when(userService.getAll()).thenReturn(users);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/"))
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