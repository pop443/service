package com.newland.ignite.secondary.entity;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

/**
 * Created by xz on 2020/3/5.
 */
public class Student {
    @QuerySqlField(index = true)
    private String id ;
    @QuerySqlField
    private String name ;
    @QuerySqlField
    private Integer age ;
    @QuerySqlField
    private Wear wear ;

    public Student() {
    }

    public Student(String id, String name, Integer age, Wear wear) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.wear = wear;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Wear getWear() {
        return wear;
    }

    public void setWear(Wear wear) {
        this.wear = wear;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", wear=" + wear +
                '}';
    }
}
