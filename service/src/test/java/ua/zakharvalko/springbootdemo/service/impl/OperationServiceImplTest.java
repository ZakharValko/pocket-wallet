package ua.zakharvalko.springbootdemo.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ua.zakharvalko.springbootdemo.dao.OperationRepository;
import ua.zakharvalko.springbootdemo.domain.Account;
import ua.zakharvalko.springbootdemo.domain.Category;
import ua.zakharvalko.springbootdemo.domain.Currency;
import ua.zakharvalko.springbootdemo.domain.GroupOfCategories;
import ua.zakharvalko.springbootdemo.domain.Operation;
import ua.zakharvalko.springbootdemo.domain.OperationType;
import ua.zakharvalko.springbootdemo.service.OperationService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
                .operationType(OperationType.builder().id(1).build())
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

    /*@Test
    public void shouldReturnFilteredOperations() {
        Operation first = Operation.builder().id(1)
                .description("description")
                .date(new Date(5000))
                .price(200L)
                .account(Account.builder().id(1).currency(Currency.builder().id(1).build()).build())
                .operationType(OperationType.builder().id(1).build())
                .category(Category.builder().id(1).group(GroupOfCategories.builder().id(1).build()).build())
                .build();

        Operation second = Operation.builder().id(2)
                .description("description")
                .date(new Date(10000))
                .price(100L)
                .account(Account.builder().id(2).currency(Currency.builder().id(2).build()).build())
                .operationType(OperationType.builder().id(2).build())
                .category(Category.builder().id(2).group(GroupOfCategories.builder().id(2).build()).build())
                .build();

        when(operationRepository.findAll()).thenReturn(Arrays.asList(first, second));
        List<Operation> actual = operationService.getOperationsByFilter(1, 1, 1, 1, 1, new Date(4000), new Date(5500));
        List<Operation> expected = new ArrayList<>();
        expected.add(first);

        assertEquals(expected, actual);
        verify(operationRepository).findAll();
    }

    @Test
    public void shouldReturnExpensesByFilteredOperations() {
        Operation first = Operation.builder().id(1)
                .description("description")
                .date(new Date(5000))
                .price(200L)
                .account(Account.builder().id(1).currency(Currency.builder().id(1).build()).build())
                .operationType(OperationType.builder().id(1).build())
                .category(Category.builder().id(1).group(GroupOfCategories.builder().id(1).build()).build())
                .build();

        Operation second = Operation.builder().id(2)
                .description("description")
                .date(new Date(10000))
                .price(100L)
                .account(Account.builder().id(1).currency(Currency.builder().id(2).build()).build())
                .operationType(OperationType.builder().id(3).build())
                .category(Category.builder().id(2).group(GroupOfCategories.builder().id(2).build()).build())
                .build();

        when(operationRepository.findAll()).thenReturn(Arrays.asList(first, second));
        Long actual = operationService.getTotalExpensesByFilter(1, 2, 2, 2, new Date(6000), new Date(11000));

        assertEquals(100, actual);
        verify(operationRepository).findAll();
    }

    @Test
    public void shouldReturnCashFlowByInterval() {
        Operation first = Operation.builder().id(1)
                .description("description")
                .date(new Date(5000))
                .price(100L)
                .account(Account.builder().id(1).currency(Currency.builder().id(1).build()).build())
                .operationType(OperationType.builder().id(1).build())
                .category(Category.builder().id(1).group(GroupOfCategories.builder().id(1).build()).build())
                .build();

        Operation second = Operation.builder().id(2)
                .description("description")
                .date(new Date(10000))
                .price(300L)
                .account(Account.builder().id(1).currency(Currency.builder().id(2).build()).build())
                .operationType(OperationType.builder().id(2).build())
                .category(Category.builder().id(2).group(GroupOfCategories.builder().id(2).build()).build())
                .build();

        Operation third = Operation.builder().id(3)
                .description("description")
                .date(new Date(12000))
                .price(300L)
                .account(Account.builder().id(1).currency(Currency.builder().id(2).build()).build())
                .operationType(OperationType.builder().id(2).build())
                .category(Category.builder().id(2).group(GroupOfCategories.builder().id(2).build()).build())
                .build();

        when(operationRepository.findAll()).thenReturn(Arrays.asList(first, second, third));
        Long actual = operationService.getCashFlow(1, new Date(4000), new Date(11000));

        assertEquals(200, actual);
        verify(operationRepository).findAll();
    }*/

    @Test
    void shouldMakeTransferBetweenAccounts() {

    }
}