package ua.zakharvalko.springbootdemo.dao;

import org.springframework.stereotype.Repository;
import ua.zakharvalko.springbootdemo.domain.Account;

@Repository
public interface AccountRepository extends AbstractRepository<Account> {
}
