package com.itheima.service;

import com.itheima.pojo.Brand;

import java.util.List;

public interface BrandService {
    List<Brand> selectAll();
    void add(Brand brand);
    Brand selectById(Brand brand);
    void edit(Brand brand);
    void delete(Brand brand);
}
