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
import ua.zakharvalko.springbootdemo.domain.Currency;
import ua.zakharvalko.springbootdemo.service.CurrencyService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest({CurrencyRestController.class})
@ContextConfiguration(classes = SpringBootDemoApplication.class)
@WithMockUser(username = "alexs", password = "123", authorities = "Admin")
class CurrencyRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrencyService currencyService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void shouldAddCurrency() throws Exception {
        Currency currency = Currency.builder().id(1).build();
        currencyService.save(currency);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/currencies/")
                        .content(asJsonString(currency))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isCreated())
                .andExpect(content().json("{}"));

        verify(currencyService, times(2)).save(any(Currency.class));
        verifyNoMoreInteractions(currencyService);
    }

    @Test
    void shouldDeleteCurrency() throws Exception {
        Currency currency = Currency.builder().id(1).build();
        when(currencyService.getById(1)).thenReturn(currency);
        mockMvc.perform( MockMvcRequestBuilders.delete("/api/currencies/{id}", 1) )
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));

        verify(currencyService).getById(1);
        verify(currencyService).delete(1);
        verifyNoMoreInteractions(currencyService);
    }

    @Test
    void shouldEditCurrency() throws Exception {
        Currency oldCurrency = Currency.builder().id(1).title("USD").build();
        Currency newCurrency = Currency.builder().id(1).title("US Dollars").build();
        currencyService.update(oldCurrency);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/currencies/")
                .content(asJsonString(newCurrency))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isOk())
                .andExpect(content().json("{}, {}"));

        verify(currencyService, times(2)).update(any(Currency.class));
        verifyNoMoreInteractions(currencyService);
    }

    @Test
    void shouldReturnCurrencyById() throws Exception {
        Currency currency = Currency.builder().id(1).build();
        when(currencyService.getById(1)).thenReturn(currency);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/currencies/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));

        verify(currencyService).getById(1);
        verifyNoMoreInteractions(currencyService);
    }

    @Test
    void shouldReturnAllCurrencies() throws Exception {
        List<Currency> currencies = Arrays.asList(
                Currency.builder().id(1).build(),
                Currency.builder().id(2).build()
        );
        when(currencyService.getAll()).thenReturn(currencies);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/currencies/"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}, {}]"));

        verify(currencyService).getAll();
        verifyNoMoreInteractions(currencyService);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}