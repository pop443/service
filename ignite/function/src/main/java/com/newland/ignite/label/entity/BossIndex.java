package com.newland.ignite.label.entity;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

/**
 * Created by xz on 2020/4/16.
 */
public class BossIndex {
    @QuerySqlField(index = true)
    private Long user_id1 ;
    @QuerySqlField
    private Long user_id2 ;
    @QuerySqlField(index = true,descending = true)
    private String user_name1 ;
    @QuerySqlField
    private String user_name2 ;
    @QuerySqlField(orderedGroups = {@QuerySqlField.Group(
            name = "BossIndexJoint", order = 0)})
    private String joint1 ;
    @QuerySqlField(orderedGroups = {@QuerySqlField.Group(
            name = "BossIndexJoint", order = 1)})
    private String joint2 ;

    public Long getUser_id1() {
        return user_id1;
    }

    public void setUser_id1(Long user_id1) {
        this.user_id1 = user_id1;
    }

    public Long getUser_id2() {
        return user_id2;
    }

    public void setUser_id2(Long user_id2) {
        this.user_id2 = user_id2;
    }

    public String getUser_name1() {
        return user_name1;
    }

    public void setUser_name1(String user_name1) {
        this.user_name1 = user_name1;
    }

    public String getUser_name2() {
        return user_name2;
    }

    public void setUser_name2(String user_name2) {
        this.user_name2 = user_name2;
    }

    public String getJoint1() {
        return joint1;
    }

    public void setJoint1(String joint1) {
        this.joint1 = joint1;
    }

    public String getJoint2() {
        return joint2;
    }

    public void setJoint2(String joint2) {
        this.joint2 = joint2;
    }
}
