package com.newland.ignite.label.entity;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.util.List;
import java.util.Map;

/**
 * Created by xz on 2020/4/16.
 */
public class BossAffinity {
    @QuerySqlField
    private Long user_id1 ;
    @QuerySqlField
    private Long user_id2 ;
    @QuerySqlField
    private String user_name1 ;
    @QuerySqlField
    private String user_name2 ;
    @QuerySqlField
    private String joint1 ;
    @QuerySqlField
    private String joint2 ;
    @QuerySqlField
    private List<String> list1 ;
    @QuerySqlField
    private List<BossIndex> list2 ;
    @QuerySqlField
    private Map<String,String> map1 ;
    @QuerySqlField
    private Map<String,BossIndex> map2 ;

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

    public List<String> getList1() {
        return list1;
    }

    public void setList1(List<String> list1) {
        this.list1 = list1;
    }

    public List<BossIndex> getList2() {
        return list2;
    }

    public void setList2(List<BossIndex> list2) {
        this.list2 = list2;
    }

    public Map<String, String> getMap1() {
        return map1;
    }

    public void setMap1(Map<String, String> map1) {
        this.map1 = map1;
    }

    public Map<String, BossIndex> getMap2() {
        return map2;
    }

    public void setMap2(Map<String, BossIndex> map2) {
        this.map2 = map2;
    }

    @Override
    public String toString() {
        return "BossAffinity{" +
                "user_id1=" + user_id1 +
                ", user_id2=" + user_id2 +
                ", user_name1='" + user_name1 + '\'' +
                ", user_name2='" + user_name2 + '\'' +
                ", joint1='" + joint1 + '\'' +
                ", joint2='" + joint2 + '\'' +
                ", list1=" + list1 +
                ", list2=" + list2 +
                ", map1=" + map1 +
                ", map2=" + map2 +
                '}';
    }
}
