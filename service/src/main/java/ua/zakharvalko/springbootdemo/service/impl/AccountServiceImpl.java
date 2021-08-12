package ua.zakharvalko.springbootdemo.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.zakharvalko.springbootdemo.dao.AccountRepository;
import ua.zakharvalko.springbootdemo.dao.OperationRepository;
import ua.zakharvalko.springbootdemo.domain.Account;
import ua.zakharvalko.springbootdemo.domain.Operation;
import ua.zakharvalko.springbootdemo.domain.specification.OperationSpecifications;
import ua.zakharvalko.springbootdemo.domain.specification.SearchCriteria;
import ua.zakharvalko.springbootdemo.domain.specification.SearchOperation;
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
            Integer operationType = operation.getOperationType().getId();
            if(operationType.equals(1)){
                Long price = operation.getTotalForTransfer();
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
