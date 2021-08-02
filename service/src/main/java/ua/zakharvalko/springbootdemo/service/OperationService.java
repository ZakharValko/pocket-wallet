package ua.zakharvalko.springbootdemo.service;

import ua.zakharvalko.springbootdemo.domain.Operation;

import java.util.Date;
import java.util.List;

public interface OperationService {

    Operation addOperation(Operation operation);
    void deleteOperation(Integer id);
    Operation editOperation(Operation operation);
    Operation getById(Integer id);
    List<Operation> getAllOperations();
    List<Operation> getOperationByFilter(Integer account, Integer category, Integer group, Integer currency, Integer operationType, Date from, Date to);
    Double getTotalExpensesByFilter(Integer account, Integer category, Integer group, Integer currency, Date from, Date to);
    Double getCashFlow(Integer account, Date from, Date to);
}
