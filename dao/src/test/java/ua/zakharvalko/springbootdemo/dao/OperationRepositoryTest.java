package ua.zakharvalko.springbootdemo.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import ua.zakharvalko.springbootdemo.domain.Operation;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class OperationRepositoryTest {

    @Autowired
    private OperationRepository operationRepository;

    private Operation operation;

    @BeforeEach
    void setUp() {
        List<Operation> operations = new ArrayList<>();
        Operation first = Operation.builder()
                .id(1)
                .description("First operation")
                .build();
        Operation second = Operation.builder()
                .id(2)
                .description("Second operation")
                .build();
        operations.add(first);
        operations.add(second);

        operationRepository.saveAll(operations);
    }

    @Test
    void shouldSaveOperation() {
        Operation saved = operationRepository.saveAndFlush(Operation.builder().id(3).build());
        Operation fromDb = operationRepository.getById(3);
        assertEquals(saved, fromDb);
    }

    @Test
    void shouldGetOperationById() {
        operation = operationRepository.getById(1);
        assertThat(operationRepository.getById(operation.getId())).isEqualTo(operation);
    }

    @Test
    void shouldGetAllOperations() {
        List<Operation> operations = operationRepository.findAll();
        assertNotNull(operations);
        assertThat(operations).hasSize(2);
    }

    @Test
    void shouldEditOperation() {
        Operation newOperation = Operation.builder()
                .id(1)
                .description("New description of operation")
                .build();
        operationRepository.saveAndFlush(newOperation);
        Operation editedOperation = operationRepository.getById(1);
        assertEquals("New description of operation", editedOperation.getDescription());
    }

    @Test
    void shouldDeleteOperation() {
        operationRepository.deleteById(1);
        List<Operation> operations = operationRepository.findAll();
        assertThat(operations).hasSize(1);
    }

}