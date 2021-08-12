package ua.zakharvalko.springbootdemo.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import ua.zakharvalko.springbootdemo.domain.GroupOfCategories;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class GroupOfCategoryRepositoryTest {

    @Autowired
    private GroupOfCategoryRepository groupRepository;

    private GroupOfCategories group;

    @BeforeEach
    void setUp() {
        List<GroupOfCategories> groups = new ArrayList<>();
        GroupOfCategories first = GroupOfCategories.builder()
                .id(1)
                .title("Food&Drinks")
                .build();
        GroupOfCategories second = GroupOfCategories.builder()
                .id(2)
                .title("Shopping")
                .build();
        groups.add(first);
        groups.add(second);

        groupRepository.saveAll(groups);
    }

    @Test
    void shouldSaveGroup() {
        GroupOfCategories saved = groupRepository.saveAndFlush(GroupOfCategories.builder().id(3).build());
        GroupOfCategories fromDb = groupRepository.getById(3);
        assertEquals(saved, fromDb);
    }

    @Test
    void shouldGetGroupById() {
        group = groupRepository.getById(1);
        assertThat(groupRepository.getById(group.getId())).isEqualTo(group);
    }

    @Test
    void shouldGetAllGroups() {
        List<GroupOfCategories> groups = groupRepository.findAll();
        assertNotNull(groups);
        assertThat(groups).hasSize(2);
    }

    @Test
    void shouldEditGroup() {
        GroupOfCategories newGroup = GroupOfCategories.builder()
                .id(1)
                .title("Food")
                .build();
        groupRepository.saveAndFlush(newGroup);
        GroupOfCategories editedGroup = groupRepository.getById(1);
        assertEquals("Food", editedGroup.getTitle());
    }

    @Test
    void shouldDeleteGroup() {
        groupRepository.deleteById(1);
        List<GroupOfCategories> groups = groupRepository.findAll();
        assertThat(groups).hasSize(1);
    }
}