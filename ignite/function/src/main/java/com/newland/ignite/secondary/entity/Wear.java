package com.newland.ignite.secondary.entity;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

/**
 * Created by xz on 2020/3/5.
 */
public class Wear {
    @QuerySqlField(index = true)
    private String coat ;
    @QuerySqlField(index = true)
    private String pants ;
    @QuerySqlField
    private String shoe ;

    public Wear() {
    }

    public Wear(String coat, String pants, String shoe) {
        this.coat = coat;
        this.pants = pants;
        this.shoe = shoe;
    }

    public String getCoat() {
        return coat;
    }

    public void setCoat(String coat) {
        this.coat = coat;
    }

    public String getPants() {
        return pants;
    }

    public void setPants(String pants) {
        this.pants = pants;
    }

    public String getShoe() {
        return shoe;
    }

    public void setShoe(String shoe) {
        this.shoe = shoe;
    }

    @Override
    public String toString() {
        return "Wear{" +
                "coat='" + coat + '\'' +
                ", pants='" + pants + '\'' +
                ", shoe='" + shoe + '\'' +
                '}';
    }
}
