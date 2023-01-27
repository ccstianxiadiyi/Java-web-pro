package com.chen.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.domain.Stu;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StuDao extends BaseMapper<Stu> {
}
