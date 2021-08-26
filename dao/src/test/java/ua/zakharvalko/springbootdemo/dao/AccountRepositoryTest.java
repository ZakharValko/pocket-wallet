package ua.zakharvalko.springbootdemo.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import ua.zakharvalko.springbootdemo.domain.Account;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@MybatisTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    private Account account;

    @BeforeEach
    void setUp() {
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
        accountRepository.save(first);
        accountRepository.save(second);
    }


    @Test
    void shouldSaveAccount() {
        Account saved = Account.builder().id(3).build();
        accountRepository.save(saved);
        Account fromDb = accountRepository.getById(3);
        assertEquals(saved.getId(), fromDb.getId());
    }

    @Test
    void shouldGetAccountById() {
        account = accountRepository.getById(1);
        assertThat(accountRepository.getById(account.getId())).isEqualTo(account);
    }

    @Test
    void shouldGetAllAccounts() {
        List<Account> accounts = accountRepository.getAll();
        assertNotNull(accounts);
        assertThat(accounts).hasSize(2);
    }

    @Test
    void shouldEditAccount() {
        Account newAccount = Account.builder()
                .id(1)
                .number("2345 7364 1733 7222")
                .build();
        accountRepository.update(newAccount);
        Account editedAccount = accountRepository.getById(1);
        assertEquals("2345 7364 1733 7222", editedAccount.getNumber());
    }

    @Test
    void shouldDeleteAccount() {
        accountRepository.delete(2);
        List<Account> accounts = accountRepository.getAll();
        assertThat(accounts).hasSize(1);
    }
}