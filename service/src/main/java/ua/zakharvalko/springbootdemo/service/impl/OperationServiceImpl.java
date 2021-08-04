package ua.zakharvalko.springbootdemo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.zakharvalko.springbootdemo.dao.OperationRepository;
import ua.zakharvalko.springbootdemo.domain.Operation;
import ua.zakharvalko.springbootdemo.service.OperationService;
import ua.zakharvalko.springbootdemo.domain.spec.OperationSpecifications;
import ua.zakharvalko.springbootdemo.domain.spec.OperationFilter;
import ua.zakharvalko.springbootdemo.domain.spec.SearchCriteria;
import ua.zakharvalko.springbootdemo.domain.spec.SearchOperation;

import java.util.Date;
import java.util.List;

@Service
public class OperationServiceImpl implements OperationService {

    @Autowired
    private OperationRepository operationRepository;

    @Override
    public Operation saveOrUpdate(Operation operation) {
        return operationRepository.saveAndFlush(operation);
    }

    @Override
    public void delete(Integer id) {
        operationRepository.deleteById(id);
    }

    @Override
    public Operation getById(Integer id) {
        return operationRepository.getById(id);
    }

    @Override
    public List<Operation> getAll() {
        return operationRepository.findAll();
    }

    @Override
    public List<Operation> getOperationsByAccountId(Integer id) {
        return operationRepository.getOperationByAccount_Id(id);
    }

    @Override
    public List<Operation> getOperationsByFilter(OperationFilter filter) {
        OperationSpecifications specifications = new OperationSpecifications();

        if(filter.getAccount().getId() != null){
            specifications.add(new SearchCriteria("account", filter.getAccount().getId(), SearchOperation.EQUAL));
        }
        if(filter.getCategory().getId() != null){
            specifications.add(new SearchCriteria("category", filter.getCategory().getId(), SearchOperation.EQUAL));
        }
        if(filter.getOperationType().getId() != null){
            specifications.add(new SearchCriteria("operationType", filter.getOperationType().getId(), SearchOperation.EQUAL));
        }
        if(filter.getCategory().getGroup().getId() != null){
            specifications.add(new SearchCriteria("category/group", filter.getCategory().getGroup().getId(), SearchOperation.JOIN));
        }
        if(filter.getAccount().getCurrency().getId() != null){
            specifications.add(new SearchCriteria("account/currency", filter.getAccount().getCurrency().getId(), SearchOperation.JOIN));
        }
        if(filter.getFrom() != null){
            specifications.add(new SearchCriteria("date", filter.getFrom(), SearchOperation.AFTER));
        }
        if(filter.getFrom() != null){
            specifications.add(new SearchCriteria("date", filter.getTo(), SearchOperation.BEFORE));
        }
        return operationRepository.findAll(specifications);
    }

    @Override
    public double getTotalExpensesByFilter(OperationFilter filter) {
        if(filter.getOperationType().getId().equals(1) || filter.getOperationType().getId().equals(3)) {
            List<Operation> filteredOperations = getOperationsByFilter(filter);
            long totalInCoins = filteredOperations.stream()
                    .map(Operation::getPrice)
                    .mapToLong(o -> o)
                    .sum();
            return totalInCoins / 100.00;
        } else {
            throw new IllegalArgumentException("Incorrect type operation in filter. Allowed only 'Expenses' and 'Transfers'");
        }
    }

    @Override
    public double getCashFlow(OperationFilter filter) {
        return 0;
    }

}
