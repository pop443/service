package com.newland.ignite.cachestore.entity;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by xz on 2020/2/13.
 */
public class Automation {
    @QuerySqlField
    private String automation_name ;
    @QuerySqlField
    private Integer automation_age ;
    @QuerySqlField
    private String automation_remark ;

    public Automation() {
    }

    public Automation(String automation_name, Integer automation_age, String automation_remark) {
        this.automation_name = automation_name;
        this.automation_age = automation_age;
        this.automation_remark = automation_remark;
    }
    public Automation(ResultSet rs) throws SQLException {
        this.automation_name = rs.getString("automation_name");
        this.automation_age = rs.getInt("automation_age");
        this.automation_remark = rs.getString("automation_remark");
    }

    public String getAutomation_name() {
        return automation_name;
    }

    public void setAutomation_name(String automation_name) {
        this.automation_name = automation_name;
    }

    public Integer getAutomation_age() {
        return automation_age;
    }

    public void setAutomation_age(Integer automation_age) {
        this.automation_age = automation_age;
    }

    public String getAutomation_remark() {
        return automation_remark;
    }

    public void setAutomation_remark(String automation_remark) {
        this.automation_remark = automation_remark;
    }

    @Override
    public String toString() {
        return "Automation{" +
                "automation_name='" + automation_name + '\'' +
                ", automation_age=" + automation_age +
                ", automation_remark='" + automation_remark + '\'' +
                '}';
    }
}
