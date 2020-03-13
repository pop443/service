package com.newland.boss.entity.performance.affinity;

import com.newland.boss.entity.performance.CustObj;
import org.apache.ignite.cache.query.annotations.QuerySqlField;

/**
 * Created by xz on 2020/3/10.
 */
public class AffinityItemYes extends CustObj {
    @QuerySqlField
    private String name ;
    public AffinityItemYes(String id, String bytes) {
        super(id, bytes);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AffinityItemYes{" +
                "id='" + getId() + '\'' +
                "name='" + name + '\'' +
                '}';
    }
}
