package com.newland.boss.entity.performance.complex;

import com.newland.boss.entity.performance.CustObj;
import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.util.ArrayList;

/**
 * Created by xz on 2020/9/21.
 */
public class ListObj extends CustObj {

    @QuerySqlField
    public ArrayList<ListObjItem> list ;
    public ListObj(String id, String bytes) {
        super(id, bytes);
        list = new ArrayList<>() ;
        list.add(new ListObjItem(id)) ;
    }

    public ArrayList<ListObjItem> getList() {
        return list;
    }

    public void setList(ArrayList<ListObjItem> list) {
        this.list = list;
    }
}
