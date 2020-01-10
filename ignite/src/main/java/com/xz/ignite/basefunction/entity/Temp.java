package com.xz.ignite.basefunction.entity;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

/**
 * Created by Administrator on 2020/1/2.
 */
public class Temp {
    @QuerySqlField(orderedGroups = {@QuerySqlField.Group(
            name = "tempindex", order = 0)})
    public String col1;
    @QuerySqlField(orderedGroups = {@QuerySqlField.Group(
            name = "tempindex", order = 1)})
    public String col2;
    @QuerySqlField
    public String col3;
    @QuerySqlField
    public String col4;
    @QuerySqlField
    public String col5;

    public Temp() {
    }

    public Temp(String col1, String col2, String col3, String col4, String col5) {
        this.col1 = col1;
        this.col2 = col2;
        this.col3 = col3;
        this.col4 = col4;
        this.col5 = col5;
    }

    public String getCol1() {
        return col1;
    }

    public void setCol1(String col1) {
        this.col1 = col1;
    }

    public String getCol2() {
        return col2;
    }

    public void setCol2(String col2) {
        this.col2 = col2;
    }

    public String getCol3() {
        return col3;
    }

    public void setCol3(String col3) {
        this.col3 = col3;
    }

    public String getCol4() {
        return col4;
    }

    public void setCol4(String col4) {
        this.col4 = col4;
    }

    public String getCol5() {
        return col5;
    }

    public void setCol5(String col5) {
        this.col5 = col5;
    }
}
