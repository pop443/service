package com.newland.ignite.security;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

/**
 * Created by xz on 2020/4/13.
 */
public class Security {

    @QuerySqlField
    private String id ;

    @QuerySqlField
    private String name ;

    public Security() {
    }

    public Security(String id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return "Security{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
