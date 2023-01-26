package com.chen.dao;

import com.chen.domain.Book;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface BookDao {
    @Insert("insert into tbl_book values (null,#{type},#{name},#{description})")
    boolean save(Book book);
    @Update("update tbl_book set type=#{type},name=#{name},description=#{description} where id=#{id}")
    boolean update(Book book);
    @Delete("delete from tbl_book where id=#{id}")
    boolean delete(Integer id);
    @Select("select * from tbl_book where id=#{id}")
    Book getById(Integer id);
    @Select("select * from tbl_book")
    List<Book> getAll();


}

