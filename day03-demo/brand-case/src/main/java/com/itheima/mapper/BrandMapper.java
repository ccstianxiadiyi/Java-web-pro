package com.itheima.mapper;

import com.itheima.pojo.Brand;
import com.itheima.pojo.PageBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BrandMapper {
    List<Brand> selectAll();
    void add(Brand brand);
    Brand selectById(Brand brand);
    void edit(Brand brand);
    void delete(Brand brand);
    void deleteMore(@Param("ids") int[] ids);
    List<Brand> selectByPage(@Param("begin")int begin,@Param("size")int size);
    int selectTotalCount();
    List<Brand> selectByConditions(@Param("brand") Brand brand, @Param("size") int size, @Param("begin") int begin);
    int selectionTotalCondition(Brand brand);
}
