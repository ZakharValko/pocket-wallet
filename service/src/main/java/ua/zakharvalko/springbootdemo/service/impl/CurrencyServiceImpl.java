package ua.zakharvalko.springbootdemo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.zakharvalko.springbootdemo.dao.CurrencyRepository;
import ua.zakharvalko.springbootdemo.domain.Currency;
import ua.zakharvalko.springbootdemo.service.CurrencyService;

import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Override
    public Currency addCurrency(Currency currency) {
        return currencyRepository.saveAndFlush(currency);
    }

    @Override
    public void deleteCurrency(Integer id) {
        currencyRepository.deleteById(id);
    }

    @Override
    public Currency editCurrency(Currency currency) {
        return currencyRepository.saveAndFlush(currency);
    }

    @Override
    public Currency getById(Integer id) {
        return currencyRepository.getById(id);
    }

    @Override
    public List<Currency> getAllCurrencies() {
        return currencyRepository.findAll();
    }
}
