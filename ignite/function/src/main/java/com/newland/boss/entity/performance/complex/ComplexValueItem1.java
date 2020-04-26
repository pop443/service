package com.newland.boss.entity.performance.complex;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.util.Arrays;

/**
 * Created by xz on 2020/4/24.
 */
public class ComplexValueItem1 {
    @QuerySqlField
    private String item1Id ;
    @QuerySqlField
    private String[] itStrings ;
    @QuerySqlField
    private Object[] objects ;

    public ComplexValueItem1(String item1Id) {
        this.item1Id = item1Id;
        this.itStrings = new String[]{"1","2"} ;
        this.objects = new Object[]{"3",4} ;
    }

    public String getItem1Id() {
        return item1Id;
    }

    public void setItem1Id(String item1Id) {
        this.item1Id = item1Id;
    }

    public String[] getItStrings() {
        return itStrings;
    }

    public void setItStrings(String[] itStrings) {
        this.itStrings = itStrings;
    }

    public Object[] getObjects() {
        return objects;
    }

    public void setObjects(Object[] objects) {
        this.objects = objects;
    }

    @Override
    public String toString() {
        return "ComplexValueItem1{" +
                "item1Id='" + item1Id + '\'' +
                ", itStrings=" + Arrays.toString(itStrings) +
                ", objects=" + Arrays.toString(objects) +
                '}';
    }
}
