package ua.zakharvalko.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.zakharvalko.domain.currency.Currency;

public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
}
