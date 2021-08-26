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
import ua.zakharvalko.springbootdemo.domain.GroupOfCategories;
import ua.zakharvalko.springbootdemo.service.GroupOfCategoryService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest({GroupCategoryRestController.class})
@ContextConfiguration(classes = SpringBootDemoApplication.class)
class GroupCategoryRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GroupOfCategoryService groupService;

    @Test
    void shouldAddGroup() throws Exception {
        GroupOfCategories group = GroupOfCategories.builder().id(1).title("Title").build();
        groupService.save(group);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/groups-of-categories/")
                        .content(asJsonString(group))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isCreated())
                .andExpect(content().json("{}, {}"));

        verify(groupService).save(any(GroupOfCategories.class));
        verifyNoMoreInteractions(groupService);
    }

    @Test
    void shouldDeleteGroup() throws Exception {
        GroupOfCategories group = GroupOfCategories.builder().id(1).build();
        when(groupService.getById(1)).thenReturn(group);
        mockMvc.perform( MockMvcRequestBuilders.delete("/api/groups-of-categories/{id}", 1) )
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));

        verify(groupService).getById(1);
        verify(groupService).delete(1);
        verifyNoMoreInteractions(groupService);
    }

    @Test
    void shouldEditGroup() throws Exception {
        GroupOfCategories oldGroup = GroupOfCategories.builder().id(1).title("Old").build();
        GroupOfCategories newGroup = GroupOfCategories.builder().id(1).title("New").build();
        groupService.update(oldGroup);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/groups-of-categories/")
                .content(asJsonString(newGroup))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isOk())
                .andExpect(content().json("{}, {}"));

        verify(groupService).update(any(GroupOfCategories.class));
        verifyNoMoreInteractions(groupService);
    }

    @Test
    void shouldReturnGroupById() throws Exception {
        GroupOfCategories group = GroupOfCategories.builder().id(1).build();
        when(groupService.getById(1)).thenReturn(group);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/groups-of-categories/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));

        verify(groupService).getById(1);
        verifyNoMoreInteractions(groupService);
    }

    @Test
    void shouldReturnAllGroups() throws Exception {
        List<GroupOfCategories> groups = Arrays.asList(
                GroupOfCategories.builder().id(1).build(),
                GroupOfCategories.builder().id(2).build()
        );
        when(groupService.getAll()).thenReturn(groups);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/groups-of-categories/"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}, {}]"));

        verify(groupService).getAll();
        verifyNoMoreInteractions(groupService);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}