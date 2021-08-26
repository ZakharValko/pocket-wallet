package ua.zakharvalko.springbootdemo.dao;

import org.apache.ibatis.annotations.*;
import ua.zakharvalko.springbootdemo.domain.GroupOfCategories;

import java.util.List;

@Mapper
public interface GroupOfCategoryRepository extends AbstractRepository<GroupOfCategories> {

    @Insert("INSERT INTO group_of_categories (title) VALUES (#{title})")
    void save(GroupOfCategories group);

    @Update("UPDATE group_of_categories set title=#{title} WHERE id=#{id}")
    void update(GroupOfCategories group);

    @Delete("DELETE FROM group_of_categories WHERE id=#{id}")
    void delete(Integer id);

    @Select("SELECT * FROM group_of_categories WHERE id=#{id}")
    GroupOfCategories getById(Integer id);

    @Select("SELECT * FROM group_of_categories")
    List<GroupOfCategories> getAll();

}
