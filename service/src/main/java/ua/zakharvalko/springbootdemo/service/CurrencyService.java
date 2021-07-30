package ua.zakharvalko.springbootdemo.service;

import ua.zakharvalko.springbootdemo.domain.Currency;

import java.util.List;

public interface CurrencyService {

    Currency addCurrency(Currency currency);
    void deleteCurrency(Integer id);
    Currency getById(Integer id);
    Currency editCurrency(Currency currency);
    List<Currency> getAllCurrencies();

}
