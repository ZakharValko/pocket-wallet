package ua.zakharvalko.springbootdemo.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import ua.zakharvalko.springbootdemo.domain.GroupOfCategories;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@MybatisTest
class GroupOfCategoryRepositoryTest {

    @Autowired
    private GroupOfCategoryRepository groupRepository;

    private GroupOfCategories group;

    @BeforeEach
    void setUp() {
        GroupOfCategories first = GroupOfCategories.builder()
                .id(1)
                .title("Food&Drinks")
                .build();
        GroupOfCategories second = GroupOfCategories.builder()
                .id(2)
                .title("Shopping")
                .build();

        groupRepository.save(first);
        groupRepository.save(second);
    }

    @Test
    void shouldSaveGroup() {
        GroupOfCategories saved = GroupOfCategories.builder().id(3).build();
        groupRepository.save(saved);
        GroupOfCategories fromDb = groupRepository.getById(3);
        assertEquals(saved.getId(), fromDb.getId());
    }

    @Test
    void shouldGetGroupById() {
        group = groupRepository.getById(1);
        assertThat(groupRepository.getById(group.getId())).isEqualTo(group);
    }

    @Test
    void shouldGetAllGroups() {
        List<GroupOfCategories> groups = groupRepository.getAll();
        assertNotNull(groups);
        assertThat(groups).hasSize(2);
    }

    @Test
    void shouldEditGroup() {
        GroupOfCategories newGroup = GroupOfCategories.builder()
                .id(1)
                .title("Food")
                .build();
        groupRepository.update(newGroup);
        GroupOfCategories editedGroup = groupRepository.getById(1);
        assertEquals("Food", editedGroup.getTitle());
    }

    @Test
    void shouldDeleteGroup() {
        groupRepository.delete(1);
        List<GroupOfCategories> groups = groupRepository.getAll();
        assertThat(groups).hasSize(1);
    }
}