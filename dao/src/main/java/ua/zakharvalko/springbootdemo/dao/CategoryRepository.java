package ua.zakharvalko.springbootdemo.dao;

import org.springframework.stereotype.Repository;
import ua.zakharvalko.springbootdemo.domain.Category;

@Repository
public interface CategoryRepository extends AbstractRepository<Category> {
}
