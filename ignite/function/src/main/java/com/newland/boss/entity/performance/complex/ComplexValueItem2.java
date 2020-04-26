package com.newland.boss.entity.performance.complex;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

/**
 * Created by xz on 2020/4/24.
 */
public class ComplexValueItem2 {
    @QuerySqlField
    private String item2Id ;

    public ComplexValueItem2(String item2Id) {
        this.item2Id = item2Id;
    }

    public String getItem2Id() {
        return item2Id;
    }

    public void setItem2Id(String item2Id) {
        this.item2Id = item2Id;
    }


    @Override
    public String toString() {
        return "ComplexValueItem2{" +
                "item2Id='" + item2Id + '\'' +
                '}';
    }
}
