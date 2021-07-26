package ua.zakharvalko.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jersey.JerseyProperties;
import org.springframework.stereotype.Service;
import ua.zakharvalko.dao.OperationRepository;
import ua.zakharvalko.domain.category.Category;
import ua.zakharvalko.domain.group.GroupOfCategories;
import ua.zakharvalko.domain.operation.FilterOperation;
import ua.zakharvalko.domain.operation.Operation;
import ua.zakharvalko.service.GroupOfCategoryService;
import ua.zakharvalko.service.OperationService;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class OperationServiceImpl implements OperationService {

    @Autowired
    private OperationRepository operationRepository;

    @Override
    public Operation addOperation(Operation operation) {
        return operationRepository.saveAndFlush(operation);
    }

    @Override
    public Operation getById(Integer id) {
        return operationRepository.getById(id);
    }

    @Override
    public List<Operation> getAllOperations() {
        return operationRepository.findAll();
    }

    @Override
    public List<Operation> getAllOperationsByGroupOfCategories(Integer id) {
        List<Operation> operationByGroupOfCategories = new ArrayList<>();

        List<Operation> allOperations = operationRepository.findAll();

        for (int i = 0; i < allOperations.size(); i++) {
            if (allOperations.get(i).getCategory().getGroup().getId() == id) {
                operationByGroupOfCategories.add(allOperations.get(i));
            }
        }
        return operationByGroupOfCategories;
    }

    @Override
    public List<Operation> getAllOperationsByTimeInterval(Date from, Date to) {
        List<Operation> operationsByTimeInterval = new ArrayList<>();
        List<Operation> allOperations = operationRepository.findAll();

        for (int i = 0; i < allOperations.size(); i++) {
            if (allOperations.get(i).getDate().after(from) && allOperations.get(i).getDate().before(to)) {
                operationsByTimeInterval.add(allOperations.get(i));
            }
        }
        return operationsByTimeInterval;
    }

    @Override
    public List<Operation> getOperationsByAccountAndTimeInterval(Integer id, Date from, Date to) {
        List<Operation> operationsByTimeInterval = getAllOperationsByTimeInterval(from, to);
        List<Operation> operationsById = new ArrayList<>();

        for (int i = 0; i < operationsByTimeInterval.size(); i++) {
            if (operationsByTimeInterval.get(i).getAccount().getId() == id) {
                operationsById.add(operationsByTimeInterval.get(i));
            }
        }
        return operationsById;
    }

    @Override
    public List<Operation> getOperationByFilter(Integer account, Integer category, Integer group, Integer currency, Integer operationType, Date from, Date to) {
        List<Operation> allOperations = this.operationRepository.findAll();
        List<Operation> filteredOperations = allOperations.stream()
                .filter(o -> ( null == account || account.equals(o.getAccount().getId()))
                && (null == category || category.equals(o.getCategory().getId()))
                && (null == group || group.equals(o.getCategory().getGroup().getId()))
                && (null == currency || currency.equals(o.getAccount().getCurrency().getId()))
                && (null == operationType || operationType.equals(o.getOperationType().getId()))
                && (null == from || o.getDate().after(from))
                && (null == to || o.getDate().before(to)))
                .collect(Collectors.toList());
        return filteredOperations;
    }
}
