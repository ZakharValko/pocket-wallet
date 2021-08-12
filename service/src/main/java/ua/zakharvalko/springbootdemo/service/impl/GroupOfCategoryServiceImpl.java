package ua.zakharvalko.springbootdemo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.zakharvalko.springbootdemo.dao.GroupOfCategoryRepository;
import ua.zakharvalko.springbootdemo.domain.GroupOfCategories;
import ua.zakharvalko.springbootdemo.service.GroupOfCategoryService;

@Service
public class GroupOfCategoryServiceImpl extends AbstractServiceImpl<GroupOfCategories, GroupOfCategoryRepository> implements GroupOfCategoryService {

    @Autowired
    private GroupOfCategoryRepository groupRepository;

    public GroupOfCategoryServiceImpl(GroupOfCategoryRepository repository) {
        super(repository);
    }
}
