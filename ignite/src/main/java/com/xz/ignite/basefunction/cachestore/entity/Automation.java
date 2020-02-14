package com.xz.ignite.basefunction.cachestore.entity;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

/**
 * Created by xz on 2020/2/13.
 */
public class Automation {
    @QuerySqlField
    private String name ;
    @QuerySqlField
    private Integer age ;
    @QuerySqlField
    private String remark ;

    public Automation() {
    }

    public Automation(String name, Integer age, String remark) {
        this.name = name;
        this.age = age;
        this.remark = remark;
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
}
