package ua.zakharvalko.springbootdemo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.zakharvalko.springbootdemo.domain.OperationType;

@Repository
public interface OperationTypeRepository extends JpaRepository<OperationType, Integer> {
}
