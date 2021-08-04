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
import ua.zakharvalko.springbootdemo.domain.Role;
import ua.zakharvalko.springbootdemo.service.RoleService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest({RoleRestController.class})
@ContextConfiguration(classes = SpringBootDemoApplication.class)
class RoleRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleService roleService;

    @Test
    void shouldAddRole() throws Exception {
        Role role = Role.builder().id(1).build();
        when(roleService.saveOrUpdate(role)).thenReturn(role);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/roles/")
                        .content(asJsonString(role))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isCreated())
                .andExpect(content().json("{}"));
    }

    @Test
    void shouldDeleteRole() throws Exception {
        Role role = Role.builder().id(1).build();
        when(roleService.getById(1)).thenReturn(role);
        mockMvc.perform( MockMvcRequestBuilders.delete("/api/roles/{id}", 1) )
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));
    }

    @Test
    void shouldEditRole() throws Exception {
        Role oldRole = Role.builder().id(1).title("Old").build();
        Role newRole= Role.builder().id(1).title("New").build();
        when(roleService.saveOrUpdate(oldRole)).thenReturn(newRole);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/roles/")
                .content(asJsonString(newRole))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isOk())
                .andExpect(content().json("{}, {}"));
    }

    @Test
    void shouldReturnRoleById() throws Exception {
        Role role = Role.builder().id(1).build();
        when(roleService.getById(1)).thenReturn(role);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/roles/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));
    }

    @Test
    void shouldReturnAllRoles() throws Exception {
        List<Role> roles = Arrays.asList(
                Role.builder().id(1).build(),
                Role.builder().id(2).build()
        );
        when(roleService.getAll()).thenReturn(roles);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/roles/"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}, {}]"));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}