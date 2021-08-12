package ua.zakharvalko.springbootdemo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.zakharvalko.springbootdemo.dao.OperationTypeRepository;
import ua.zakharvalko.springbootdemo.domain.OperationType;
import ua.zakharvalko.springbootdemo.service.OperationTypeService;

@Service
public class OperationTypeServiceImpl extends AbstractServiceImpl<OperationType, OperationTypeRepository> implements OperationTypeService {

    @Autowired
    private OperationTypeRepository operationTypeRepository;

    public OperationTypeServiceImpl(OperationTypeRepository repository) {
        super(repository);
    }
}
