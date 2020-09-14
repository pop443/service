package com.newland.ignite.multicenter.entity;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

/**
 * Created by xz on 2020/8/28.
 */
public class MultiCenterObj {
    @QuerySqlField
    private String id ;
    @QuerySqlField
    private Integer age ;

    public MultiCenterObj() {
    }

    public MultiCenterObj(String id, Integer age) {
        this.id = id;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
