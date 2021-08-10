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
import ua.zakharvalko.springbootdemo.domain.Account;
import ua.zakharvalko.springbootdemo.domain.Category;
import ua.zakharvalko.springbootdemo.domain.Operation;
import ua.zakharvalko.springbootdemo.domain.OperationType;
import ua.zakharvalko.springbootdemo.domain.specification.OperationFilter;
import ua.zakharvalko.springbootdemo.service.OperationService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

        Operation operation = Operation.builder()
                .id(1)
                .description("Test operation")
                .date(new Date())
                .price(1000L)
                .operationType(OperationType.builder().id(1).build())
                .category(Category.builder().id(1).build())
                .account(Account.builder().id(1).build())
                .build();

        when(operationService.saveOrUpdate(any(Operation.class))).thenReturn(operation);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/operations/")
                        .content(asJsonString(operation))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andExpect(content().json("{}"));

        verify(operationService).saveOrUpdate(any(Operation.class));
        verifyNoMoreInteractions(operationService);
    }

    @Test
    void shouldDeleteOperation() throws Exception {
        Operation operation = Operation.builder().id(1).build();

        when(operationService.getById(1)).thenReturn(operation);

        mockMvc.perform( MockMvcRequestBuilders.delete("/api/operations/{id}", 1) )
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));

        verify(operationService).getById(1);
        verify(operationService).delete(1);
        verifyNoMoreInteractions(operationService);
    }

    @Test
    void shouldEditOperation() throws Exception {
        Operation oldOperation = Operation.builder().id(1).description("Old description").build();
        Operation newOperation = Operation.builder().id(1).description("New description").build();

        when(operationService.saveOrUpdate(oldOperation)).thenReturn(newOperation);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/operations/")
                .content(asJsonString(newOperation))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));

        verify(operationService).saveOrUpdate(any());
        verifyNoMoreInteractions(operationService);
    }

    @Test
    void shouldReturnOperationById() throws Exception {
        Operation operation = Operation.builder().id(1).build();

        when(operationService.getById(1)).thenReturn(operation);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/operations/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));

        verify(operationService).getById(1);
        verifyNoMoreInteractions(operationService);
    }

    @Test
    void shouldReturnAllOperations() throws Exception {
        List<Operation> operations = Arrays.asList(
                Operation.builder().id(1).build(),
                Operation.builder().id(2).build()
        );

        when(operationService.getAll()).thenReturn(operations);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/operations/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));

        verify(operationService).getAll();
        verifyNoMoreInteractions(operationService);

    }

    @Test
    void shouldReturnOperationsByFilter() throws Exception {
        List<Operation> operationsList = new ArrayList<>();
        operationsList.add(
                Operation.builder()
                        .id(1)
                        .description("Test operation")
                        .date(new Date())
                        .price(1000L)
                        .operationType(OperationType.builder().id(1).build())
                        .category(Category.builder().id(1).build())
                        .account(Account.builder().id(1).build())
                        .build());
        operationsList.add(
                Operation.builder()
                        .id(2)
                        .description("Test operation 2")
                        .date(new Date())
                        .price(1500L)
                        .operationType(OperationType.builder().id(2).build())
                        .category(Category.builder().id(1).build())
                        .account(Account.builder().id(1).build())
                        .build());

        OperationFilter filter = new OperationFilter();
        filter.setAccount(Account.builder().id(1).build());

        when(operationService.getOperationsByFilter(any())).thenReturn(operationsList);

        mockMvc.perform(post("/api/operations/get-operation-by-filter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(filter)))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}, {}]"));

        verify(operationService).getOperationsByFilter(any());
        verifyNoMoreInteractions(operationService);
    }

    @Test
    void shouldReturnTotalExpensesByFilter() throws Exception {
        OperationFilter filter = new OperationFilter();
        filter.setAccount(Account.builder().id(1).build());

        when(operationService.getTotalExpensesByFilter(any())).thenReturn(100.35);
        mockMvc.perform(post("/api/operations/get-total-by-filter")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(filter)))
                .andExpect(status().isOk())
                .andExpect(content().string("100.35"));

        verify(operationService).getTotalExpensesByFilter(any());
        verifyNoMoreInteractions(operationService);
    }

    @Test
    void shouldMakeTransferBetweenAccounts() throws Exception {
        List<Operation> operations = new ArrayList<>();
        Operation operation = Operation.builder()
                .id(1)
                .account(Account.builder().id(1).build())
                .operationType(OperationType.builder().id(1).build())
                .transferTo(Account.builder().id(2).build())
                .totalForTransfer(1000L)
                .build();

        Operation back = Operation.builder()
                .description("Transfer from: " + operation.getAccount().getId())
                .date(new Date())
                .price(operation.getTotalForTransfer())
                .account(operation.getTransferTo())
                .operationType(OperationType.builder().id(2).build())
                .build();

        operations.add(operation);
        operations.add(back);

        when(operationService.transferBetweenAccounts(any())).thenReturn(operations);

        mockMvc.perform(post("/api/operations/transfer-btw-accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(operation)))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}, {}]"));

        verify(operationService).transferBetweenAccounts(any());
        verifyNoMoreInteractions(operationService);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}