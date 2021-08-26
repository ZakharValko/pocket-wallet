package ua.zakharvalko.springbootdemo.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ua.zakharvalko.springbootdemo.dao.GroupOfCategoryRepository;
import ua.zakharvalko.springbootdemo.domain.GroupOfCategories;
import ua.zakharvalko.springbootdemo.service.GroupOfCategoryService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = GroupOfCategoryServiceImpl.class)
@RunWith(SpringRunner.class)
class GroupOfCategoryServiceImplTest {

    @MockBean
    private GroupOfCategoryRepository groupRepository;

    @Autowired
    private GroupOfCategoryService groupService;

    @Test
    void shouldReturnGroupWhenSaved() {
        GroupOfCategories group = GroupOfCategories.builder().id(1).build();

        groupRepository.save(group);
        when(groupRepository.getById(1)).thenReturn(group);
        GroupOfCategories actual = groupService.getById(group.getId());

        assertEquals(group.getId(), actual.getId());
        verify(groupRepository).save(group);
    }

    @Test
    void shouldDeleteGroup() {
        GroupOfCategories group = GroupOfCategories.builder().id(1).build();

        when(groupRepository.getById(group.getId())).thenReturn(group);
        groupService.delete(group.getId());
        verify(groupRepository).delete(group.getId());
    }

    @Test
    void shouldEditGroup() {
        GroupOfCategories oldGroup = GroupOfCategories.builder().id(1).title("Old").build();
        GroupOfCategories newGroup = GroupOfCategories.builder().id(1).title("New").build();

        given(groupRepository.getById(oldGroup.getId())).willReturn(newGroup);
        groupService.update(newGroup);

        verify(groupRepository).update(newGroup);
    }

    @Test
    void shouldReturnGroupById() {
        GroupOfCategories group = GroupOfCategories.builder().id(1).build();
        when(groupRepository.getById(1)).thenReturn(group);

        GroupOfCategories actual = groupService.getById(1);

        assertEquals(group.getId(), actual.getId());
        verify(groupRepository).getById(1);
    }

    @Test
    void shouldReturnAllGroups() {
        List<GroupOfCategories> groups = new ArrayList<>();
        groups.add(new GroupOfCategories());
        when(groupRepository.getAll()).thenReturn(groups);

        List<GroupOfCategories> actual = groupService.getAll();

        assertEquals(groups, actual);
        verify(groupRepository).getAll();
    }
}