package com.newland.boss.problem.local;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

/**
 * Created by xz on 2020/6/2.
 */
public class Employee {
    @QuerySqlField
    public String firstName;

    @QuerySqlField
    public String lastName;

    @QuerySqlField
    public EmployeeId id;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public EmployeeId getId() {
        return id;
    }

    public void setId(EmployeeId id) {
        this.id = id;
    }
}
