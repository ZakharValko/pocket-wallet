package ua.zakharvalko.springbootdemo.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ua.zakharvalko.springbootdemo.dao.AccountRepository;
import ua.zakharvalko.springbootdemo.domain.Currency;
import ua.zakharvalko.springbootdemo.domain.Account;
import ua.zakharvalko.springbootdemo.domain.Category;
import ua.zakharvalko.springbootdemo.domain.GroupOfCategories;
import ua.zakharvalko.springbootdemo.domain.Operation;
import ua.zakharvalko.springbootdemo.domain.OperationType;
import ua.zakharvalko.springbootdemo.domain.User;
import ua.zakharvalko.springbootdemo.service.AccountService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = AccountServiceImpl.class)
@RunWith(SpringRunner.class)
class AccountServiceImplTest {

    @MockBean
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Test
    public void shouldReturnAccountWhenSaved() {
        Account account = Account.builder().id(1).build();

        when(accountRepository.saveAndFlush(account)).thenReturn(account);

        Account added = accountService.addAccount(account);

        assertEquals(account.getId(), added.getId());
        verify(accountRepository).saveAndFlush(account);
    }

    @Test
    public void shouldDeleteAccount() {
        Account account = Account.builder().id(1).build();

        when(accountRepository.getById(account.getId())).thenReturn(account);
        accountService.deleteAccount(account.getId());
        verify(accountRepository).deleteById(account.getId());

    }

    @Test
    public void shouldEditAccount() {
        Account oldAccount = Account.builder().id(1).build();
        oldAccount.setBalance(150.00);
        Account newAccount = Account.builder().id(1).build();
        newAccount.setBalance(100.00);

        given(accountRepository.getById(oldAccount.getId())).willReturn(oldAccount);
        accountService.editAccount(newAccount);

        verify(accountRepository).saveAndFlush(newAccount);
    }

    @Test
    public void shouldReturnAccountById() {
        Account account = Account.builder().id(1).build();
        when(accountRepository.getById(1)).thenReturn(account);

        Account actual = accountService.getById(1);

        assertEquals(account.getId(), actual.getId());
        verify(accountRepository).getById(1);
    }

    @Test
    public void shouldReturnAllAccounts() {
        List<Account> accountsFromMock = new ArrayList<>();
        accountsFromMock.add(new Account());
        when(accountRepository.findAll()).thenReturn(accountsFromMock);

        List<Account> accounts = accountService.getAllAccounts();

        assertEquals(accounts, accountsFromMock);
        verify(accountRepository).findAll();
    }

    @Test
    public void shouldReturnBalanceOnDate() {
        Operation operation = new Operation(1, "description", new Date(5000), 50.00, OperationType.builder().id(1).build(), Category.builder().id(1).group(GroupOfCategories.builder().id(1).build()).build());
        List<Operation> operations = new ArrayList<>();
        operations.add(operation);
        Account account = new Account(1, 13473L, 100.00, User.builder().id(1).build(), Currency.builder().id(1).build(), operations);

        when(accountRepository.getById(1)).thenReturn(account);

        Double actual = accountService.getCurrentBalanceOnDate(1, new Date());
        assertEquals(50, actual);
    }
}