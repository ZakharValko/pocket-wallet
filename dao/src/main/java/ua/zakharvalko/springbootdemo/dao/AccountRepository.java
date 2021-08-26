package ua.zakharvalko.springbootdemo.dao;

import org.apache.ibatis.annotations.*;
import ua.zakharvalko.springbootdemo.domain.Account;

import java.util.List;

@Mapper
public interface AccountRepository extends AbstractRepository<Account> {

    @Insert("INSERT INTO account(number, balance, user_id, currency_id) VALUES (#{number}, #{balance}, #{user_id}, #{currency_id})")
    void save(Account account);

    @Update("UPDATE account set number=#{number}, balance=#{balance}, user_id=#{user_id}, currency_id=#{currency_id} WHERE id=#{id}")
    void update(Account account);

    @Delete("DELETE FROM account WHERE id=#{id}")
    void delete(Integer id);

    @Select("SELECT * FROM account WHERE id=#{id}")
    Account getById(Integer id);

    @Select("SELECT * FROM account")
    List<Account> getAll();

}
