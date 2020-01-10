package com.xz.ignite.affinity;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

/**
 * Created by Administrator on 2019/12/25.
 */
public class Company {
    @QuerySqlField(index = true)
    private String companyId ;
    @QuerySqlField
    private String companyName ;

    public Company() {
    }

    public Company(String companyId, String companyName) {
        this.companyId = companyId;
        this.companyName = companyName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
