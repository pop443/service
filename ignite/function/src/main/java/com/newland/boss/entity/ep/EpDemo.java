package com.newland.boss.entity.ep;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

/**
 * Created by xz on 2020/3/24.
 */
public class EpDemo {
    @QuerySqlField
    private String name ;
    @QuerySqlField
    private Integer count ;

    public EpDemo() {
    }

    public EpDemo(String name, Integer count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
