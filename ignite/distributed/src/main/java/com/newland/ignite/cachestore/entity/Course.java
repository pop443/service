package com.newland.ignite.cachestore.entity;

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
    private String uidd ;

    public Course() {
    }

    public Course(String id, String name, String uidd) {
        this.id = id;
        this.name = name;
        this.uidd = uidd;
    }

    public Course(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getString("id") ;
        this.name = resultSet.getString("name") ;
        this.uidd = resultSet.getString("uidd") ;
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

    public String getUidd() {
        return uidd;
    }

    public void setUidd(String uidd) {
        this.uidd = uidd;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", uidd='" + uidd + '\'' +
                '}';
    }
}
