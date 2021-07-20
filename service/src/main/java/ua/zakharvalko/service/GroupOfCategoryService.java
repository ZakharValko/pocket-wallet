package ua.zakharvalko.service;


import ua.zakharvalko.domain.group.GroupOfCategories;

import java.util.List;

public interface GroupOfCategoryService {

    GroupOfCategories addGroup(GroupOfCategories group);
    void deleteGroup(Integer id);
    GroupOfCategories getById(Integer id);
    List<GroupOfCategories> getAllGroups();

}
