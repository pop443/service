package com.newland.ignite.cachestore.entity;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by xz on 2020/2/5.
 */
public class UserInfo {

    @QuerySqlField
    private String id;
    @QuerySqlField
    private String name;
    @QuerySqlField
    private String password;
    @QuerySqlField
    private String remark;

    public UserInfo() {
    }

    public UserInfo(String id, String name, String password, String remark) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.remark = remark;
    }

    public UserInfo(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getString("id") ;
        this.name = resultSet.getString("name") ;
        this.password = resultSet.getString("password") ;
        this.remark = resultSet.getString("remark") ;

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

    @Override
    public String toString() {
        return "UserInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
