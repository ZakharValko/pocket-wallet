package ua.zakharvalko.springbootdemo.service;

import ua.zakharvalko.springbootdemo.domain.Currency;

import java.util.List;

public interface CurrencyService {

    Currency saveOrUpdate(Currency currency);
    void delete(Integer id);
    Currency getById(Integer id);
    List<Currency> getAll();

}
