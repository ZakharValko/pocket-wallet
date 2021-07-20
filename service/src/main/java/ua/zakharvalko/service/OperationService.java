package ua.zakharvalko.service;

import ua.zakharvalko.domain.operation.Operation;

import java.util.List;

public interface OperationService {

    Operation addOperation(Operation operation);
    Operation getById(Integer id);
    List<Operation> getAllOperations();

}
