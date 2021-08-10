package ua.zakharvalko.springbootdemo.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import ua.zakharvalko.springbootdemo.domain.Account;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    private Account account;

    @BeforeEach
    void setUp() {
        List<Account> accounts = new ArrayList<>();
        Account first = Account.builder()
                .id(1)
                .number("2345 7364 1733 7111")
                .balance(1000L)
                .build();
        Account second = Account.builder()
                .id(2)
                .number("2345 7364 1733 2222")
                .balance(1500L)
                .build();
        accounts.add(first);
        accounts.add(second);

        accountRepository.saveAll(accounts);
    }


    @Test
    void shouldSaveAccount() {
        Account saved = accountRepository.saveAndFlush(Account.builder().id(3).build());
        accountRepository.saveAndFlush(saved);
        Account fromDb = accountRepository.getById(3);
        assertEquals(saved, fromDb);
    }

    @Test
    void shouldGetAccountById() {
        account = accountRepository.getById(1);
        assertThat(accountRepository.getById(account.getId())).isEqualTo(account);
    }

    @Test
    void shouldGetAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        assertNotNull(accounts);
        assertThat(accounts).hasSize(2);
    }

    @Test
    void shouldEditAccount() {
        Account newAccount = Account.builder()
                .id(1)
                .number("2345 7364 1733 7222")
                .build();
        accountRepository.saveAndFlush(newAccount);
        Account editedAccount = accountRepository.getById(1);
        assertEquals("2345 7364 1733 7222", editedAccount.getNumber());
    }

    @Test
    void shouldDeleteAccount() {
        accountRepository.deleteById(2);
        List<Account> accounts = accountRepository.findAll();
        assertThat(accounts).hasSize(1);
    }
}