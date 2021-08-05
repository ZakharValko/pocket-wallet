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

        Account added = accountService.saveOrUpdate(account);

        assertEquals(account.getId(), added.getId());
        verify(accountRepository).saveAndFlush(account);
    }

    @Test
    public void shouldDeleteAccount() {
        Account account = Account.builder().id(1).build();

        when(accountRepository.getById(account.getId())).thenReturn(account);
        accountService.delete(account.getId());
        verify(accountRepository).deleteById(account.getId());

    }

    @Test
    public void shouldEditAccount() {
        Account oldAccount = Account.builder().id(1).build();
        oldAccount.setBalance(150L);
        Account newAccount = Account.builder().id(1).build();
        newAccount.setBalance(100L);

        given(accountRepository.getById(oldAccount.getId())).willReturn(oldAccount);
        accountService.saveOrUpdate(newAccount);

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

        List<Account> accounts = accountService.getAll();

        assertEquals(accounts, accountsFromMock);
        verify(accountRepository).findAll();
    }

    @Test
    public void shouldReturnBalanceOnDate() {
        Operation operation = new Operation(1, "description", new Date(5000), 50L, OperationType.builder().id(1).build(), Category.builder().id(1).group(GroupOfCategories.builder().id(1).build()).build());
        List<Operation> operations = new ArrayList<>();
        operations.add(operation);

        Account account = Account.builder().id(1).number("1347 5423 4321 1212").balance(10000L)
                .user(User.builder().id(1).build())
                .currency(Currency.builder().id(1).build())
                .operations(operations)
                .build();

        when(accountRepository.getById(1)).thenReturn(account);

        Long actual = accountService.getCurrentBalanceOnDate(1, new Date());
        assertEquals(50, actual);
    }
}