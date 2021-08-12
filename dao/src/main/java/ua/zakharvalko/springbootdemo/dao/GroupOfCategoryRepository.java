package ua.zakharvalko.springbootdemo.dao;

import org.springframework.stereotype.Repository;
import ua.zakharvalko.springbootdemo.domain.GroupOfCategories;

@Repository
public interface GroupOfCategoryRepository extends AbstractRepository<GroupOfCategories> {
}
