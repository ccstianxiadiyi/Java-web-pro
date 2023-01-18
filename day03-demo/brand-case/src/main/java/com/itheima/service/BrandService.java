package com.itheima.service;

import com.itheima.pojo.Brand;
import com.itheima.pojo.PageBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BrandService {
    List<Brand> selectAll();
    void add(Brand brand);
    Brand selectById(Brand brand);
    void edit(Brand brand);
    void delete(Brand brand);
    void deleteMore(int[] ids);
    PageBean<Brand> selectByPage(int currentPage,int pageSize);
    PageBean<Brand> selectByConditions(int size, int begin,Brand brand);

}
