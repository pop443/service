package com.newland.boss.entity.performance.complex;

import com.newland.boss.entity.performance.CustObj;
import org.apache.ignite.cache.query.annotations.QuerySqlField;

/**
 * Created by xz on 2020/4/22.
 */
public class ComplexValue extends CustObj{
    @QuerySqlField
    private ComplexValueItem1 complexValueItem1 ;
    @QuerySqlField
    private ComplexValueItem2 complexValueItem2 ;
    @QuerySqlField
    private String item2Name ;

    public ComplexValue(String id, String bytes) {
        super(id, bytes);
        this.complexValueItem1 = new ComplexValueItem1(id) ;
        this.complexValueItem2 = new ComplexValueItem2(id) ;
        this.item2Name = id ;
    }

    public ComplexValueItem1 getComplexValueItem1() {
        return complexValueItem1;
    }

    public void setComplexValueItem1(ComplexValueItem1 complexValueItem1) {
        this.complexValueItem1 = complexValueItem1;
    }

    public ComplexValueItem2 getComplexValueItem2() {
        return complexValueItem2;
    }

    public void setComplexValueItem2(ComplexValueItem2 complexValueItem2) {
        this.complexValueItem2 = complexValueItem2;
    }

    public String getItem2Name() {
        return item2Name;
    }

    public void setItem2Name(String item2Name) {
        this.item2Name = item2Name;
    }

    @Override
    public String toString() {
        return "ComplexValue{" +
                "complexValueItem1=" + complexValueItem1 +
                ", complexValueItem2=" + complexValueItem2 +
                ", item2Name='" + item2Name + '\'' +
                '}';
    }
}
