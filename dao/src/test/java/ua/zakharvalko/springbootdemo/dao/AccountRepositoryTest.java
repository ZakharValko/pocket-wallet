package ua.zakharvalko.springbootdemo.dao;

import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import ua.zakharvalko.springbootdemo.domain.Account;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AccountRepositoryTest {

    @Autowired
    private ConfigurableApplicationContext applicationContext;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void shouldSaveAccount() {
        accountRepository.save(Account.builder().build());
    }

    @Test
    public void shouldDeleteAccount() {
        Account account = Account.builder().id(1).build();
        accountRepository.save(account);
        accountRepository.deleteById(account.getId());
    }

    @Test
    public void shouldReturnAccountById() {
        accountRepository.getById(Account.builder().id(1).build().getId());

    }

    @Test
    public void shouldReturnAllAccounts() {
        accountRepository.findAll();
    }

}