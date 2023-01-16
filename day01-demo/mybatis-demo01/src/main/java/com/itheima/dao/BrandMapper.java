package com.itheima.dao;

import com.itheima.pojo.Brand;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface BrandMapper {

List<Brand> selectAll();
Brand selectById(int id);
Brand selectById2(Brand brand);
Brand selectById3(Map map);
Brand selectByConditions(Brand brand);
Brand selectByConditionSingle(Brand brand);
void add(Brand brand);
void delete(Brand brand);
void deleteMore(@Param("ids") int[] arr);
void edit(Brand brand);
}
