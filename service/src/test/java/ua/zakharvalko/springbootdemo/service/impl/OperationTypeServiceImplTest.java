package ua.zakharvalko.springbootdemo.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ua.zakharvalko.springbootdemo.dao.OperationTypeRepository;
import ua.zakharvalko.springbootdemo.domain.OperationType;
import ua.zakharvalko.springbootdemo.service.OperationTypeService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = OperationTypeServiceImpl.class)
@RunWith(SpringRunner.class)
class OperationTypeServiceImplTest {

    @MockBean
    private OperationTypeRepository operationTypeRepository;

    @Autowired
    private OperationTypeService operationTypeService;

    @Test
    void shouldReturnTypeWhenSaved() {
        OperationType type = OperationType.builder().id(1).build();

        operationTypeRepository.save(type);
        when(operationTypeRepository.getById(1)).thenReturn(type);
        OperationType actual = operationTypeService.getById(type.getId());

        assertEquals(type.getId(), actual.getId());
        verify(operationTypeRepository).save(type);
    }

    @Test
    void shouldDeleteType() {
        OperationType type = OperationType.builder().id(1).build();

        when(operationTypeRepository.getById(type.getId())).thenReturn(type);
        operationTypeService.delete(type.getId());
        verify(operationTypeRepository).delete(type.getId());
    }

    @Test
    void shouldEditType() {
        OperationType oldType = OperationType.builder().id(1).build();
        oldType.setTitle("Old");
        OperationType newType = OperationType.builder().id(1).build();
        newType.setTitle("New");

        given(operationTypeRepository.getById(oldType.getId())).willReturn(oldType);
        operationTypeService.update(newType);

        verify(operationTypeRepository).update(newType);
    }

    @Test
    void shouldReturnTypeById() {
        OperationType type = OperationType.builder().id(1).build();
        when(operationTypeRepository.getById(1)).thenReturn(type);

        OperationType actual = operationTypeService.getById(1);

        assertEquals(type.getId(), actual.getId());
        verify(operationTypeRepository).getById(1);
    }

    @Test
    void shouldReturnAllTypes() {
        List<OperationType> types = new ArrayList<>();
        types.add(new OperationType());
        when(operationTypeRepository.getAll()).thenReturn(types);

        List<OperationType> actual = operationTypeService.getAll();

        assertEquals(types, actual);
        verify(operationTypeRepository).getAll();
    }
}