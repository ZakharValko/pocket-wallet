package ua.zakharvalko.springbootdemo.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import ua.zakharvalko.springbootdemo.domain.Operation;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@MybatisTest
class OperationRepositoryTest {

    @Autowired
    private OperationRepository operationRepository;

    private Operation operation;

    @BeforeEach
    void setUp() {
        Operation first = Operation.builder()
                .id(1)
                .description("First operation")
                .build();
        Operation second = Operation.builder()
                .id(2)
                .description("Second operation")
                .build();

        operationRepository.save(first);
        operationRepository.save(second);
    }

    @Test
    void shouldSaveOperation() {
        Operation saved = Operation.builder().id(3).build();
        operationRepository.save(saved);
        Operation fromDb = operationRepository.getById(3);
        assertEquals(saved.getId(), fromDb.getId());
    }

    @Test
    void shouldGetOperationById() {
        operation = operationRepository.getById(1);
        assertThat(operationRepository.getById(operation.getId())).isEqualTo(operation);
    }

    @Test
    void shouldGetAllOperations() {
        List<Operation> operations = operationRepository.getAll();
        assertNotNull(operations);
        assertThat(operations).hasSize(2);
    }

    @Test
    void shouldEditOperation() {
        Operation newOperation = Operation.builder()
                .id(1)
                .description("New description of operation")
                .build();
        operationRepository.update(newOperation);
        Operation editedOperation = operationRepository.getById(1);
        assertEquals("New description of operation", editedOperation.getDescription());
    }

    @Test
    void shouldDeleteOperation() {
        operationRepository.delete(1);
        List<Operation> operations = operationRepository.getAll();
        assertThat(operations).hasSize(1);
    }

}