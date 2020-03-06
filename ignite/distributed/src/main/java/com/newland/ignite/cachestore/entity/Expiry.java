package com.newland.ignite.cachestore.entity;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by xz on 2020/2/14.
 */
public class Expiry {
    @QuerySqlField
    private String id;
    @QuerySqlField
    private String name;
    @QuerySqlField
    private String remark;
    @QuerySqlField
    private Automation automation;

    public Expiry() {
    }

    public Expiry(String id, String name, String remark,Automation automation) {
        this.id = id;
        this.name = name;
        this.remark = remark;
        this.automation = automation;
    }

    public Expiry(ResultSet rs) throws SQLException {
        this.id = rs.getString("id");
        this.name = rs.getString("name");
        this.remark = rs.getString("remark");
        this.automation = new Automation(rs) ;
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

    public Automation getAutomation() {
        return automation;
    }

    public void setAutomation(Automation automation) {
        this.automation = automation;
    }

    @Override
    public String toString() {
        return "Expiry{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", automation=" + automation +
                '}';
    }
}
