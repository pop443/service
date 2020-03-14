package com.newland.boss.entity.performance.affinity;

import com.newland.boss.entity.performance.CustObj;
import org.apache.ignite.cache.query.annotations.QuerySqlField;

/**
 * Created by xz on 2020/3/10.
 */
public class AffinityItemYes extends CustObj {
    @QuerySqlField
    private String name ;
    @QuerySqlField(index = true)
    private String name2 ;
    public AffinityItemYes(String id, String bytes) {
        super(id, bytes);
        this.name = bytes;
        this.name2 = bytes ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    @Override
    public String toString() {
        return "AffinityItemYes{" +
                "id='" + getId() + '\'' +
                "name='" + name + '\'' +
                "name2='" + name2 + '\'' +
                '}';
    }
}
