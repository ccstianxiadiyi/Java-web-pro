package com.chen.domain;

import com.baomidou.mybatisplus.annotation.*;

@TableName("stu")
public class Stu {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer age;
    @TableField(exist = false, select = false)
    private Long status;
/*
    @TableLogic(value="0",delval="1")
    private String deleted;
    @Version
    private Interger version;
 */
    public Stu() {
    }

    public Stu(Integer id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    /**
     * 获取
     *
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取
     *
     * @return age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * 设置
     *
     * @param age
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    public String toString() {
        return "Stu{id = " + id + ", name = " + name + ", age = " + age + "}";
    }
}
