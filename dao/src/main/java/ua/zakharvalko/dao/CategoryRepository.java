package ua.zakharvalko.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.zakharvalko.domain.category.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
