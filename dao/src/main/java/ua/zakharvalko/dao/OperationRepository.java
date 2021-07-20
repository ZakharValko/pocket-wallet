package ua.zakharvalko.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.zakharvalko.domain.operation.Operation;

public interface OperationRepository extends JpaRepository<Operation, Integer> {
}
