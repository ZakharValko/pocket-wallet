package ua.zakharvalko.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.zakharvalko.domain.operationtype.OperationType;

public interface OperationTypeRepository extends JpaRepository<OperationType, Integer> {
}
