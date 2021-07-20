package ua.zakharvalko.service.impl;

import org.springframework.stereotype.Service;
import ua.zakharvalko.dao.GroupOfCategoryRepository;
import ua.zakharvalko.domain.group.GroupOfCategories;
import ua.zakharvalko.service.GroupOfCategoryService;

import java.util.List;

@Service
public class GroupOfCategoryServiceImpl implements GroupOfCategoryService {

    private GroupOfCategoryRepository categoryRepository;

    @Override
    public GroupOfCategories addGroup(GroupOfCategories group) {
        return categoryRepository.saveAndFlush(group);
    }

    @Override
    public void deleteGroup(Integer id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public GroupOfCategories getById(Integer id) {
        return categoryRepository.getById(id);
    }

    @Override
    public List<GroupOfCategories> getAllGroups() {
        return categoryRepository.findAll();
    }
}
