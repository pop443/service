package com.xz.ignite.basefunction.cachestore.entity;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by xz on 2020/2/9.
 */
public class Course {
    @QuerySqlField
    private String id ;
    @QuerySqlField
    private String name ;
    @QuerySqlField
    private String uid ;

    public Course() {
    }

    public Course(String id, String name, String uid) {
        this.id = id;
        this.name = name;
        this.uid = uid;
    }

    public Course(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getString("id") ;
        this.name = resultSet.getString("name") ;
        this.uid = resultSet.getString("uid") ;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }
}
