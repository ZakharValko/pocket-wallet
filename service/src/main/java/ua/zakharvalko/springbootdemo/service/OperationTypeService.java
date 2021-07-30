package ua.zakharvalko.springbootdemo.service;

import ua.zakharvalko.springbootdemo.domain.OperationType;

import java.util.List;

public interface OperationTypeService {

    OperationType addOperationType(OperationType type);
    void deleteOperationType(Integer id);
    OperationType getById(Integer id);
    OperationType editOperationType(OperationType type);
    List<OperationType> getAllOperationTypes();

}
