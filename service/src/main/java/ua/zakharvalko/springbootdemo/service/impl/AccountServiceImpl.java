package ua.zakharvalko.springbootdemo.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.zakharvalko.springbootdemo.dao.AccountRepository;
import ua.zakharvalko.springbootdemo.dao.OperationRepository;
import ua.zakharvalko.springbootdemo.domain.Account;
import ua.zakharvalko.springbootdemo.domain.Operation;
import ua.zakharvalko.springbootdemo.domain.filter.OperationFilter;
import ua.zakharvalko.springbootdemo.service.AccountService;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class AccountServiceImpl extends AbstractServiceImpl<Account, AccountRepository> implements AccountService{

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private OperationRepository operationRepository;

    public AccountServiceImpl(AccountRepository repository) {
        super(repository);
    }

    @Override
    public double getCurrentBalanceOnDate(Integer id, java.util.Date date) {
        if(date == null) {
            date = new Date();
        }

        OperationFilter filter = new OperationFilter();
        filter.setAccount_id(id);
        filter.setTo(date);

        long balance = accountRepository.getById(id).getBalance();
        List<Operation> operations = operationRepository.getAllByFilter(filter);

        for (Operation operation : operations) {
            Integer operationType = operation.getOperation_type_id();
            if(operationType.equals(1)){
                Long price = operation.getTotal_for_transfer();
                balance -= price;
            } else if (operationType.equals(2)) {
                Long price = operation.getPrice();
                balance += price;
            } else if (operationType.equals(3)) {
                Long price = operation.getPrice();
                balance -= price;
            }
        }
        return balance/100.00;
    }


}
