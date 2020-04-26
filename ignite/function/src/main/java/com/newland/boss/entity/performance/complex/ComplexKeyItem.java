package com.newland.boss.entity.performance.complex;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

/**
 * Created by xz on 2020/4/22.
 */
public class ComplexKeyItem {
    @QuerySqlField(index = true)
    private String itemId;
    @QuerySqlField(index = true, descending = false)
    private Long itemUserId;
    @QuerySqlField(index = true, descending = true)
    private Long itemUserId2;
    @QuerySqlField(index = true, descending = false)
    private String itemUserStr;
    @QuerySqlField(index = true, descending = true)
    private String itemUserStr2;

    public ComplexKeyItem() {
    }

    public ComplexKeyItem(String itemId, Long itemUserId, Long itemUserId2, String itemUserStr, String itemUserStr2) {
        this.itemId = itemId;
        this.itemUserId = itemUserId;
        this.itemUserId2 = itemUserId2;
        this.itemUserStr = itemUserStr;
        this.itemUserStr2 = itemUserStr2;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Long getItemUserId() {
        return itemUserId;
    }

    public void setItemUserId(Long itemUserId) {
        this.itemUserId = itemUserId;
    }

    public Long getItemUserId2() {
        return itemUserId2;
    }

    public void setItemUserId2(Long itemUserId2) {
        this.itemUserId2 = itemUserId2;
    }

    public String getItemUserStr() {
        return itemUserStr;
    }

    public void setItemUserStr(String itemUserStr) {
        this.itemUserStr = itemUserStr;
    }

    public String getItemUserStr2() {
        return itemUserStr2;
    }

    public void setItemUserStr2(String itemUserStr2) {
        this.itemUserStr2 = itemUserStr2;
    }

}
