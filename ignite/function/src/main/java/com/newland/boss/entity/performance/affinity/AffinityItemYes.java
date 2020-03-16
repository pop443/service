package com.newland.boss.entity.performance.affinity;

import com.newland.boss.entity.performance.CustObj;
import org.apache.ignite.cache.query.annotations.QuerySqlField;

/**
 * Created by xz on 2020/3/10.
 */
public class AffinityItemYes extends CustObj {
    public AffinityItemYes(String id, String bytes) {
        super(id, bytes);
    }


    @Override
    public String toString() {
        return "AffinityItemYes{" +
                "id='" + getId() + '\'' +
                '}';
    }
}
