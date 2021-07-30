package ua.zakharvalko.springbootdemo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.zakharvalko.springbootdemo.dao.OperationRepository;
import ua.zakharvalko.springbootdemo.domain.Operation;
import ua.zakharvalko.springbootdemo.service.OperationService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OperationServiceImpl implements OperationService {

    @Autowired
    private OperationRepository operationRepository;

    @Override
    public Operation addOperation(Operation operation) {
        return operationRepository.saveAndFlush(operation);
    }

    @Override
    public void deleteOperation(Integer id) {
        operationRepository.deleteById(id);
    }

    @Override
    public Operation getById(Integer id) {
        return operationRepository.getById(id);
    }

    @Override
    public Operation editOperation(Operation operation) {
        return operationRepository.saveAndFlush(operation);
    }

    @Override
    public List<Operation> getAllOperations() {
        return operationRepository.findAll();
    }

    @Override
    public List<Operation> getOperationByFilter(Integer account, Integer category, Integer group, Integer currency, Integer operationType, Date from, Date to) {
        List<Operation> allOperations = this.operationRepository.findAll();
        List<Operation> filteredOperations = allOperations.stream()
                .filter(o -> (null == account || account.equals(o.getAccount().getId()))
                && (null == category || category.equals(o.getCategory().getId()))
                && (null == group || group.equals(o.getCategory().getGroup().getId()))
                && (null == currency || currency.equals(o.getAccount().getCurrency().getId()))
                && (null == operationType || operationType.equals(o.getOperationType().getId()))
                && (null == from || o.getDate().after(from))
                && (null == to || o.getDate().before(to)))
                .collect(Collectors.toList());
        return filteredOperations;
    }

    @Override
    public Double getTotalExpensesByFilter(Integer account, Integer category, Integer group, Integer currency, Date from, Date to) {
        List<Operation> allOperations = this.operationRepository.findAll();

        List<Double> allExpenses = allOperations.stream()
                .filter(o -> (account.equals(o.getAccount().getId()))
                        && (o.getOperationType().getId().equals(3) || o.getOperationType().getId().equals(1))
                        && (null == category || category.equals(o.getCategory().getId()))
                        && (null == group || group.equals(o.getCategory().getGroup().getId()))
                        && (null == currency || currency.equals(o.getAccount().getCurrency().getId()))
                        && (null == from || o.getDate().after(from))
                        && (null == to || o.getDate().before(to)))
                .map(Operation::getPrice)
                .collect(Collectors.toList());

        return allExpenses.stream()
                .mapToDouble(o -> o)
                .sum();
    }

    @Override
    public Double getCashFlow(Integer account, Date from, Date to) {
        List<Operation> allOperations = this.operationRepository.findAll();

        Double outcome = allOperations.stream()
                .filter(o -> (account.equals(o.getAccount().getId()))
                        && (o.getOperationType().getId().equals(1) || o.getOperationType().getId().equals(3))
                        && (o.getDate().after(from))
                        && (o.getDate().before(to)))
                .map(Operation::getPrice)
                .mapToDouble(o -> o)
                .sum();

        Double income = allOperations.stream()
                .filter(o -> (account.equals(o.getAccount().getId()))
                        && (o.getOperationType().getId().equals(2))
                        && (o.getDate().after(from))
                        && (o.getDate().before(to)))
                .map(Operation::getPrice)
                .mapToDouble(o -> o)
                .sum();

        return income - outcome;
    }
}
