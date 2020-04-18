package com.newland.ignite.label.entity;

import org.apache.ignite.cache.affinity.AffinityKeyMapped;
import org.apache.ignite.cache.query.annotations.QuerySqlField;

/**
 * Created by xz on 2020/4/16.
 */
public class BossAffinityKey {
    @AffinityKeyMapped
    @QuerySqlField(index = true)
    private String id ;
    @QuerySqlField(index = true)

    private String name ;

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
}
