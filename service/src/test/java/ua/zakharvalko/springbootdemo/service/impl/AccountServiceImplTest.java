package ua.zakharvalko.springbootdemo.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import ua.zakharvalko.springbootdemo.dao.AccountRepository;
import ua.zakharvalko.springbootdemo.dao.OperationRepository;
import ua.zakharvalko.springbootdemo.domain.Account;
import ua.zakharvalko.springbootdemo.domain.Operation;
import ua.zakharvalko.springbootdemo.domain.OperationType;
import ua.zakharvalko.springbootdemo.domain.spec.OperationSpecifications;
import ua.zakharvalko.springbootdemo.service.AccountService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = AccountServiceImpl.class)
@RunWith(SpringRunner.class)
class AccountServiceImplTest {

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private OperationRepository operationRepository;

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
        Account account = Account.builder().id(1).balance(10000L).build();
        List<Operation> operations = new ArrayList<>();
        operations.add(Operation.builder().account(account).operationType(OperationType.builder().id(1).build()).price(1000L).date(parseDate("2021-05-05T13:19:49")).build());
        operations.add(Operation.builder().account(account).operationType(OperationType.builder().id(2).build()).price(2000L).date(parseDate("2021-05-05T13:19:49")).build());
        account.setOperations(operations);

        when(operationRepository.findAll(ArgumentMatchers.any(OperationSpecifications.class))).thenReturn(operations);
        when(accountRepository.getById(1)).thenReturn(account);

        double balance = accountService.getCurrentBalanceOnDate(1, new Date());

        assertThat(balance).isEqualTo(110.00);
    }

    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
}