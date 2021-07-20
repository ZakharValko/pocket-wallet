package ua.zakharvalko.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.zakharvalko.dao.CurrencyRepository;
import ua.zakharvalko.domain.currency.Currency;
import ua.zakharvalko.service.CurrencyService;

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
    public Currency getById(Integer id) {
        return currencyRepository.getById(id);
    }

    @Override
    public List<Currency> getAllCurrencies() {
        return currencyRepository.findAll();
    }
}
