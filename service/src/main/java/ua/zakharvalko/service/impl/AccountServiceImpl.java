package ua.zakharvalko.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.zakharvalko.dao.AccountRepository;
import ua.zakharvalko.domain.account.Account;
import ua.zakharvalko.domain.operation.Operation;
import ua.zakharvalko.service.AccountService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public Account getById(Integer id) {
        return accountRepository.getById(id);
    }

    @Override
    public Account editAccount(Account account) {
        return accountRepository.saveAndFlush(account);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Double getCurrentBalanceOnDate(Integer id, Date date) {
        List<Operation> operations = accountRepository.getById(id).getOperations();
        List<Operation> operationsByDate = new ArrayList<>();
        Double balance = accountRepository.getById(id).getBalance();

        for (int i = 0; i < operations.size(); i++) {
            if(operations.get(i).getDate().before(date)){
                operationsByDate.add(operations.get(i));
            }
        }

        for (int i = 0; i <operationsByDate.size() ; i++) {
            Double price = operationsByDate.get(i).getPrice();
            Integer operationType = operationsByDate.get(i).getOperationType().getId();
            if(operationType == 1){
                balance = balance - price;
            } else if(operationType == 2) {
                balance = balance + price;
            } else if(operationType == 3) {
                balance = balance - price;
            }
        }
        return balance;
    }
}
