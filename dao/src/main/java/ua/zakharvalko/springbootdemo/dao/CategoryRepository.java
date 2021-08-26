package ua.zakharvalko.springbootdemo.dao;

import org.apache.ibatis.annotations.*;
import ua.zakharvalko.springbootdemo.domain.Category;

import java.util.List;

@Mapper
public interface CategoryRepository extends AbstractRepository<Category> {

    @Insert("INSERT INTO category(title, group_id) VALUES (#{title}, #{group_id})")
    void save(Category category);

    @Update("UPDATE category set title=#{title}, group_id=#{group_id}")
    void update(Category category);

    @Delete("DELETE FROM category WHERE id=#{id}")
    void delete(Integer id);

    @Select("SELECT * FROM category WHERE id=#{id}")
    Category getById(Integer id);

    @Select("SELECT * FROM category")
    List<Category> getAll();

}
