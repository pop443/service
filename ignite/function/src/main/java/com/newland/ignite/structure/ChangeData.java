package com.newland.ignite.structure;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by xz on 2020/3/30.
 */
public class ChangeData {

    @QuerySqlField
    private String id ;
    @QuerySqlField
    private Integer aInt ;
    @QuerySqlField
    private Long aLong ;
    @QuerySqlField
    private Double aDouble ;
    @QuerySqlField
    private Timestamp timestamp ;
    @QuerySqlField
    private Date birthday ;
    @QuerySqlField
    private String remark1 ;
    @QuerySqlField
    private String remark2 ;
    @QuerySqlField
    private String remark3 ;
    @QuerySqlField
    private String remark5 ;

    public ChangeData() {
    }

    public ChangeData(String id, Integer aInt, Long aLong, Double aDouble, Timestamp timestamp, Date birthday, String remark1, String remark2, String remark4) {
        this.id = id;
        this.aInt = aInt;
        this.aLong = aLong;
        this.aDouble = aDouble;
        this.timestamp = timestamp;
        this.birthday = birthday;
        this.remark1 = remark1;
        this.remark2 = remark2;
        this.remark3 = remark4;
        this.remark5 = remark4;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getaInt() {
        return aInt;
    }

    public void setaInt(Integer aInt) {
        this.aInt = aInt;
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

    public String getRemark3() {
        return remark3;
    }

    public void setRemark3(String remark3) {
        this.remark3 = remark3;
    }

    public String getRemark5() {
        return remark5;
    }

    public void setRemark5(String remark5) {
        this.remark5 = remark5;
    }

    @Override
    public String toString() {
        return "ChangeData{" +
                "id='" + id + '\'' +
                ", aInt=" + aInt +
                ", aLong=" + aLong +
                ", aDouble=" + aDouble +
                ", timestamp=" + timestamp +
                ", birthday=" + birthday +
                ", remark1='" + remark1 + '\'' +
                ", remark2='" + remark2 + '\'' +
                ", remark4='" + remark3 + '\'' +
                ", remark6='" + remark5 + '\'' +
                '}';
    }
}
