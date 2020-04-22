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
    private Long keyUserId2;
    @QuerySqlField(index = true, descending = false)
    private String keyUserStr;
    @QuerySqlField(index = true, descending = true)
    private String keyUserStr2;

}
