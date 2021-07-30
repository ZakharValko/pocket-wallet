package ua.zakharvalko.springbootdemo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.zakharvalko.springbootdemo.domain.Operation;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Integer> {
}
