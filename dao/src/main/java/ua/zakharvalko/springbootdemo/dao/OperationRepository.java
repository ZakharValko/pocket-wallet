package ua.zakharvalko.springbootdemo.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ua.zakharvalko.springbootdemo.domain.Operation;

@Repository
public interface OperationRepository extends AbstractRepository<Operation>, JpaSpecificationExecutor<Operation> {
}
