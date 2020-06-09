package com.newland.boss.entity.performance.affinity;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

/**
 * Created by xz on 2020/4/26.
 */
public class AffinityItemNoItem {
    @QuerySqlField(index = true)
    private Integer itemNoItemId ;
    @QuerySqlField(index = true)
    private String id3 ;

    public AffinityItemNoItem(Integer itemNoItemId) {
        this.itemNoItemId = itemNoItemId;
        this.id3 = itemNoItemId+"";
    }

    public String getId3() {
        return id3;
    }

    public void setId3(String id3) {
        this.id3 = id3;
    }

    public Integer getItemNoItemId() {
        return itemNoItemId;
    }

    public void setItemNoItemId(Integer itemNoItemId) {
        this.itemNoItemId = itemNoItemId;
    }
}
