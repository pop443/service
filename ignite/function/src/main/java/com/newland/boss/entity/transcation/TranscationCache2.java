package com.newland.boss.entity.transcation;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

/**
 * Created by xz on 2020/3/25.
 */
public class TranscationCache2 {
    @QuerySqlField
    private String id ;
    @QuerySqlField
    private Integer age ;

    public TranscationCache2() {
    }

    public TranscationCache2(String id, Integer age) {
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