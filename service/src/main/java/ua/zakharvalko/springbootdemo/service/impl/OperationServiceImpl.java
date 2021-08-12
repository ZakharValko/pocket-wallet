package ua.zakharvalko.springbootdemo.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.zakharvalko.springbootdemo.dao.OperationRepository;
import ua.zakharvalko.springbootdemo.domain.Operation;
import ua.zakharvalko.springbootdemo.domain.OperationType;
import ua.zakharvalko.springbootdemo.domain.specification.OperationFilter;
import ua.zakharvalko.springbootdemo.domain.specification.OperationSpecifications;
import ua.zakharvalko.springbootdemo.domain.specification.SearchCriteria;
import ua.zakharvalko.springbootdemo.domain.specification.SearchOperation;
import ua.zakharvalko.springbootdemo.service.OperationService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class OperationServiceImpl extends AbstractServiceImpl<Operation, OperationRepository> implements OperationService {

    @Autowired
    private OperationRepository operationRepository;

    public OperationServiceImpl(OperationRepository repository) {
        super(repository);
    }

    @Override
    public Operation saveOrUpdate(Operation operation) {
        if (operation.getOperationType().getId().equals(1)) {
            transferBetweenAccounts(operation);
            }
            return operationRepository.saveAndFlush(operation);
    }

    @Override
    public List<Operation> getOperationsByFilter(OperationFilter filter) {
        OperationSpecifications specifications = new OperationSpecifications();

        if(filter.getAccount() != null && filter.getAccount().getId() != null){
            specifications.add(new SearchCriteria("account", filter.getAccount().getId(), SearchOperation.EQUAL));
        }
        if(filter.getCategory() != null && filter.getCategory().getId() != null){
            specifications.add(new SearchCriteria("category", filter.getCategory().getId(), SearchOperation.EQUAL));
        }
        if(filter.getOperationType() != null && filter.getOperationType().getId() != null){
            specifications.add(new SearchCriteria("operationType", filter.getOperationType().getId(), SearchOperation.EQUAL));
        }
        if(filter.getCategory() != null && filter.getCategory().getGroup() != null && filter.getCategory().getGroup().getId() != null){
            specifications.add(new SearchCriteria("category/group", filter.getCategory().getGroup().getId(), SearchOperation.JOIN));
        }
        if(filter.getAccount() != null && filter.getAccount().getCurrency() != null && filter.getAccount().getCurrency().getId() != null){
            specifications.add(new SearchCriteria("account/currency", filter.getAccount().getCurrency().getId(), SearchOperation.JOIN));
        }
        if(filter.getFrom() != null){
            specifications.add(new SearchCriteria("date", filter.getFrom(), SearchOperation.AFTER));

        }
        if(filter.getTo() != null){
            specifications.add(new SearchCriteria("date", filter.getTo(), SearchOperation.BEFORE));

        }
        return operationRepository.findAll(specifications);
    }

    @Override
    public double getTotalExpensesByFilter(OperationFilter filter) {
        if (filter.getOperationType().getId().equals(1) || filter.getOperationType().getId().equals(3)) {
            List<Operation> filteredOperations = getOperationsByFilter(filter);
            long totalInCoins = 0L;
            for(Operation operation : filteredOperations){
                if(operation.getOperationType().getId().equals(1)){
                    totalInCoins += operation.getTotalForTransfer();
                } else if(operation.getOperationType().getId().equals(3)) {
                    totalInCoins += operation.getPrice();
                }
            }
            return totalInCoins / 100.00;
        } else {
            throw new IllegalArgumentException("Incorrect type operation in filter. Allowed only 'Expenses' and 'Transfers'");
        }
    }

    @Override
    public List<Operation> transferBetweenAccounts(Operation operation) {
        List<Operation> operations = new ArrayList<>();
        if(operation.getOperationType().getId().equals(1) && operation.getTransferTo() != null){
            if(operation.getTotalForTransfer() == null) {
                log.info("Total for transfer is null");
            } else {
                operationRepository.saveAndFlush(operation);
                operations.add(operation);
                Operation back = Operation.builder()
                        .description("Transfer from: " + operation.getAccount().getId())
                        .date(new Date())
                        .price(operation.getTotalForTransfer())
                        .account(operation.getTransferTo())
                        .operationType(OperationType.builder().id(2).build())
                        .build();
                operationRepository.saveAndFlush(back);
                operations.add(back);
            }
        } else {
            log.info("Incorrect 'type of operation' or 'transfer account id' is null");
        }
        return operations;
    }

}
