package ua.zakharvalko.springbootdemo.dao;

import org.springframework.stereotype.Repository;
import ua.zakharvalko.springbootdemo.domain.Currency;

@Repository
public interface CurrencyRepository extends AbstractRepository<Currency> {
}
