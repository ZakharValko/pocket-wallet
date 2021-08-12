package ua.zakharvalko.springbootdemo.dao;

import org.springframework.stereotype.Repository;
import ua.zakharvalko.springbootdemo.domain.OperationType;

@Repository
public interface OperationTypeRepository extends AbstractRepository<OperationType> {
}
