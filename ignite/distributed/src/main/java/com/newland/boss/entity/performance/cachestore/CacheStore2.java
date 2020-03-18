package com.newland.boss.entity.performance.cachestore;

import com.newland.boss.entity.performance.CustObj;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by xz on 2020/3/17.
 */
public class CacheStore2 extends CustObj{

    public CacheStore2(String id, String bytes) {
        super(id, bytes);
    }

    public CacheStore2(ResultSet rs) throws SQLException {
        super(rs);
    }

}
