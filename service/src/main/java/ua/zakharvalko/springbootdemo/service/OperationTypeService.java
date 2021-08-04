package ua.zakharvalko.springbootdemo.service;

import ua.zakharvalko.springbootdemo.domain.OperationType;

import java.util.List;

public interface OperationTypeService {

    OperationType saveOrUpdate(OperationType type);
    void delete(Integer id);
    OperationType getById(Integer id);
    List<OperationType> getAll();

}
