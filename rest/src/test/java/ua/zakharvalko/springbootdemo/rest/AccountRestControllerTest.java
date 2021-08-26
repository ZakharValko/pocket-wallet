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
import ua.zakharvalko.springbootdemo.dao.UserRepository;
import ua.zakharvalko.springbootdemo.domain.Account;
import ua.zakharvalko.springbootdemo.service.AccountService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest({AccountRestController.class})
@ContextConfiguration(classes = SpringBootDemoApplication.class)
class AccountRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldAddAccount() throws Exception {
        Account account = Account.builder().id(1).build();
        accountService.save(account);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/accounts/")
                                .content(asJsonString(account))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isCreated())
                .andExpect(content().json("{}"));

        verify(accountService).save(any(Account.class));
        verifyNoMoreInteractions(accountService);
    }


    @Test
    void shouldDeleteAccount() throws Exception {
        Account account = Account.builder().id(1).build();
        when(accountService.getById(1)).thenReturn(account);
        mockMvc.perform( MockMvcRequestBuilders.delete("/api/accounts/{id}", 1) )
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));

        verify(accountService).getById(1);
        verify(accountService).delete(1);
        verifyNoMoreInteractions(accountService);
    }

    @Test
    void shouldEditAccount() throws Exception {
        Account oldAccount = Account.builder().id(1).balance(100L).build();
        Account newAccount = Account.builder().id(1).balance(150L).build();
        accountService.update(oldAccount);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/accounts/")
                        .content(asJsonString(newAccount))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(content().json("{}, {}"));

        verify(accountService).update(any(Account.class));
        verifyNoMoreInteractions(accountService);
    }

    @Test
    void shouldReturnAccountById() throws Exception {
        Account account = Account.builder().id(1).build();
        when(accountService.getById(1)).thenReturn(account);
        mockMvc.perform(get("/api/accounts/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));

        verify(accountService).getById(1);
        verifyNoMoreInteractions(accountService);
    }

    @Test
    void shouldReturnAllAccounts() throws Exception {
        List<Account> accounts = Arrays.asList(
                Account.builder().id(1).build(),
                Account.builder().id(2).build()
        );
        when(accountService.getAll()).thenReturn(accounts);
        mockMvc.perform(get("/api/accounts/"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}, {}]"));

        verify(accountService).getAll();
        verifyNoMoreInteractions(accountService);
    }

    @Test
    void getCurrentBalanceOnDate() throws Exception {
        double balance = 10000L;

        when(accountService.getCurrentBalanceOnDate(any(), any())).thenReturn(balance/100);

        mockMvc.perform(get("/api/accounts/get-balance-on-date?id=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(balance/100)));

        verify(accountService).getCurrentBalanceOnDate(any(), any());
        verifyNoMoreInteractions(accountService);

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}