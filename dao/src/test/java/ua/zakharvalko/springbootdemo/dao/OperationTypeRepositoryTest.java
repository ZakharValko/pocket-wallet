package ua.zakharvalko.springbootdemo.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import ua.zakharvalko.springbootdemo.domain.OperationType;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class OperationTypeRepositoryTest {

    @Autowired
    private OperationTypeRepository typeRepository;

    private OperationType type;

    @BeforeEach
    void setUp() {
        List<OperationType> types = new ArrayList<>();
        OperationType first = OperationType.builder()
                .id(1)
                .title("Transfer")
                .build();
        OperationType second = OperationType.builder()
                .id(2)
                .title("Income")
                .build();
        types.add(first);
        types.add(second);

        typeRepository.saveAll(types);
    }

    @Test
    void shouldSaveType() {
        OperationType saved = typeRepository.saveAndFlush(OperationType.builder().id(3).build());
        OperationType fromDb = typeRepository.getById(3);
        assertEquals(saved, fromDb);
    }

    @Test
    void shouldGetTypeById() {
        type = typeRepository.getById(1);
        assertThat(typeRepository.getById(type.getId())).isEqualTo(type);
    }

    @Test
    void shouldGetAllTypes() {
        List<OperationType> types = typeRepository.findAll();
        assertNotNull(types);
        assertThat(types).hasSize(2);
    }

    @Test
    void shouldEditType() {
        OperationType newType = OperationType.builder()
                .id(1)
                .title("Transfers")
                .build();
        typeRepository.saveAndFlush(newType);
        OperationType editedType = typeRepository.getById(1);
        assertEquals("Transfers", editedType.getTitle());
    }

    @Test
    void shouldDeleteType() {
        typeRepository.deleteById(1);
        List<OperationType> types = typeRepository.findAll();
        assertThat(types).hasSize(1);
    }

}