package com.xz.ignite.basefunction.entity;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.sql.Timestamp;

/**
 * 用户套餐订购基础中间层
 */
public class UserPackageDayTemp {
    /**
     * 结束时间
     */
    @QuerySqlField
    private Timestamp end_time;
    /**
     * 渠道标识
     */
    @QuerySqlField
    private String channel_id;
    /**
     * 渠道类型
     */
    @QuerySqlField
    private String channel_type;
    /**
     * 商品实体编码
     */
    @QuerySqlField
    private Long offering_inst_id;
    /**
     * 取消时间精确到秒
     */
    @QuerySqlField
    private Timestamp cancel_time;
    /**
     * 取消渠道编码
     */
    @QuerySqlField
    private String cancelorgid;
    /**
     * 取消渠道类型
     */
    @QuerySqlField
    private String cancelaccesstype;
    /**
     * 取消时间
     */
    @QuerySqlField
    private Integer canceldate;
    /**
     * 取消流水号
     */
    @QuerySqlField
    private Long canceloid;
    /**
     * 取消操作员编号
     */
    @QuerySqlField
    private String canceloperid;
    /**
     * 申请时间
     */
    @QuerySqlField
    private Timestamp apply_time;
    /**
     * 开始时间
     */
    @QuerySqlField
    private Timestamp start_time;
    /**
     * 分区标识
     */
    @QuerySqlField
    private Integer day_number;
    /**
     * 统计日期
     */
    @QuerySqlField
    private Integer state_date;
    /**
     * 用户标示
     */
    @QuerySqlField
    private Long user_id;
    /**
     * 地区编码
     */
    @QuerySqlField
    private Integer area_id;
    /**
     * 预订时间
     */
    @QuerySqlField
    private Integer apply_date;
    /**
     * 生效时间
     */
    @QuerySqlField
    private Integer start_date;
    /**
     * 结束时间
     */
    @QuerySqlField
    private Integer end_date;
    /**
     * 套餐编码
     */
    @QuerySqlField
    private Long package_code;
    /**
     * 状态
     */
    @QuerySqlField
    private Integer state;
    /**
     * 操作员
     */
    @QuerySqlField
    private Long operator_id;
    /**
     * 套餐大类
     */
    @QuerySqlField
    private Long business_id;
    /**
     * 产品编码
     */
    @QuerySqlField
    private String prodid;
    /**
     * 用户类型（网络类型）
     */
    @QuerySqlField
    private String net_type;
    /**
     * 用户群编码
     */
    @QuerySqlField
    private Long grpsubsid;
    /**
     * 注册类型(受理渠道，字典定义同办理流水表)
     */
    @QuerySqlField
    private String regtype;
    /**
     * 申请流水号
     */
    @QuerySqlField
    private Long applyoid;

    public Timestamp getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Timestamp end_time) {
        this.end_time = end_time;
    }

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public String getChannel_type() {
        return channel_type;
    }

    public void setChannel_type(String channel_type) {
        this.channel_type = channel_type;
    }

    public Long getOffering_inst_id() {
        return offering_inst_id;
    }

    public void setOffering_inst_id(Long offering_inst_id) {
        this.offering_inst_id = offering_inst_id;
    }

    public Timestamp getCancel_time() {
        return cancel_time;
    }

    public void setCancel_time(Timestamp cancel_time) {
        this.cancel_time = cancel_time;
    }

    public String getCancelorgid() {
        return cancelorgid;
    }

    public void setCancelorgid(String cancelorgid) {
        this.cancelorgid = cancelorgid;
    }

    public String getCancelaccesstype() {
        return cancelaccesstype;
    }

    public void setCancelaccesstype(String cancelaccesstype) {
        this.cancelaccesstype = cancelaccesstype;
    }

    public Integer getCanceldate() {
        return canceldate;
    }

    public void setCanceldate(Integer canceldate) {
        this.canceldate = canceldate;
    }

    public Long getCanceloid() {
        return canceloid;
    }

    public void setCanceloid(Long canceloid) {
        this.canceloid = canceloid;
    }

    public String getCanceloperid() {
        return canceloperid;
    }

    public void setCanceloperid(String canceloperid) {
        this.canceloperid = canceloperid;
    }

    public Timestamp getApply_time() {
        return apply_time;
    }

    public void setApply_time(Timestamp apply_time) {
        this.apply_time = apply_time;
    }

    public Timestamp getStart_time() {
        return start_time;
    }

    public void setStart_time(Timestamp start_time) {
        this.start_time = start_time;
    }

    public Integer getDay_number() {
        return day_number;
    }

    public void setDay_number(Integer day_number) {
        this.day_number = day_number;
    }

    public Integer getState_date() {
        return state_date;
    }

    public void setState_date(Integer state_date) {
        this.state_date = state_date;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Integer getArea_id() {
        return area_id;
    }

    public void setArea_id(Integer area_id) {
        this.area_id = area_id;
    }

    public Integer getApply_date() {
        return apply_date;
    }

    public void setApply_date(Integer apply_date) {
        this.apply_date = apply_date;
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

    public Long getPackage_code() {
        return package_code;
    }

    public void setPackage_code(Long package_code) {
        this.package_code = package_code;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getOperator_id() {
        return operator_id;
    }

    public void setOperator_id(Long operator_id) {
        this.operator_id = operator_id;
    }

    public Long getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(Long business_id) {
        this.business_id = business_id;
    }

    public String getProdid() {
        return prodid;
    }

    public void setProdid(String prodid) {
        this.prodid = prodid;
    }

    public String getNet_type() {
        return net_type;
    }

    public void setNet_type(String net_type) {
        this.net_type = net_type;
    }

    public Long getGrpsubsid() {
        return grpsubsid;
    }

    public void setGrpsubsid(Long grpsubsid) {
        this.grpsubsid = grpsubsid;
    }

    public String getRegtype() {
        return regtype;
    }

    public void setRegtype(String regtype) {
        this.regtype = regtype;
    }

    public Long getApplyoid() {
        return applyoid;
    }

    public void setApplyoid(Long applyoid) {
        this.applyoid = applyoid;
    }
}
