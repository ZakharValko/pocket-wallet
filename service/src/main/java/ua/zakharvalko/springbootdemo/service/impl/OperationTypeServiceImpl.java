package ua.zakharvalko.springbootdemo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.zakharvalko.springbootdemo.dao.OperationTypeRepository;
import ua.zakharvalko.springbootdemo.domain.OperationType;
import ua.zakharvalko.springbootdemo.service.OperationTypeService;

import java.util.List;

@Service
public class OperationTypeServiceImpl implements OperationTypeService{

    @Autowired
    private OperationTypeRepository operationTypeRepository;

    @Override
    public OperationType saveOrUpdate(OperationType type) {
        return operationTypeRepository.saveAndFlush(type);
    }

    @Override
    public void delete(Integer id) {
        operationTypeRepository.deleteById(id);
    }

    @Override
    public OperationType getById(Integer id) {
        return operationTypeRepository.getById(id);
    }

    @Override
    public List<OperationType> getAll() {
        return operationTypeRepository.findAll();
    }
}
