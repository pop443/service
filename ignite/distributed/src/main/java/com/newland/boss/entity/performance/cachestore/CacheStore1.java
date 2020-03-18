package com.newland.boss.entity.performance.cachestore;

import com.newland.boss.entity.performance.CustObj;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by xz on 2020/3/17.
 */
public class CacheStore1 extends CustObj{

    public CacheStore1(String id, String bytes) {
        super(id, bytes);
    }

    public CacheStore1(ResultSet rs) throws SQLException {
        super(rs);
    }
}
