package ua.zakharvalko.springbootdemo.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import ua.zakharvalko.springbootdemo.domain.Currency;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CurrencyRepositoryTest {

    @Autowired
    private CurrencyRepository currencyRepository;

    private Currency currency;

    @BeforeEach
    void setUp() {
        List<Currency> currencies = new ArrayList<>();
        Currency first = Currency.builder()
                .id(1)
                .title("USD")
                .build();
        Currency second = Currency.builder()
                .id(2)
                .title("UAH")
                .build();
        currencies.add(first);
        currencies.add(second);

        currencyRepository.saveAll(currencies);
    }

    @Test
    void shouldSaveCurrency() {
        Currency saved = currencyRepository.saveAndFlush(Currency.builder().id(3).build());
        Currency fromDb = currencyRepository.getById(3);
        assertEquals(saved, fromDb);
    }

    @Test
    void shouldGetCurrencyById() {
        currency = currencyRepository.getById(1);
        assertThat(currencyRepository.getById(currency.getId())).isEqualTo(currency);
    }

    @Test
    void shouldGetAllCurrencies() {
        List<Currency> currencies = currencyRepository.findAll();
        assertNotNull(currencies);
        assertThat(currencies).hasSize(2);
    }

    @Test
    void shouldEditCurrency() {
        Currency newCurrency = Currency.builder()
                .id(1)
                .title("US DOLLARS")
                .build();
        currencyRepository.saveAndFlush(newCurrency);
        Currency editedCurrency = currencyRepository.getById(1);
        assertEquals("US DOLLARS", editedCurrency.getTitle());
    }

    @Test
    void shouldDeleteCurrency() {
        currencyRepository.deleteById(1);
        List<Currency> currencies = currencyRepository.findAll();
        assertThat(currencies).hasSize(1);
    }

}