package ua.zakharvalko.springbootdemo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.zakharvalko.springbootdemo.dao.GroupOfCategoryRepository;
import ua.zakharvalko.springbootdemo.domain.GroupOfCategories;
import ua.zakharvalko.springbootdemo.service.GroupOfCategoryService;

import java.util.List;

@Service
public class GroupOfCategoryServiceImpl implements GroupOfCategoryService {

    @Autowired
    private GroupOfCategoryRepository groupRepository;

    @Override
    public GroupOfCategories addGroup(GroupOfCategories group) {
        return groupRepository.saveAndFlush(group);
    }

    @Override
    public void deleteGroup(Integer id) {
        groupRepository.deleteById(id);
    }

    @Override
    public GroupOfCategories getById(Integer id) {
        return groupRepository.getById(id);
    }

    @Override
    public GroupOfCategories editGroup(GroupOfCategories group) {
        return groupRepository.saveAndFlush(group);
    }

    @Override
    public List<GroupOfCategories> getAllGroups() {
        return groupRepository.findAll();
    }
}
