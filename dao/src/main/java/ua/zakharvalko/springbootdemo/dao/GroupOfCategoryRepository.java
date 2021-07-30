package ua.zakharvalko.springbootdemo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.zakharvalko.springbootdemo.domain.GroupOfCategories;

@Repository
public interface GroupOfCategoryRepository extends JpaRepository<GroupOfCategories, Integer> {
}
