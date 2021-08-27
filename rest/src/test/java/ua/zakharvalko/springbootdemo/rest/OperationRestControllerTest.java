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
import ua.zakharvalko.springbootdemo.dao.AccountRepository;
import ua.zakharvalko.springbootdemo.dao.UserRepository;
import ua.zakharvalko.springbootdemo.domain.*;
import ua.zakharvalko.springbootdemo.domain.filter.OperationFilter;
import ua.zakharvalko.springbootdemo.service.OperationService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest({OperationRestController.class})
@ContextConfiguration(classes = SpringBootDemoApplication.class)
@WithMockUser(username = "alexs", password = "123", authorities = "Admin")
class OperationRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OperationService operationService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AccountRepository accountRepository;

    @Test
    void shouldAddOperation() throws Exception {

        Operation operation = Operation.builder()
                .id(1)
                .description("Test operation")
                .date(new Date())
                .price(1000L)
                .operation_type_id(1)
                .category_id(1)
                .account_id(1)
                .build();

        when(operationService.getById(any())).thenReturn(operation);

        Account account = Account.builder().id(1).build();
        User user = User.builder().username("alexs").build();
        when(userRepository.getById(any())).thenReturn(user);
        when(accountRepository.getById(any())).thenReturn(account);

        operationService.save(any(Operation.class));

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/operations/")
                        .content(asJsonString(operation))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andExpect(content().json("{}"));

        verify(operationService).save(any(Operation.class));
    }

    @Test
    void shouldDeleteOperation() throws Exception {
        Operation operation = Operation.builder().id(1).build();

        when(operationService.getById(any())).thenReturn(operation);

        Account account = Account.builder().id(1).build();
        User user = User.builder().username("alexs").build();
        when(userRepository.getById(any())).thenReturn(user);
        when(accountRepository.getById(any())).thenReturn(account);

        mockMvc.perform( MockMvcRequestBuilders.delete("/api/operations/{id}", 1) )
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));

        verify(operationService, times(2)).getById(1);
        verify(operationService).delete(1);
        verifyNoMoreInteractions(operationService);
    }

    @Test
    void shouldEditOperation() throws Exception {
        Operation oldOperation = Operation.builder().id(1).description("Old description").build();
        Operation newOperation = Operation.builder().id(1).description("New description").build();

        operationService.update(oldOperation);

        when(operationService.getById(any())).thenReturn(oldOperation);

        Account account = Account.builder().id(1).build();
        User user = User.builder().username("alexs").build();
        when(userRepository.getById(any())).thenReturn(user);
        when(accountRepository.getById(any())).thenReturn(account);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/operations/")
                .content(asJsonString(newOperation))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));

        verify(operationService, times(2)).update(any());
    }

    @Test
    void shouldReturnOperationById() throws Exception {
        Operation operation = Operation.builder().id(1).build();

        when(operationService.getById(any())).thenReturn(operation);

        Account account = Account.builder().id(1).build();
        User user = User.builder().username("alexs").build();
        when(userRepository.getById(any())).thenReturn(user);
        when(accountRepository.getById(any())).thenReturn(account);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/operations/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));

        verify(operationService, times(2)).getById(1);
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
                        .operation_type_id(1)
                        .category_id(1)
                        .account_id(1)
                        .build());
        operationsList.add(
                Operation.builder()
                        .id(2)
                        .description("Test operation 2")
                        .date(new Date())
                        .price(1500L)
                        .operation_type_id(2)
                        .category_id(1)
                        .account_id(1)
                        .build());

        OperationFilter filter = new OperationFilter();
        filter.setAccount_id(1);

        when(operationService.getById(any())).thenReturn(Operation.builder().id(1).build());

        Account account = Account.builder().id(1).build();
        User user = User.builder().username("alexs").build();
        when(userRepository.getById(any())).thenReturn(user);
        when(accountRepository.getById(any())).thenReturn(account);

        when(operationService.getOperationsByFilter(any())).thenReturn(operationsList);

        mockMvc.perform(post("/api/operations/get-operation-by-filter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(filter)))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}, {}]"));

        verify(operationService).getOperationsByFilter(any());
    }

    @Test
    void shouldReturnTotalExpensesByFilter() throws Exception {
        OperationFilter filter = new OperationFilter();
        filter.setAccount_id(1);

        List<Operation> operations = new ArrayList<>();
        operations.add(Operation.builder().id(1).build());

        when(operationService.getById(any())).thenReturn(Operation.builder().id(1).build());

        Account account = Account.builder().id(1).operations(operations).build();
        User user = User.builder().username("alexs").build();
        when(userRepository.getById(any())).thenReturn(user);
        when(accountRepository.getById(any())).thenReturn(account);

        when(operationService.getTotalExpensesByFilter(any())).thenReturn(100.35);
        mockMvc.perform(post("/api/operations/get-total-by-filter")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(filter)))
                .andExpect(status().isOk())
                .andExpect(content().string("100.35"));

        verify(operationService).getTotalExpensesByFilter(any());
    }

    @Test
    void shouldMakeTransferBetweenAccounts() throws Exception {
        List<Operation> operations = new ArrayList<>();
        Operation operation = Operation.builder()
                .id(1)
                .account_id(1)
                .operation_type_id(1)
                .transfer_to(2)
                .total_for_transfer(1000L)
                .build();

        Operation back = Operation.builder()
                .description("Transfer from: " + operation.getAccount_id())
                .date(new Date())
                .price(operation.getTotal_for_transfer())
                .account_id(operation.getTransfer_to())
                .operation_type_id(2)
                .build();

        operations.add(operation);
        operations.add(back);

        Account account = Account.builder().id(1).build();
        User user = User.builder().username("alexs").build();
        when(userRepository.getById(any())).thenReturn(user);
        when(accountRepository.getById(any())).thenReturn(account);
        when(operationService.getById(any())).thenReturn(operation);

        when(operationService.transferBetweenAccounts(any())).thenReturn(operations);

        mockMvc.perform(post("/api/operations/transfer-btw-accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(operation)))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}, {}]"));

        verify(operationService).transferBetweenAccounts(any());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}