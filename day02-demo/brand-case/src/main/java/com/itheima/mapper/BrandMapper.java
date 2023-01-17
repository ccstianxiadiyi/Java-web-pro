package com.itheima.mapper;

import com.itheima.pojo.Brand;

import java.util.List;

public interface BrandMapper {
    List<Brand> selectAll();
    void add(Brand brand);
    Brand selectById(Brand brand);
    void edit(Brand brand);
    void delete(Brand brand);
}
