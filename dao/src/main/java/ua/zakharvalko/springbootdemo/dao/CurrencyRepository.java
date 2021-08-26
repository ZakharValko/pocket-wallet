package ua.zakharvalko.springbootdemo.dao;

import org.apache.ibatis.annotations.*;
import ua.zakharvalko.springbootdemo.domain.Currency;

import java.util.List;

@Mapper
public interface CurrencyRepository extends AbstractRepository<Currency> {

    @Insert("INSERT INTO currency(title) VALUES (#{title})")
    void save(Currency currency);

    @Update("UPDATE currency set title=#{title} WHERE id=#{id}")
    void update(Currency currency);

    @Delete("DELETE FROM currency WHERE id=#{id}")
    void delete(Integer id);

    @Select("SELECT * FROM currency WHERE id=#{id}")
    Currency getById(Integer id);

    @Select("SELECT * FROM currency")
    List<Currency> getAll();

}
