package ua.zakharvalko.springbootdemo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.zakharvalko.springbootdemo.dao.AccountRepository;
import ua.zakharvalko.springbootdemo.domain.Account;
import ua.zakharvalko.springbootdemo.domain.Operation;
import ua.zakharvalko.springbootdemo.service.AccountService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account addAccount(Account account) {
        return accountRepository.saveAndFlush(account);
    }

    @Override
    public void deleteAccount(Integer id) {
        accountRepository.deleteById(id);
    }

    @Override
    public Account editAccount(Account account) {
        return accountRepository.saveAndFlush(account);
    }

    @Override
    public Account getById(Integer id) {
        return accountRepository.getById(id);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Long getCurrentBalanceOnDate(Integer id, Date date) {
        List<Operation> operations = accountRepository.getById(id).getOperations();
        Long balance = accountRepository.getById(id).getBalance();


        if(date == null) {
            Date now = new Date();
            date = now;
        }

        Date finalDate = date;
        List<Operation> operationsByDate = operations.stream()
                .filter(operation -> operation.getDate().before(finalDate))
                .collect(Collectors.toList());

        for (Operation operation : operationsByDate) {
            Long price = operation.getPrice();
            Integer operationType = operation.getOperationType().getId();
            if (operationType == 1) {
                balance -= price;
            } else if (operationType == 2) {
                balance += price;
            } else if (operationType == 3) {
                balance -= price;
            }
        }
        return balance;
    }
}
