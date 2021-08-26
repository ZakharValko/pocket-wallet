package ua.zakharvalko.springbootdemo.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import ua.zakharvalko.springbootdemo.domain.Currency;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@MybatisTest
class CurrencyRepositoryTest {

    @Autowired
    private CurrencyRepository currencyRepository;

    private Currency currency;

    @BeforeEach
    void setUp() {
        Currency first = Currency.builder()
                .id(1)
                .title("USD")
                .build();
        Currency second = Currency.builder()
                .id(2)
                .title("UAH")
                .build();

        currencyRepository.save(first);
        currencyRepository.save(second);
    }

    @Test
    void shouldSaveCurrency() {
        Currency saved = Currency.builder().id(3).build();
        currencyRepository.save(saved);
        Currency fromDb = currencyRepository.getById(3);
        assertEquals(saved.getId(), fromDb.getId());
    }

    @Test
    void shouldGetCurrencyById() {
        currency = currencyRepository.getById(1);
        assertThat(currencyRepository.getById(currency.getId())).isEqualTo(currency);
    }

    @Test
    void shouldGetAllCurrencies() {
        List<Currency> currencies = currencyRepository.getAll();
        assertNotNull(currencies);
        assertThat(currencies).hasSize(2);
    }

    @Test
    void shouldEditCurrency() {
        Currency newCurrency = Currency.builder()
                .id(1)
                .title("US DOLLARS")
                .build();
        currencyRepository.update(newCurrency);
        Currency editedCurrency = currencyRepository.getById(1);
        assertEquals("US DOLLARS", editedCurrency.getTitle());
    }

    @Test
    void shouldDeleteCurrency() {
        currencyRepository.delete(1);
        List<Currency> currencies = currencyRepository.getAll();
        assertThat(currencies).hasSize(1);
    }

}