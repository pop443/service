package com.newland.boss.entity.expired;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

/**
 * Created by xz on 2020/3/3.
 */
public class Expired {
    @QuerySqlField
    private String data ;

    public Expired() {
    }

    public Expired(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Event{" +
                "data='" + data + '\'' +
                '}';
    }
}
