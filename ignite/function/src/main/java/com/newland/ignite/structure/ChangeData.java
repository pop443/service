package com.newland.ignite.structure;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.util.Date;

/**
 * Created by xz on 2020/3/30.
 */
public class ChangeData {

    @QuerySqlField
    private String id ;
    @QuerySqlField
    private Long aLong ;
    @QuerySqlField
    private Double aDouble ;
    @QuerySqlField
    private Date birthday ;
    @QuerySqlField
    private String remark1 ;
    @QuerySqlField
    private String remark2 ;
    @QuerySqlField
    private String remark4 ;

    public ChangeData() {
    }

    public ChangeData(String id, Long aLong, Double aDouble, Date birthday, String remark1, String remark2, String remark4) {
        this.id = id;
        this.aLong = aLong;
        this.aDouble = aDouble;
        this.birthday = birthday;
        this.remark1 = remark1;
        this.remark2 = remark2;
        this.remark4 = remark4;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getaLong() {
        return aLong;
    }

    public void setaLong(Long aLong) {
        this.aLong = aLong;
    }

    public Double getaDouble() {
        return aDouble;
    }

    public void setaDouble(Double aDouble) {
        this.aDouble = aDouble;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    public String getRemark4() {
        return remark4;
    }

    public void setRemark4(String remark4) {
        this.remark4 = remark4;
    }

    @Override
    public String toString() {
        return "ChangeData{" +
                "id='" + id + '\'' +
                ", aLong=" + aLong +
                ", aDouble=" + aDouble +
                ", birthday=" + birthday +
                ", remark1='" + remark1 + '\'' +
                ", remark2='" + remark2 + '\'' +
                ", remark4='" + remark4 + '\'' +
                '}';
    }
}
