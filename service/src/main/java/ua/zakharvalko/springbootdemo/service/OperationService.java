package ua.zakharvalko.springbootdemo.service;

import ua.zakharvalko.springbootdemo.domain.Operation;
import ua.zakharvalko.springbootdemo.domain.specification.OperationFilter;

import java.util.List;

public interface OperationService extends AbstractCrudService<Operation> {

    List<Operation> getOperationsByFilter(OperationFilter filter);
    double getTotalExpensesByFilter(OperationFilter filter);
    List<Operation> transferBetweenAccounts(Operation operation);

}
