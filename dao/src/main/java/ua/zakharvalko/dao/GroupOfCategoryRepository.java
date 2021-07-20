package ua.zakharvalko.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.zakharvalko.domain.group.GroupOfCategories;

public interface GroupOfCategoryRepository extends JpaRepository<GroupOfCategories, Integer> {
}
