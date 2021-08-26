package ua.zakharvalko.springbootdemo.dao;

import org.apache.ibatis.annotations.*;
import ua.zakharvalko.springbootdemo.dao.querybuilder.SqlForFilter;
import ua.zakharvalko.springbootdemo.domain.Operation;
import ua.zakharvalko.springbootdemo.domain.filter.OperationFilter;

import java.util.List;

@Mapper
public interface OperationRepository extends AbstractRepository<Operation> {

    @Insert("INSERT INTO operation(description, date, price, operation_type_id, category_id, account_id, transfer_to, total_for_transfer) VALUES (#{description}, #{date}, #{price}, #{operation_type_id}, #{category_id},  #{account_id},  #{transfer_to},  #{total_for_transfer})")
    void save(Operation operation);

    @Update("UPDATE operation set description=#{description}, date=#{date}, price=#{price}, operation_type_id=#{operation_type_id}, category_id=#{category_id}, account_id=#{account_id}, transfer_to=#{transfer_to}, total_for_transfer=#{total_for_transfer} WHERE id=#{id}")
    void update(Operation operation);

    @Delete("DELETE FROM operation WHERE id=#{id}")
    void delete(Integer id);

    @Select("SELECT * FROM operation WHERE id=#{id}")
    Operation getById(Integer id);

    @Select("SELECT * FROM operation")
    List<Operation> getAll();

    @SelectProvider(type = SqlForFilter.class, method = "getAllByFilterQuery")
    List<Operation> getAllByFilter(@Param(value = "filter") OperationFilter filter);

}
