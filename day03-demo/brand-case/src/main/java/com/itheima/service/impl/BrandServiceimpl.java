package com.itheima.service.impl;

import com.itheima.mapper.BrandMapper;
import com.itheima.pojo.Brand;
import com.itheima.pojo.PageBean;
import com.itheima.service.BrandService;
import com.itheima.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class BrandServiceimpl implements BrandService {
    @Override
    public List<Brand> selectAll() {
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);
        List<Brand> brands = mapper.selectAll();
        sqlSession.close();
        return brands;
    }

    @Override
    public void add(Brand brand) {
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);
        mapper.add(brand);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public Brand selectById(Brand brand) {
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);
        Brand brand1 = mapper.selectById(brand);

        sqlSession.close();
        return brand1;
    }

    @Override
    public void edit(Brand brand) {
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);
        mapper.edit(brand);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void delete(Brand brand) {
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);
        mapper.delete(brand);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void deleteMore(int[] ids) {
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);
        mapper.deleteMore(ids);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public PageBean<Brand> selectByPage(int currentPage, int pageSize) {
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);
        List<Brand> brands = mapper.selectByPage((currentPage-1)*pageSize, pageSize);
        int i = mapper.selectTotalCount();
        PageBean<Brand> pageBean=new PageBean<>();
        pageBean.setRows(brands);
        pageBean.setTotalCount(i);
        return pageBean;

    }

    @Override
    public PageBean<Brand> selectByConditions(int size, int begin,Brand brand) {
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);
        if(brand.getBrandName()!=null&&brand.getBrandName().length()>0){
            brand.setBrandName("%"+brand.getBrandName()+"%");
        }
        if(brand.getCompanyName()!=null&&brand.getCompanyName().length()>0){
            brand.setCompanyName("%"+brand.getCompanyName()+"%");
        }
        List<Brand> brands = mapper.selectByConditions(brand,size,begin);
        int totalCount=mapper.selectionTotalCondition(brand);
        PageBean<Brand> pageBean=new PageBean<>();
        pageBean.setRows(brands);
        pageBean.setTotalCount(totalCount);
        sqlSession.close();
        return pageBean;
    }


}
