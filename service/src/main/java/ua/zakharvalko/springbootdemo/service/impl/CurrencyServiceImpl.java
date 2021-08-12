package ua.zakharvalko.springbootdemo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.zakharvalko.springbootdemo.dao.CurrencyRepository;
import ua.zakharvalko.springbootdemo.domain.Currency;
import ua.zakharvalko.springbootdemo.service.CurrencyService;

@Service
public class CurrencyServiceImpl extends AbstractServiceImpl<Currency, CurrencyRepository> implements CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    public CurrencyServiceImpl(CurrencyRepository repository) {
        super(repository);
    }
}
