package ua.zakharvalko.springbootdemo.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import ua.zakharvalko.springbootdemo.domain.OperationType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@MybatisTest
class OperationTypeRepositoryTest {

    @Autowired
    private OperationTypeRepository typeRepository;

    private OperationType type;

    @BeforeEach
    void setUp() {
        OperationType first = OperationType.builder()
                .id(1)
                .title("Transfer")
                .build();
        OperationType second = OperationType.builder()
                .id(2)
                .title("Income")
                .build();

        typeRepository.save(first);
        typeRepository.save(second);
    }

    @Test
    void shouldSaveType() {
        OperationType saved = OperationType.builder().id(3).build();
        typeRepository.save(saved);
        OperationType fromDb = typeRepository.getById(3);
        assertEquals(saved.getId(), fromDb.getId());
    }

    @Test
    void shouldGetTypeById() {
        type = typeRepository.getById(1);
        assertThat(typeRepository.getById(type.getId())).isEqualTo(type);
    }

    @Test
    void shouldGetAllTypes() {
        List<OperationType> types = typeRepository.getAll();
        assertNotNull(types);
        assertThat(types).hasSize(2);
    }

    @Test
    void shouldEditType() {
        OperationType newType = OperationType.builder()
                .id(1)
                .title("Transfers")
                .build();
        typeRepository.update(newType);
        OperationType editedType = typeRepository.getById(1);
        assertEquals("Transfers", editedType.getTitle());
    }

    @Test
    void shouldDeleteType() {
        typeRepository.delete(1);
        List<OperationType> types = typeRepository.getAll();
        assertThat(types).hasSize(1);
    }

}