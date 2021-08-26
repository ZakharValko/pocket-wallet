package ua.zakharvalko.springbootdemo.dao;

import org.apache.ibatis.annotations.*;
import ua.zakharvalko.springbootdemo.domain.Role;

import java.util.List;

@Mapper
public interface RoleRepository extends AbstractRepository<Role> {

    @Insert("INSERT INTO role(title) VALUES (#{title})")
    void save(Role role);

    @Update("UPDATE role set title=#{title} WHERE id=#{id}")
    void update(Role role);

    @Delete("DELETE FROM role WHERE id=#{id}")
    void delete(Integer id);

    @Select("SELECT * FROM role WHERE id=#{id}")
    Role getById(Integer id);

    @Select("SELECT * FROM role")
    List<Role> getAll();

}
