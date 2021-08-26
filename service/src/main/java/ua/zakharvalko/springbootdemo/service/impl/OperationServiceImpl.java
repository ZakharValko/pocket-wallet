package ua.zakharvalko.springbootdemo.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.zakharvalko.springbootdemo.dao.OperationRepository;
import ua.zakharvalko.springbootdemo.domain.Operation;
import ua.zakharvalko.springbootdemo.domain.filter.OperationFilter;
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
    public void save(Operation operation) {
        if (operation.getOperation_type_id().equals(1)) {
            transferBetweenAccounts(operation);
            }
            operationRepository.save(operation);
    }

    @Override
    public List<Operation> getOperationsByFilter(OperationFilter filter) {
        return operationRepository.getAllByFilter(filter);
    }

    @Override
    public double getTotalExpensesByFilter(OperationFilter filter) {
        if (filter.getOperation_type_id().equals(1) || filter.getOperation_type_id().equals(3)) {
            List<Operation> filteredOperations = getOperationsByFilter(filter);
            long totalInCoins = 0L;
            for(Operation operation : filteredOperations){
                if(operation.getOperation_type_id().equals(1)){
                    totalInCoins += operation.getTotal_for_transfer();
                } else if(operation.getOperation_type_id().equals(3)) {
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
        if(operation.getOperation_type_id().equals(1) && operation.getTransfer_to() != null){
            if(operation.getTotal_for_transfer() == null) {
                log.info("Total for transfer is null");
            } else {
                operationRepository.save(operation);
                operations.add(operation);
                Operation back = Operation.builder()
                        .description("Transfer from: " + operation.getAccount_id())
                        .date(new Date())
                        .price(operation.getTotal_for_transfer())
                        .account_id(operation.getTransfer_to())
                        .operation_type_id(2)
                        .build();
                operationRepository.save(back);
                operations.add(back);
            }
        } else {
            log.info("Incorrect 'type of operation' or 'transfer account id' is null");
        }
        return operations;
    }

}
