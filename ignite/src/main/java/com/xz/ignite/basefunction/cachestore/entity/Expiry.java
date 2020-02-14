package com.xz.ignite.basefunction.cachestore.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by xz on 2020/2/14.
 */
public class Expiry {
    private String id;
    private String name;
    private String remark;

    public Expiry() {
    }

    public Expiry(String id, String name, String remark) {
        this.id = id;
        this.name = name;
        this.remark = remark;
    }

    public Expiry(ResultSet rs) throws SQLException {
        this.id = rs.getString("id");
        this.name = rs.getString("name");
        this.remark = rs.getString("remark");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Expiry{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
