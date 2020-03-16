package com.newland.boss.entity.test;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

/**
 * Created by xz on 2020/3/2.
 */
public class TestIgnite {
    @QuerySqlField(index = true)
    private String id ;
    @QuerySqlField
    private String name ;
    @QuerySqlField
    private Integer age ;
    @QuerySqlField
    private String remark ;

    public TestIgnite() {
    }

    public TestIgnite(String id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "TestIgnite{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", remark=" + remark +
                '}';
    }
}
