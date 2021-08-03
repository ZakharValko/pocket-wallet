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
import ua.zakharvalko.springbootdemo.domain.Operation;
import ua.zakharvalko.springbootdemo.service.OperationService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest({OperationRestController.class})
@ContextConfiguration(classes = SpringBootDemoApplication.class)
class OperationRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OperationService operationService;

    @Test
    void shouldAddOperation() throws Exception {
        Operation operation = Operation.builder().id(1).build();
        when(operationService.addOperation(operation)).thenReturn(operation);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/operations/")
                        .content(asJsonString(operation))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isCreated())
                .andExpect(content().json("{}"));;
    }

    @Test
    void shouldDeleteOperation() throws Exception {
        Operation operation = Operation.builder().id(1).build();
        when(operationService.getById(1)).thenReturn(operation);
        mockMvc.perform( MockMvcRequestBuilders.delete("/api/operations/{id}", 1) )
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));;
    }

    @Test
    void shouldEditOperation() throws Exception {
        Operation oldOperation = Operation.builder().id(1).description("Old description").build();
        Operation newOperation = Operation.builder().id(1).description("New description").build();
        when(operationService.editOperation(oldOperation)).thenReturn(newOperation);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/operations/")
                .content(asJsonString(newOperation))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));
    }

    @Test
    void shouldReturnOperationById() throws Exception {
        Operation operation = Operation.builder().id(1).build();
        when(operationService.getById(1)).thenReturn(operation);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/operations/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));;
    }

    @Test
    void shouldReturnAllOperations() throws Exception {
        List<Operation> operations = Arrays.asList(
                Operation.builder().id(1).build(),
                Operation.builder().id(2).build()
        );
        when(operationService.getAllOperations()).thenReturn(operations);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/operations/"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}, {}]"));;

    }

    @Test
    void shouldReturnOperationsByFilter() throws Exception {
        Operation operation = Operation.builder().build();
        List<Operation> operations = new ArrayList<>();
        operations.add(operation);
        when(operationService.getOperationByFilter(1, 1, 1, 1, 1, new Date(100), new Date(200))).thenReturn(operations);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/operations/get-operation-by-filter")
                .param("account", "1")
                .param("category", "1")
                .param("group", "1")
                .param("currency", "1")
                .param("operation-type","1")
                .param("from", "2021-01-01")
                .param("to", "2021-01-02")
        )
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));;
    }

    @Test
    void shouldReturnTotalExpensesByFilter() throws Exception {
        when(operationService.getTotalExpensesByFilter(1, 1, 1, 1, new Date(5000), new Date(6000))).thenReturn(500L);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/operations/get-total-by-filter")
                .param("account", "1")
                .param("category", "1")
                .param("group", "1")
                .param("currency", "1")
                .param("from", "2021-01-01")
                .param("to", "2021-01-02")
        )
                .andExpect(status().isOk())
                .andExpect(content().string("0.0"));;
    }

    @Test
    void shouldReturnCashFlow() throws Exception {
        when(operationService.getCashFlow(1, new Date(5000), new Date(6000))).thenReturn(500L);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/operations/get-cashflow")
                .param("account", "1")
                .param("from", "2021-01-01")
                .param("to", "2021-01-02")
        )
                .andExpect(status().isOk())
                .andExpect(content().string("0.0"));;
    }

    void shouldMakeTransferBetweenAccounts() throws Exception {

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}