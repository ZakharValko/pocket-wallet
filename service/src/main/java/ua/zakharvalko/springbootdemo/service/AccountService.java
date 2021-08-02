package ua.zakharvalko.springbootdemo.service;

import ua.zakharvalko.springbootdemo.domain.Account;

import java.util.Date;
import java.util.List;

public interface AccountService {

    Account addAccount(Account account);
    void deleteAccount(Integer id);
    Account getById(Integer id);
    Account editAccount(Account account);
    List<Account> getAllAccounts();
    Double getCurrentBalanceOnDate(Integer id, Date date);
}