package ua.zakharvalko.springbootdemo.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ua.zakharvalko.springbootdemo.dao.OperationRepository;
import ua.zakharvalko.springbootdemo.domain.*;
import ua.zakharvalko.springbootdemo.domain.specification.OperationFilter;
import ua.zakharvalko.springbootdemo.domain.specification.OperationSpecifications;
import ua.zakharvalko.springbootdemo.service.OperationService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = OperationServiceImpl.class)
@RunWith(SpringRunner.class)
class OperationServiceImplTest {

    @MockBean
    private OperationRepository operationRepository;

    @Autowired
    private OperationService operationService;

    @Test
    public void shouldReturnOperationsWhenAdded() {
        Operation operation = Operation.builder().id(1)
                .description("description")
                .date(new Date(5000))
                .price(200L)
                .account(Account.builder().id(1).currency(Currency.builder().id(1).build()).build())
                .operationType(OperationType.builder().id(2).build())
                .category(Category.builder().id(1).group(GroupOfCategories.builder().id(1).build()).build())
                .build();

        when(operationRepository.saveAndFlush(operation)).thenReturn(operation);
        Operation added = operationService.saveOrUpdate(operation);

        assertEquals(operation.getId(), added.getId());
        verify(operationRepository).saveAndFlush(operation);
    }

    @Test
    public void shouldDeleteOperation() {
        Operation operation = Operation.builder().id(1).build();

        when(operationRepository.getById(operation.getId())).thenReturn(operation);
        operationService.delete(operation.getId());
        verify(operationRepository).deleteById(operation.getId());
    }

    @Test
    public void shouldEditOperation() {
        Operation oldOperation = Operation.builder().id(1).price(100L).build();
        Operation newOperation = Operation.builder().id(1).price(150L).build();

        given(operationRepository.getById(oldOperation.getId())).willReturn(newOperation);
        operationService.saveOrUpdate(newOperation);

        verify(operationRepository).saveAndFlush(newOperation);
    }

    @Test
    public void shouldReturnOperationById() {
        Operation operation = Operation.builder().id(1)
                .description("description")
                .date(new Date(5000))
                .price(200L)
                .account(Account.builder().id(1).currency(Currency.builder().id(1).build()).build())
                .operationType(OperationType.builder().id(1).build())
                .category(Category.builder().id(1).group(GroupOfCategories.builder().id(1).build()).build())
                .build();
        when(operationRepository.getById(1)).thenReturn(operation);

        Operation actual = operationService.getById(1);

        assertEquals(operation.getId(), actual.getId());
        verify(operationRepository).getById(1);
    }

    @Test
    public void shouldReturnAllOperations() {
        List<Operation> operations = new ArrayList<>();
        operations.add(new Operation());
        when(operationRepository.findAll()).thenReturn(operations);

        List<Operation> actual = operationService.getAll();

        assertEquals(operations, actual);
        verify(operationRepository).findAll();
    }

    @Test
    public void shouldReturnFilteredOperations() {
        List<Operation> operations = new ArrayList<>();
        operations.add(Operation.builder().id(1)
                .category(Category.builder().id(2).build())
                .operationType(OperationType.builder().id(1).build())
                .date(parseDate("2021-05-05T13:19:49"))
                .account(Account.builder().currency(Currency.builder().id(1).build()).build())
                .build());
        operations.add(Operation.builder().id(2)
                .category(Category.builder().id(1).build())
                .operationType(OperationType.builder().id(1).build())
                .date(parseDate("2021-05-08T13:19:49"))
                .account(Account.builder().currency(Currency.builder().id(1).build()).build())
                .build());
        operations.add(Operation.builder().id(3)
                .category(Category.builder().id(1).build())
                .operationType(OperationType.builder().id(1).build())
                .date(parseDate("2021-05-10T13:19:49"))
                .account(Account.builder().currency(Currency.builder().id(1).build()).build())
                .build());

        OperationFilter filter = new OperationFilter();

        when(operationRepository.findAll(any(OperationSpecifications.class))).thenReturn(operations);
        List<Operation> actual = operationService.getOperationsByFilter(filter);
        assertEquals(operations, actual);
    }

    @Test
    public void shouldReturnExpensesByFilteredOperations() {
        List<Operation> operations = new ArrayList<>();
        operations.add(Operation.builder().id(1)
                .category(Category.builder().id(2).build())
                .operationType(OperationType.builder().id(1).build())
                .totalForTransfer(1000L)
                .date(parseDate("2021-05-05T13:19:49"))
                .account(Account.builder().currency(Currency.builder().id(1).build()).build())
                .build());
        operations.add(Operation.builder().id(2)
                .category(Category.builder().id(1).build())
                .operationType(OperationType.builder().id(1).build())
                .totalForTransfer(1000L)
                .date(parseDate("2021-05-08T13:19:49"))
                .account(Account.builder().currency(Currency.builder().id(1).build()).build())
                .build());

        OperationFilter filter = new OperationFilter();
        filter.setOperationType(OperationType.builder().id(1).build());

        when(operationRepository.findAll(any(OperationSpecifications.class))).thenReturn(operations);
        double actual = operationService.getTotalExpensesByFilter(filter);
        assertEquals(20.0, actual);

    }

    @Test
    void shouldMakeTransferBetweenAccounts() {
        Operation operation = Operation.builder()
                .id(1)
                .totalForTransfer(1000L)
                .operationType(OperationType.builder().id(1).build())
                .account(Account.builder().id(1).build())
                .transferTo(Account.builder().id(2).build())
                .build();

        Operation back = Operation.builder()
                .description("Transfer from: " + operation.getAccount().getId())
                .date(new Date())
                .price(operation.getTotalForTransfer())
                .account(operation.getTransferTo())
                .operationType(OperationType.builder().id(2).build())
                .build();

        List<Operation> operations = new ArrayList<>();
        when(operationRepository.saveAndFlush(operation)).thenReturn(operation);
        when(operationRepository.saveAndFlush(back)).thenReturn(back);
        operations.add(operation);
        operations.add(back);

        List<Operation> actual = operationService.transferBetweenAccounts(operation);

        assertEquals(operations, actual);
        verify(operationRepository).saveAndFlush(operation);
    }

    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
}