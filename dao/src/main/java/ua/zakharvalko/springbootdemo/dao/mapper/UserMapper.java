package ua.zakharvalko.springbootdemo.dao.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;
import ua.zakharvalko.springbootdemo.domain.User;

import java.util.List;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO users(first_name, second_name, username, password, role_id) VALUES")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Integer.class)
    User saveOrUpdate(User user);

    void delete(Integer id);
    User getById(Integer id);
    List<User> getAll();

}
