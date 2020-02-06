package com.xz.ignite.basefunction.cachestore.entity;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

/**
 * Created by xz on 2020/2/5.
 */
public class User {

    @QuerySqlField
    private String id;
    @QuerySqlField
    private String name;
    @QuerySqlField
    private String password;
    @QuerySqlField
    private String remark;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
