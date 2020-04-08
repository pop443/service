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
    /*@QuerySqlField
    private Date birthday ;
    @QuerySqlField
    private String remark1 ;
    @QuerySqlField
    private String remark2 ;
    @QuerySqlField
    private String remark4 ;*/

    public ChangeData() {
    }

    public ChangeData(String id, Integer aInt, Long aLong, Double aDouble, Timestamp timestamp) {
        this.id = id;
        this.aInt = aInt;
        this.aLong = aLong;
        this.aDouble = aDouble;
        this.timestamp = timestamp;
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


    @Override
    public String toString() {
        return "ChangeData{" +
                "id='" + id + '\'' +
                ", aInt=" + aInt +
                ", aLong=" + aLong +
                ", aDouble=" + aDouble +
                ", timestamp=" + timestamp +
                '}';
    }
}
