package ua.zakharvalko.springbootdemo.service;

import ua.zakharvalko.springbootdemo.domain.Operation;
import ua.zakharvalko.springbootdemo.domain.spec.OperationFilter;

import java.util.Date;
import java.util.List;

public interface OperationService {

    Operation saveOrUpdate(Operation operation);
    void delete(Integer id);
    Operation getById(Integer id);
    List<Operation> getAll();
    List<Operation> getOperationsByFilter(OperationFilter filter);
    double getTotalExpensesByFilter(OperationFilter filter);
    List<Operation> transferBetweenAccounts(Operation operation);

    //double getCashFlow(OperationFilter filter);
}
