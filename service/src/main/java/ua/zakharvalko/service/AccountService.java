package ua.zakharvalko.service;

import ua.zakharvalko.domain.account.Account;

import java.util.List;

public interface AccountService {

    Account addAccount(Account account);
    void deleteAccount(Integer id);
    Account getById(Integer id);
    Account editAccount(Account account);
    List<Account> getAllAccounts();
}
