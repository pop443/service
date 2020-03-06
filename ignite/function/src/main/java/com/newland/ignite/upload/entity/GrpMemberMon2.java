package com.newland.ignite.upload.entity;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

/**
 * 集团客户成员信息表
 */
public class GrpMemberMon2 {
    /**
     * 归属乡镇
     */
    @QuerySqlField
    private String town_id;
    /**
     * 用户号码
     */
    @QuerySqlField
    private String phone_number;
    /**
     * 号码类型
     */
    @QuerySqlField
    private Integer phone_type;
    /**
     * 成员类别
     */
    @QuerySqlField
    private Integer member_class;
    /**
     * 成员类型
     */
    @QuerySqlField
    private Integer member_type;
    /**
     * 关键客户标识
     */
    @QuerySqlField
    private Integer key_flag;
    /**
     * 加入集团时间/最先业务使用时间
     */
    @QuerySqlField
    private Integer start_date;
    /**
     * 退出集团时间/最后业务退订时间
     */
    @QuerySqlField
    private Integer end_date;
    /**
     * 集团客户标识
     */
    @QuerySqlField
    private Long customer_id;
    /**
     * 月分区标识
     */
    @QuerySqlField
    private Integer month_number;
    /**
     * 用户品牌
     */
    @QuerySqlField
    private Integer brand_id;
    /**
     * 三大品牌(产品转)
     */
    @QuerySqlField
    private Integer big_brand_p;
    /**
     * 产品大类
     */
    @QuerySqlField
    private Integer product_class;
    /**
     * 产品小类
     */
    @QuerySqlField
    private Integer product_id;
    /**
     * 用户状态
     */
    @QuerySqlField
    private Integer user_status;
    /**
     * 入网时间
     */
    @QuerySqlField
    private Integer innet_date;
    /**
     * 停机时间
     */
    @QuerySqlField
    private Integer suspend_date;
    /**
     * 销户时间
     */
    @QuerySqlField
    private Integer terminate_date;
    /**
     * 大客户等级
     */
    @QuerySqlField
    private Integer vip_grade;
    /**
     * 用户年龄
     */
    @QuerySqlField
    private Integer age;
    /**
     * 成员收入
     */
    @QuerySqlField
    private Double fee_consume;
    /**
     * 集团代付收入
     */
    @QuerySqlField
    private Double fee_df;
    /**
     * 集团付费关系标示
     */
    @QuerySqlField
    private Integer pay_type;
    /**
     * 统付上限
     */
    @QuerySqlField
    private Double amount_uplimit;
    /**
     * 统计日期
     */
    @QuerySqlField
    private Integer stat_month;
    /**
     * 用户标识
     */
    @QuerySqlField
    private Long user_id;
    /**
     * 姓名
     */
    @QuerySqlField
    private String name;
    /**
     * 归属县市
     */
    @QuerySqlField
    private Integer area_id;

    public String getTown_id() {
        return town_id;
    }

    public void setTown_id(String town_id) {
        this.town_id = town_id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public Integer getPhone_type() {
        return phone_type;
    }

    public void setPhone_type(Integer phone_type) {
        this.phone_type = phone_type;
    }

    public Integer getMember_class() {
        return member_class;
    }

    public void setMember_class(Integer member_class) {
        this.member_class = member_class;
    }

    public Integer getMember_type() {
        return member_type;
    }

    public void setMember_type(Integer member_type) {
        this.member_type = member_type;
    }

    public Integer getKey_flag() {
        return key_flag;
    }

    public void setKey_flag(Integer key_flag) {
        this.key_flag = key_flag;
    }

    public Integer getStart_date() {
        return start_date;
    }

    public void setStart_date(Integer start_date) {
        this.start_date = start_date;
    }

    public Integer getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Integer end_date) {
        this.end_date = end_date;
    }

    public Long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Long customer_id) {
        this.customer_id = customer_id;
    }

    public Integer getMonth_number() {
        return month_number;
    }

    public void setMonth_number(Integer month_number) {
        this.month_number = month_number;
    }

    public Integer getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(Integer brand_id) {
        this.brand_id = brand_id;
    }

    public Integer getBig_brand_p() {
        return big_brand_p;
    }

    public void setBig_brand_p(Integer big_brand_p) {
        this.big_brand_p = big_brand_p;
    }

    public Integer getProduct_class() {
        return product_class;
    }

    public void setProduct_class(Integer product_class) {
        this.product_class = product_class;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public Integer getUser_status() {
        return user_status;
    }

    public void setUser_status(Integer user_status) {
        this.user_status = user_status;
    }

    public Integer getInnet_date() {
        return innet_date;
    }

    public void setInnet_date(Integer innet_date) {
        this.innet_date = innet_date;
    }

    public Integer getSuspend_date() {
        return suspend_date;
    }

    public void setSuspend_date(Integer suspend_date) {
        this.suspend_date = suspend_date;
    }

    public Integer getTerminate_date() {
        return terminate_date;
    }

    public void setTerminate_date(Integer terminate_date) {
        this.terminate_date = terminate_date;
    }

    public Integer getVip_grade() {
        return vip_grade;
    }

    public void setVip_grade(Integer vip_grade) {
        this.vip_grade = vip_grade;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getFee_consume() {
        return fee_consume;
    }

    public void setFee_consume(Double fee_consume) {
        this.fee_consume = fee_consume;
    }

    public Double getFee_df() {
        return fee_df;
    }

    public void setFee_df(Double fee_df) {
        this.fee_df = fee_df;
    }

    public Integer getPay_type() {
        return pay_type;
    }

    public void setPay_type(Integer pay_type) {
        this.pay_type = pay_type;
    }

    public Double getAmount_uplimit() {
        return amount_uplimit;
    }

    public void setAmount_uplimit(Double amount_uplimit) {
        this.amount_uplimit = amount_uplimit;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getArea_id() {
        return area_id;
    }

    public void setArea_id(Integer area_id) {
        this.area_id = area_id;
    }
}
