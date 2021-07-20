package ua.zakharvalko.service;

import ua.zakharvalko.domain.currency.Currency;

import java.util.List;

public interface CurrencyService {

    Currency addCurrency(Currency currency);
    void deleteCurrency(Integer id);
    Currency getById(Integer id);
    List<Currency> getAllCurrencies();

}
