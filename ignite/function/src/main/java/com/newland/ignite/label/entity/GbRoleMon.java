package com.newland.ignite.label.entity;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

/**
 * GB信令解析规则前15位（月）
 */
public class GbRoleMon {
    /**
     * 分区
     */
    @QuerySqlField
    private Integer month_number;
    /**
     * 统计月
     */
    @QuerySqlField
    private Integer stat_month;
    /**
     * 用户标识
     */
    @QuerySqlField
    private Long user_id;
    /**
     * 手机号码
     */
    @QuerySqlField
    private String msisdn;
    /**
     * rule_id前15位
     */
    @QuerySqlField
    private String rule_id;
    /**
     * 使用流量
     */
    @QuerySqlField
    private Long traffic_cnt;
    /**
     * 使用次数
     */
    @QuerySqlField
    private Integer gb_cnt;
    /**
     * 使用天数
     */
    @QuerySqlField
    private Integer app_cnt;

    public Integer getMonth_number() {
        return month_number;
    }

    public void setMonth_number(Integer month_number) {
        this.month_number = month_number;
    }

    public Integer getStat_month() {
        return stat_month;
    }

    public void setStat_month(Integer stat_month) {
        this.stat_month = stat_month;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getRule_id() {
        return rule_id;
    }

    public void setRule_id(String rule_id) {
        this.rule_id = rule_id;
    }

    public Long getTraffic_cnt() {
        return traffic_cnt;
    }

    public void setTraffic_cnt(Long traffic_cnt) {
        this.traffic_cnt = traffic_cnt;
    }

    public Integer getGb_cnt() {
        return gb_cnt;
    }

    public void setGb_cnt(Integer gb_cnt) {
        this.gb_cnt = gb_cnt;
    }

    public Integer getApp_cnt() {
        return app_cnt;
    }

    public void setApp_cnt(Integer app_cnt) {
        this.app_cnt = app_cnt;
    }
}
