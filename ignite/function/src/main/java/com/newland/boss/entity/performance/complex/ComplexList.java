package com.newland.boss.entity.performance.complex;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xz on 2020/4/26.
 */
public class ComplexList {

    @QuerySqlField
    private List<ComplexValueItem2> list1 ;
    @QuerySqlField
    private List<String> list2 ;

    @QuerySqlField
    private String[] strings ;

    @QuerySqlField
    private Object[] objects ;

    @QuerySqlField
    private ComplexValueItem2[] array1 ;

    public ComplexList(int base) {
        this.list1 = new ArrayList<>();
        list1.add(new ComplexValueItem2(base+"1")) ;
        list1.add(new ComplexValueItem2(base+"2")) ;
        this.list2 = new ArrayList<>();
        array1 = new ComplexValueItem2[]{new ComplexValueItem2(base+"1"),new ComplexValueItem2(base+"2")};
        list2.add(base+"1") ;
        this.strings = new String[]{base+"",base+"1"};
        this.objects = new Object[]{base,base+"1"};
    }

    public List<ComplexValueItem2> getList1() {
        return list1;
    }

    public void setList1(List<ComplexValueItem2> list1) {
        this.list1 = list1;
    }

    public List<String> getList2() {
        return list2;
    }

    public void setList2(List<String> list2) {
        this.list2 = list2;
    }

    public String[] getStrings() {
        return strings;
    }

    public void setStrings(String[] strings) {
        this.strings = strings;
    }

    public Object[] getObjects() {
        return objects;
    }

    public void setObjects(Object[] objects) {
        this.objects = objects;
    }

    public ComplexValueItem2[] getArray1() {
        return array1;
    }

    public void setArray1(ComplexValueItem2[] array1) {
        this.array1 = array1;
    }
}
