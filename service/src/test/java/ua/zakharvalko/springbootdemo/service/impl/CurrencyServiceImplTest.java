package ua.zakharvalko.springbootdemo.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ua.zakharvalko.springbootdemo.dao.CurrencyRepository;
import ua.zakharvalko.springbootdemo.domain.Currency;
import ua.zakharvalko.springbootdemo.service.CurrencyService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = CurrencyServiceImpl.class)
@RunWith(SpringRunner.class)
class CurrencyServiceImplTest {

    @MockBean
    private CurrencyRepository currencyRepository;

    @Autowired
    private CurrencyService currencyService;

    @Test
    public void shouldReturnCurrencyWhenSaved() {
        Currency currency = Currency.builder().id(1).build();

        when(currencyRepository.saveAndFlush(currency)).thenReturn(currency);

        Currency added = currencyService.addCurrency(currency);

        assertEquals(currency.getId(), added.getId());
        verify(currencyRepository).saveAndFlush(currency);
    }

    @Test
    public void shouldDeleteCurrency() {
        Currency currency = Currency.builder().id(1).build();

        when(currencyRepository.getById(currency.getId())).thenReturn(currency);
        currencyService.deleteCurrency(currency.getId());
        verify(currencyRepository).deleteById(currency.getId());
    }

    @Test
    public void shouldReturnAllCurrencies() {
        List<Currency> currencies = new ArrayList<>();
        currencies.add(new Currency());
        when(currencyRepository.findAll()).thenReturn(currencies);

        List<Currency> actual = currencyService.getAllCurrencies();

        assertEquals(currencies, actual);
        verify(currencyRepository).findAll();
    }

    @Test
    public void shouldReturnCurrencyById() {
        Currency currency = Currency.builder().id(1).build();
        when(currencyRepository.getById(1)).thenReturn(currency);

        Currency actual = currencyService.getById(1);

        assertEquals(currency.getId(), actual.getId());
        verify(currencyRepository).getById(1);
    }

    public void shouldEditCurrency() {
        Currency oldCurrency = Currency.builder().id(1).title("USD").build();
        Currency newCurrency = Currency.builder().id(1).title("US Dollars").build();

        given(currencyRepository.getById(oldCurrency.getId())).willReturn(newCurrency);
        currencyService.editCurrency(newCurrency);

        verify(currencyRepository).saveAndFlush(newCurrency);
    }
}