package ua.zakharvalko.service.impl;

import org.springframework.stereotype.Service;
import ua.zakharvalko.dao.OperationTypeRepository;
import ua.zakharvalko.domain.operationtype.OperationType;
import ua.zakharvalko.service.OperationService;
import ua.zakharvalko.service.OperationTypeService;

import java.util.List;

@Service
public class OperationTypeServiceImpl implements OperationTypeService{

    private OperationTypeRepository operationTypeRepository;

    @Override
    public OperationType addOperationType(OperationType type) {
        return operationTypeRepository.saveAndFlush(type);
    }

    @Override
    public void deleteOperationType(Integer id) {
        operationTypeRepository.deleteById(id);
    }

    @Override
    public OperationType getById(Integer id) {
        return operationTypeRepository.getById(id);
    }

    @Override
    public OperationType editOperationType(OperationType type) {
        return operationTypeRepository.saveAndFlush(type);
    }

    @Override
    public List<OperationType> getAllOperationTypes() {
        return operationTypeRepository.findAll();
    }
}
