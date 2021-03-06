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
import ua.zakharvalko.springbootdemo.domain.OperationType;
import ua.zakharvalko.springbootdemo.service.OperationTypeService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest({OperationTypeRestController.class})
@ContextConfiguration(classes = SpringBootDemoApplication.class)
class OperationTypeRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OperationTypeService typeService;

    @Test
    void shouldAddType() throws Exception {
        OperationType type = OperationType.builder().id(1).build();
        when(typeService.saveOrUpdate(type)).thenReturn(type);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/operation-types/")
                        .content(asJsonString(type))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isCreated())
                .andExpect(content().json("{}"));

        verify(typeService).saveOrUpdate(any(OperationType.class));
        verifyNoMoreInteractions(typeService);
    }

    @Test
    void shouldDeleteType() throws Exception {
        OperationType type = OperationType.builder().id(1).build();
        when(typeService.getById(1)).thenReturn(type);
        mockMvc.perform( MockMvcRequestBuilders.delete("/api/operation-types/{id}", 1) )
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));

        verify(typeService).getById(1);
        verify(typeService).delete(1);
        verifyNoMoreInteractions(typeService);
    }

    @Test
    void shouldEditType() throws Exception {
        OperationType oldType = OperationType.builder().id(1).title("Old").build();
        OperationType newType = OperationType.builder().id(1).title("New").build();
        when(typeService.saveOrUpdate(oldType)).thenReturn(newType);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/operation-types/")
                .content(asJsonString(newType))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isOk())
                .andExpect(content().json("{}, {}"));

        verify(typeService).saveOrUpdate(any(OperationType.class));
        verifyNoMoreInteractions(typeService);
    }

    @Test
    void shouldReturnTypeById() throws Exception {
        OperationType type = OperationType.builder().id(1).build();
        when(typeService.getById(1)).thenReturn(type);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/operation-types/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));

        verify(typeService).getById(1);
        verifyNoMoreInteractions(typeService);
    }

    @Test
    void shouldReturnAllTypes() throws Exception {
        List<OperationType> types = Arrays.asList(
                OperationType.builder().id(1).build(),
                OperationType.builder().id(2).build()
        );
        when(typeService.getAll()).thenReturn(types);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/operation-types/"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}, {}]"));

        verify(typeService).getAll();
        verifyNoMoreInteractions(typeService);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}