package ua.zakharvalko.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.zakharvalko.dao.OperationRepository;
import ua.zakharvalko.domain.operation.Operation;
import ua.zakharvalko.service.OperationService;

import java.util.List;

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
}
