package ua.zakharvalko.springbootdemo.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ua.zakharvalko.springbootdemo.dao.OperationRepository;
import ua.zakharvalko.springbootdemo.domain.*;

import ua.zakharvalko.springbootdemo.domain.filter.OperationFilter;
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
                .account_id(1)
                .operation_type_id(1)
                .category_id(1)
                .build();

        operationRepository.save(operation);
        when(operationRepository.getById(1)).thenReturn(operation);
        Operation actual = operationService.getById(operation.getId());

        assertEquals(operation.getId(), actual.getId());
        verify(operationRepository).save(operation);
    }

    @Test
    public void shouldDeleteOperation() {
        Operation operation = Operation.builder().id(1).build();

        when(operationRepository.getById(operation.getId())).thenReturn(operation);
        operationService.delete(operation.getId());
        verify(operationRepository).delete(operation.getId());
    }

    @Test
    public void shouldEditOperation() {
        Operation oldOperation = Operation.builder().id(1).price(100L).build();
        Operation newOperation = Operation.builder().id(1).price(150L).build();

        given(operationRepository.getById(oldOperation.getId())).willReturn(newOperation);
        operationService.update(newOperation);

        verify(operationRepository).update(newOperation);
    }

    @Test
    public void shouldReturnOperationById() {
        Operation operation = Operation.builder().id(1)
                .description("description")
                .date(new Date(5000))
                .price(200L)
                .account_id(1)
                .operation_type_id(1)
                .category_id(1)
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
        when(operationRepository.getAll()).thenReturn(operations);

        List<Operation> actual = operationService.getAll();

        assertEquals(operations, actual);
        verify(operationRepository).getAll();
    }

    @Test
    public void shouldReturnFilteredOperations() {
        List<Operation> operations = new ArrayList<>();
        operations.add(Operation.builder().id(1)
                .category_id(2)
                .operation_type_id(1)
                .date(parseDate("2021-05-05T13:19:49"))
                .account_id(1)
                .build());
        operations.add(Operation.builder().id(2)
                .category_id(1)
                .operation_type_id(1)
                .date(parseDate("2021-05-08T13:19:49"))
                .account_id(1)
                .build());
        operations.add(Operation.builder().id(3)
                .category_id(1)
                .operation_type_id(1)
                .date(parseDate("2021-05-10T13:19:49"))
                .account_id(1)
                .build());

        OperationFilter filter = new OperationFilter();

        when(operationRepository.getAllByFilter(any(OperationFilter.class))).thenReturn(operations);
        List<Operation> actual = operationService.getOperationsByFilter(filter);
        assertEquals(operations, actual);
    }

    @Test
    public void shouldReturnExpensesByFilteredOperations() {
        List<Operation> operations = new ArrayList<>();
        operations.add(Operation.builder().id(1)
                .category_id(2)
                .operation_type_id(1)
                .total_for_transfer(1000L)
                .date(parseDate("2021-05-05T13:19:49"))
                .account_id(1)
                .build());
        operations.add(Operation.builder().id(2)
                .category_id(1)
                .operation_type_id(1)
                .total_for_transfer(1000L)
                .date(parseDate("2021-05-08T13:19:49"))
                .account_id(1)
                .build());

        OperationFilter filter = new OperationFilter();
        filter.setOperation_type_id(1);

        when(operationRepository.getAllByFilter(any(OperationFilter.class))).thenReturn(operations);
        double actual = operationService.getTotalExpensesByFilter(filter);
        assertEquals(20.0, actual);

    }

    @Test
    void shouldMakeTransferBetweenAccounts() {
        Operation operation = Operation.builder()
                .id(1)
                .total_for_transfer(1000L)
                .operation_type_id(1)
                .account_id(1)
                .transfer_to(2)
                .build();

        Operation back = Operation.builder()
                .description("Transfer from: " + operation.getAccount_id())
                .date(new Date())
                .price(operation.getTotal_for_transfer())
                .account_id(operation.getTransfer_to())
                .operation_type_id(2)
                .build();

        List<Operation> operations = new ArrayList<>();
        operationRepository.save(operation);
        operationRepository.save(back);
        operations.add(operation);
        operations.add(back);

        List<Operation> actual = operationService.transferBetweenAccounts(operation);

        assertEquals(operations.get(0).getId(), actual.get(0).getId());
    }

    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
}