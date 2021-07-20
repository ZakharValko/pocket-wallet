package ua.zakharvalko.service;

import ua.zakharvalko.domain.account.Account;
import ua.zakharvalko.domain.operationtype.OperationType;

import java.util.List;

public interface OperationTypeService {

    OperationType addOperationType(OperationType type);
    void deleteOperationType(Integer id);
    OperationType getById(Integer id);
    OperationType editOperationType(OperationType type);
    List<OperationType> getAllOperationTypes();

}
