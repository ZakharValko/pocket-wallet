package ua.zakharvalko.springbootdemo.service.impl;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.zakharvalko.springbootdemo.dao.AccountRepository;
import ua.zakharvalko.springbootdemo.dao.OperationRepository;
import ua.zakharvalko.springbootdemo.domain.Account;
import ua.zakharvalko.springbootdemo.domain.Operation;
import ua.zakharvalko.springbootdemo.domain.spec.OperationSpecifications;
import ua.zakharvalko.springbootdemo.domain.spec.SearchCriteria;
import ua.zakharvalko.springbootdemo.domain.spec.SearchOperation;
import ua.zakharvalko.springbootdemo.service.AccountService;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private OperationRepository operationRepository;

    @Override
    public Account saveOrUpdate(Account account) {
        return accountRepository.saveAndFlush(account);
    }

    @Override
    public void delete(Integer id) {
        accountRepository.deleteById(id);
    }

    @Override
    public Account getById(Integer id) {
        return accountRepository.getById(id);
    }

    @Override
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    @Override
    public double getCurrentBalanceOnDate(Integer id, Date date) {
        if(date == null) {
            date = new Date();
        }

        OperationSpecifications specifications = new OperationSpecifications();
        specifications.add(new SearchCriteria("account", id, SearchOperation.EQUAL));
        specifications.add(new SearchCriteria("date", date, SearchOperation.BEFORE));

        long balance = accountRepository.getById(id).getBalance();
        List<Operation> operations = operationRepository.findAll(specifications);

        for (Operation operation : operations) {
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
        return balance/100.00;
    }
}
