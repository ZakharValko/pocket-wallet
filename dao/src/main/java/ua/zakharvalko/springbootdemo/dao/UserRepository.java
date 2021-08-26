package ua.zakharvalko.springbootdemo.dao;

import org.apache.ibatis.annotations.*;
import ua.zakharvalko.springbootdemo.domain.User;

import java.util.List;

@Mapper
public interface UserRepository extends AbstractRepository<User> {

    @Insert("INSERT INTO user(first_name, second_name, username, password, role_id) VALUES (#{first_name}, #{second_name}, #{username}, #{password}, #{role_id})")
    void save(User user);

    @Update("UPDATE user set first_name=#{first_name}, second_name=#{second_name}, username=#{username}, password=#{password} WHERE id=#{id}")
    void update(User user);

    @Delete("DELETE FROM user WHERE id=#{id}")
    void delete(Integer id);

    @Select("SELECT * FROM user WHERE id=#{id}")
    User getById(Integer id);

    @Select("SELECT * FROM user")
    List<User> getAll();

    @Select("SELECT * FROM user WHERE username=#{username}")
    User findByUsername(String username);

}
