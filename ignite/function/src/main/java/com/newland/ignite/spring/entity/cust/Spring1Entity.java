package com.newland.ignite.spring.entity.cust;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

/**
 * Created by xz on 2020/2/27.
 */
public class Spring1Entity {
    @QuerySqlField(index = true)
    private Integer id ;
    @QuerySqlField
    private String name ;
    @QuerySqlField
    private Integer age ;

    public Spring1Entity() {
    }

    public Spring1Entity(Integer id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Spring1Entity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
