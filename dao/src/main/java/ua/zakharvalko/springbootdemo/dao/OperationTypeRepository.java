package ua.zakharvalko.springbootdemo.dao;

import org.apache.ibatis.annotations.*;
import ua.zakharvalko.springbootdemo.domain.OperationType;

import java.util.List;

@Mapper
public interface OperationTypeRepository extends AbstractRepository<OperationType> {

    @Insert("INSERT INTO operation_type(title) VALUES (#{title})")
    void save(OperationType type);

    @Update("UPDATE operation_type set title=#{title} WHERE id=#{id}")
    void update(OperationType type);

    @Delete("DELETE FROM operation_type WHERE id=#{id}")
    void delete(Integer id);

    @Select("SELECT * FROM operation_type WHERE id=#{id}")
    OperationType getById(Integer id);

    @Select("SELECT * FROM operation_type")
    List<OperationType> getAll();

}
