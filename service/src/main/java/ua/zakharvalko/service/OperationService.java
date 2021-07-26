package ua.zakharvalko.service;

import ua.zakharvalko.domain.group.GroupOfCategories;
import ua.zakharvalko.domain.operation.FilterOperation;
import ua.zakharvalko.domain.operation.Operation;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public interface OperationService {

    Operation addOperation(Operation operation);
    Operation getById(Integer id);
    List<Operation> getAllOperations();
    List<Operation> getAllOperationsByGroupOfCategories(Integer id);
    List<Operation> getAllOperationsByTimeInterval(Date from, Date to);
    List<Operation> getOperationsByAccountAndTimeInterval(Integer id, Date from, Date to);
    List<Operation> getOperationByFilter(Integer account, Integer category, Integer group, Integer currency, Integer operationType, Date from, Date to);
}
