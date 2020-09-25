package com.newland.boss.entity.performance.complex;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

/**
 * Created by xz on 2020/4/24.
 */
public class ComplexValueItem2 {
    @QuerySqlField
    private String item2Id ;
    @QuerySqlField
    private Integer age ;

    public ComplexValueItem2(String item2Id, Integer age) {
        this.item2Id = item2Id;
        this.age = age;
    }

    public String getItem2Id() {
        return item2Id;
    }

    public void setItem2Id(String item2Id) {
        this.item2Id = item2Id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "ComplexValueItem2{" +
                "item2Id='" + item2Id + '\'' +
                ", age=" + age +
                '}';
    }
}
