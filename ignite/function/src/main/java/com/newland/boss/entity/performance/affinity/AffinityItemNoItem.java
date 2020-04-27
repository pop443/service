package com.newland.boss.entity.performance.affinity;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

/**
 * Created by xz on 2020/4/26.
 */
public class AffinityItemNoItem {
    @QuerySqlField(index = true)
    private Integer itemNoItemId ;

    public AffinityItemNoItem(Integer itemNoItemId) {
        this.itemNoItemId = itemNoItemId;
    }

    public Integer getItemNoItemId() {
        return itemNoItemId;
    }

    public void setItemNoItemId(Integer itemNoItemId) {
        this.itemNoItemId = itemNoItemId;
    }
}
