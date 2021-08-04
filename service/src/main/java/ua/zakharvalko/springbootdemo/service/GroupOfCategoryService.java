package ua.zakharvalko.springbootdemo.service;


import ua.zakharvalko.springbootdemo.domain.GroupOfCategories;

import java.util.List;

public interface GroupOfCategoryService {

    GroupOfCategories saveOrUpdate(GroupOfCategories group);
    void delete(Integer id);
    GroupOfCategories getById(Integer id);
    List<GroupOfCategories> getAll();

}
