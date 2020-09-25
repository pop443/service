package com.newland.boss.entity.performance.complex;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xz on 2020/4/26.
 */
public class ComplexList {
    @QuerySqlField
    private String id ;
    @QuerySqlField
    private List<ComplexValueItem2> objList ;
    @QuerySqlField
    private List<String> stringList ;
    @QuerySqlField
    private String[] stringArray ;
    @QuerySqlField
    private ComplexValueItem2[] objArray ;

    public ComplexList(int base) {
        this.id = ""+base ;

        this.objList = new ArrayList<>();
        objList.add(new ComplexValueItem2(base+"!1",base)) ;
        objList.add(new ComplexValueItem2(base+"!2",base)) ;
        objList.add(new ComplexValueItem2(base+"!3",base)) ;

        this.stringList = new ArrayList<>();
        stringList.add(base+"-1") ;
        stringList.add(base+"-2") ;
        stringList.add(base+"-3") ;

        this.stringArray = new String[]{base+"+1",base+"+2",base+"+3"};

        this.objArray = new ComplexValueItem2[]{new ComplexValueItem2(base+"@1",base),new ComplexValueItem2(base+"@2",base),new ComplexValueItem2(base+"@3",base)};
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ComplexValueItem2> getObjList() {
        return objList;
    }

    public void setObjList(List<ComplexValueItem2> objList) {
        this.objList = objList;
    }

    public List<String> getStringList() {
        return stringList;
    }

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }

    public String[] getStringArray() {
        return stringArray;
    }

    public void setStringArray(String[] stringArray) {
        this.stringArray = stringArray;
    }

    public ComplexValueItem2[] getObjArray() {
        return objArray;
    }

    public void setObjArray(ComplexValueItem2[] objArray) {
        this.objArray = objArray;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder() ;
        sb.append("id").append(id).append("\r\n") ;
        for (ComplexValueItem2 complexValueItem2:objList) {
            sb.append("complexValueItem2").append(complexValueItem2).append("\r\n") ;
        }
        for (String s:stringList) {
            sb.append("stringList").append(s).append("\r\n") ;
        }
        for (String s:stringArray) {
            sb.append("stringArray").append(s).append("\r\n") ;
        }
        for (ComplexValueItem2 complexValueItem2:objArray) {
            sb.append("objArray").append(complexValueItem2).append("\r\n") ;
        }
        return sb.toString();
    }
}
