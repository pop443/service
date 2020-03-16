package com.newland.boss.entity.performance.affinity;

import com.newland.boss.entity.performance.CustObj;
import org.apache.ignite.cache.query.annotations.QuerySqlField;

/**
 * Created by xz on 2020/3/10.
 */
public class AffinityItemNo extends CustObj {
    @QuerySqlField
    private Integer range1 ;
    @QuerySqlField(index = true)
    private Integer range2 ;
    public AffinityItemNo(String id, String bytes) {
        super(id, bytes);
    }

    public Integer getRange1() {
        return range1;
    }

    public void setRange1(Integer range1) {
        this.range1 = range1;
    }

    public Integer getRange2() {
        return range2;
    }

    public void setRange2(Integer range2) {
        this.range2 = range2;
    }

    @Override
    public String toString() {
        return "AffinityItemNo{" +
                "id='" + getId() + '\'' +
                "range1='" + range1 + '\'' +
                "range2='" + range2 + '\'' +
                '}';
    }
}
