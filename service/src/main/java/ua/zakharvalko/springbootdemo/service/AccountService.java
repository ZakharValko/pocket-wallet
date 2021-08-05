package ua.zakharvalko.springbootdemo.service;

import ua.zakharvalko.springbootdemo.domain.Account;

import java.util.Date;
import java.util.List;

public interface AccountService {

    Account saveOrUpdate(Account account);
    void delete(Integer id);
    Account getById(Integer id);
    List<Account> getAll();
    double getCurrentBalanceOnDate(Integer id, Date date);
}
