package ua.zakharvalko.springbootdemo.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.zakharvalko.springbootdemo.SpringBootDemoApplication;
import ua.zakharvalko.springbootdemo.dao.UserRepository;
import ua.zakharvalko.springbootdemo.domain.User;
import ua.zakharvalko.springbootdemo.service.UserService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest({UserRestController.class})
@ContextConfiguration(classes = SpringBootDemoApplication.class)
@WithMockUser(username = "alexs", password = "123", authorities = "Admin")
class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void shouldAddUser() throws Exception {
        User user = User.builder().id(1).build();
        userService.save(user);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/users/")
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isCreated())
                .andExpect(content().json("{}"));

        verify(userService, times(2)).save(any(User.class));
        verifyNoMoreInteractions(userService);
    }

    @Test
    void shouldReturnUserById() throws Exception {
        User user = User.builder().id(1).build();
        when(userService.getById(1)).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));

        verify(userService).getById(1);
        verifyNoMoreInteractions(userService);
    }

    @Test
    void shouldEditUser() throws Exception {
        User oldUser = User.builder().id(1).first_name("Alex").build();
        User newUser = User.builder().id(1).first_name("John").build();
        userService.update(oldUser);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/")
                .content(asJsonString(newUser))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isOk())
                .andExpect(content().json("{}, {}"));

        verify(userService, times(2)).update(any(User.class));
        verifyNoMoreInteractions(userService);
    }

    @Test
    void shouldDeleteUser() throws Exception {
        User user = User.builder().id(1).build();
        when(userService.getById(1)).thenReturn(user);
        mockMvc.perform( MockMvcRequestBuilders.delete("/api/users/{id}", 1) )
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));

        verify(userService).getById(1);
        verify(userService).delete(1);
        verifyNoMoreInteractions(userService);
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
                .andExpect(content().json("[{}, {}]"));

        verify(userService).getAll();
        verifyNoMoreInteractions(userService);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}