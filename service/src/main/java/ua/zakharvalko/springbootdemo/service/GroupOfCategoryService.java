package ua.zakharvalko.springbootdemo.service;


import ua.zakharvalko.springbootdemo.domain.GroupOfCategories;

import java.util.List;

public interface GroupOfCategoryService {

    GroupOfCategories addGroup(GroupOfCategories group);
    void deleteGroup(Integer id);
    GroupOfCategories editGroup(GroupOfCategories group);
    GroupOfCategories getById(Integer id);
    List<GroupOfCategories> getAllGroups();

}
