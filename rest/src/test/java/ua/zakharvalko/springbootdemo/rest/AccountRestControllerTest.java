package ua.zakharvalko.springbootdemo.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.zakharvalko.springbootdemo.SpringBootDemoApplication;
import ua.zakharvalko.springbootdemo.domain.Account;
import ua.zakharvalko.springbootdemo.service.AccountService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
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

    @Test
    void shouldAddAccount() throws Exception {
        Account account = Account.builder().id(1).build();
        when(accountService.addAccount(account)).thenReturn(account);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/accounts/")
                        .content(asJsonString(account))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isCreated())
                .andExpect(content().json("{}"));
    }


    @Test
    void shouldDeleteAccount() throws Exception {
        Account account = Account.builder().id(1).build();
        when(accountService.getById(1)).thenReturn(account);
        mockMvc.perform( MockMvcRequestBuilders.delete("/api/accounts/{id}", 1) )
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));
    }

    @Test
    void shouldEditAccount() throws Exception {
        Account oldAccount = Account.builder().id(1).balance(100L).build();
        Account newAccount = Account.builder().id(1).balance(150L).build();
        when(accountService.editAccount(oldAccount)).thenReturn(newAccount);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/accounts/")
                .content(asJsonString(newAccount))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isOk())
                .andExpect(content().json("{}, {}"));
    }

    @Test
    void shouldReturnAccountById() throws Exception {
        Account account = Account.builder().id(1).build();
        when(accountService.getById(1)).thenReturn(account);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/accounts/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));
    }

    @Test
    void shouldReturnAllAccounts() throws Exception {
        List<Account> accounts = Arrays.asList(
                Account.builder().id(1).build(),
                Account.builder().id(2).build()
        );
        when(accountService.getAllAccounts()).thenReturn(accounts);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/accounts/"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}, {}]"));

    }

    @Test
    void getCurrentBalanceOnDate() throws Exception {
        when(accountService.getCurrentBalanceOnDate(1, new Date())).thenReturn(0L);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/accounts/get-balance-on-date").param("id", "1").param("date", "1970-01-01"))
                .andExpect(status().isOk())
                .andExpect(content().string("0.0"));

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}