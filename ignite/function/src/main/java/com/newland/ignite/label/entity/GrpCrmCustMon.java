package com.newland.ignite.label.entity;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.sql.Timestamp;

/**
 * 集团客户资料月
 */
public class GrpCrmCustMon {
    /**
     * 地区
     */
    @QuerySqlField
    private Integer area_id;
    /**
     * 状态
     */
    @QuerySqlField
    private String status;
    /**
     * 创建时间
     */
    @QuerySqlField
    private Timestamp createtimestamp;
    /**
     * 创建渠道
     */
    @QuerySqlField
    private String orgid;
    /**
     * 联系电话
     */
    @QuerySqlField
    private String linkphone;
    /**
     * 客户经理工号
     */
    @QuerySqlField
    private String custmgr;
    /**
     * 地址
     */
    @QuerySqlField
    private String address;
    /**
     * 姓名
     */
    @QuerySqlField
    private String custmgrname;
    /**
     * 县市编码
     */
    @QuerySqlField
    private String countryid;
    /**
     * 规模
     */
    @QuerySqlField
    private Long annual_sales;
    /**
     * 区域类型
     */
    @QuerySqlField
    private String areatype;
    /**
     * 范围
     */
    @QuerySqlField
    private String corpscope;
    /**
     * 部门类型
     */
    @QuerySqlField
    private String departtype;
    /**
     * 公司成员数
     */
    @QuerySqlField
    private Integer emplyeenum;
    /**
     * 聚类市场编码
     */
    @QuerySqlField
    private String gathermarketid;
    /**
     * 组编码
     */
    @QuerySqlField
    private String groupid;
    /**
     * 集团名称
     */
    @QuerySqlField
    private String groupname;
    /**
     * 集团价值
     */
    @QuerySqlField
    private String groupvalue;
    /**
     * 入网时间
     */
    @QuerySqlField
    private Integer innettimestamp;
    /**
     * 是否聚类市场
     */
    @QuerySqlField
    private String isgathermarket;
    /**
     * 是否拍照
     */
    @QuerySqlField
    private String isphoto;
    /**
     * 付费类型
     */
    @QuerySqlField
    private String paymode;
    /**
     * 是否真实
     */
    @QuerySqlField
    private Integer istrue;
    /**
     * 主体产品
     */
    @QuerySqlField
    private String mainproduct;
    /**
     * 月分区
     */
    @QuerySqlField
    private Integer month_number;
    /**
     * 省
     */
    @QuerySqlField
    private String province;
    /**
     * 地域
     */
    @QuerySqlField
    private Integer region;
    /**
     * 统计月
     */
    @QuerySqlField
    private Integer stat_month;
    /**
     * 状态
     */
    @QuerySqlField
    private String stockstatus;
    /**
     * 单元类型
     */
    @QuerySqlField
    private String unitkind;
    /**
     * 集团行业类别1
     */
    @QuerySqlField
    private String vocaionkind1;
    /**
     * 集团行业类别2
     */
    @QuerySqlField
    private String vocaionkind2;
    /**
     * 集团行业类别3
     */
    @QuerySqlField
    private String vocaionkind3;
    /**
     * 集团行业类别4
     */
    @QuerySqlField
    private String vocaionkind4;
    /**
     * 聚类类型
     */
    @QuerySqlField
    private String gathertype;
    /**
     * 客户编码
     */
    @QuerySqlField
    private Long customer_id;

    public Integer getArea_id() {
        return area_id;
    }

    public void setArea_id(Integer area_id) {
        this.area_id = area_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatetimestamp() {
        return createtimestamp;
    }

    public void setCreatetimestamp(Timestamp createtimestamp) {
        this.createtimestamp = createtimestamp;
    }

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    public String getLinkphone() {
        return linkphone;
    }

    public void setLinkphone(String linkphone) {
        this.linkphone = linkphone;
    }

    public String getCustmgr() {
        return custmgr;
    }

    public void setCustmgr(String custmgr) {
        this.custmgr = custmgr;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCustmgrname() {
        return custmgrname;
    }

    public void setCustmgrname(String custmgrname) {
        this.custmgrname = custmgrname;
    }

    public String getCountryid() {
        return countryid;
    }

    public void setCountryid(String countryid) {
        this.countryid = countryid;
    }

    public Long getAnnual_sales() {
        return annual_sales;
    }

    public void setAnnual_sales(Long annual_sales) {
        this.annual_sales = annual_sales;
    }

    public String getAreatype() {
        return areatype;
    }

    public void setAreatype(String areatype) {
        this.areatype = areatype;
    }

    public String getCorpscope() {
        return corpscope;
    }

    public void setCorpscope(String corpscope) {
        this.corpscope = corpscope;
    }

    public String getDeparttype() {
        return departtype;
    }

    public void setDeparttype(String departtype) {
        this.departtype = departtype;
    }

    public Integer getEmplyeenum() {
        return emplyeenum;
    }

    public void setEmplyeenum(Integer emplyeenum) {
        this.emplyeenum = emplyeenum;
    }

    public String getGathermarketid() {
        return gathermarketid;
    }

    public void setGathermarketid(String gathermarketid) {
        this.gathermarketid = gathermarketid;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getGroupvalue() {
        return groupvalue;
    }

    public void setGroupvalue(String groupvalue) {
        this.groupvalue = groupvalue;
    }

    public Integer getInnettimestamp() {
        return innettimestamp;
    }

    public void setInnettimestamp(Integer innettimestamp) {
        this.innettimestamp = innettimestamp;
    }

    public String getIsgathermarket() {
        return isgathermarket;
    }

    public void setIsgathermarket(String isgathermarket) {
        this.isgathermarket = isgathermarket;
    }

    public String getIsphoto() {
        return isphoto;
    }

    public void setIsphoto(String isphoto) {
        this.isphoto = isphoto;
    }

    public String getPaymode() {
        return paymode;
    }

    public void setPaymode(String paymode) {
        this.paymode = paymode;
    }

    public Integer getIstrue() {
        return istrue;
    }

    public void setIstrue(Integer istrue) {
        this.istrue = istrue;
    }

    public String getMainproduct() {
        return mainproduct;
    }

    public void setMainproduct(String mainproduct) {
        this.mainproduct = mainproduct;
    }

    public Integer getMonth_number() {
        return month_number;
    }

    public void setMonth_number(Integer month_number) {
        this.month_number = month_number;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Integer getRegion() {
        return region;
    }

    public void setRegion(Integer region) {
        this.region = region;
    }

    public Integer getStat_month() {
        return stat_month;
    }

    public void setStat_month(Integer stat_month) {
        this.stat_month = stat_month;
    }

    public String getStockstatus() {
        return stockstatus;
    }

    public void setStockstatus(String stockstatus) {
        this.stockstatus = stockstatus;
    }

    public String getUnitkind() {
        return unitkind;
    }

    public void setUnitkind(String unitkind) {
        this.unitkind = unitkind;
    }

    public String getVocaionkind1() {
        return vocaionkind1;
    }

    public void setVocaionkind1(String vocaionkind1) {
        this.vocaionkind1 = vocaionkind1;
    }

    public String getVocaionkind2() {
        return vocaionkind2;
    }

    public void setVocaionkind2(String vocaionkind2) {
        this.vocaionkind2 = vocaionkind2;
    }

    public String getVocaionkind3() {
        return vocaionkind3;
    }

    public void setVocaionkind3(String vocaionkind3) {
        this.vocaionkind3 = vocaionkind3;
    }

    public String getVocaionkind4() {
        return vocaionkind4;
    }

    public void setVocaionkind4(String vocaionkind4) {
        this.vocaionkind4 = vocaionkind4;
    }

    public String getGathertype() {
        return gathertype;
    }

    public void setGathertype(String gathertype) {
        this.gathertype = gathertype;
    }

    public Long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Long customer_id) {
        this.customer_id = customer_id;
    }

    @Override
    public String toString() {
        return "GrpCrmCustMon{" +
                "area_id=" + area_id +
                ", status='" + status + '\'' +
                ", createtimestamp=" + createtimestamp +
                ", orgid='" + orgid + '\'' +
                ", linkphone='" + linkphone + '\'' +
                ", custmgr='" + custmgr + '\'' +
                ", address='" + address + '\'' +
                ", custmgrname='" + custmgrname + '\'' +
                ", countryid='" + countryid + '\'' +
                ", annual_sales=" + annual_sales +
                ", areatype='" + areatype + '\'' +
                ", corpscope='" + corpscope + '\'' +
                ", departtype='" + departtype + '\'' +
                ", emplyeenum=" + emplyeenum +
                ", gathermarketid='" + gathermarketid + '\'' +
                ", groupid='" + groupid + '\'' +
                ", groupname='" + groupname + '\'' +
                ", groupvalue='" + groupvalue + '\'' +
                ", innettimestamp=" + innettimestamp +
                ", isgathermarket='" + isgathermarket + '\'' +
                ", isphoto='" + isphoto + '\'' +
                ", paymode='" + paymode + '\'' +
                ", istrue=" + istrue +
                ", mainproduct='" + mainproduct + '\'' +
                ", month_number=" + month_number +
                ", province='" + province + '\'' +
                ", region=" + region +
                ", stat_month=" + stat_month +
                ", stockstatus='" + stockstatus + '\'' +
                ", unitkind='" + unitkind + '\'' +
                ", vocaionkind1='" + vocaionkind1 + '\'' +
                ", vocaionkind2='" + vocaionkind2 + '\'' +
                ", vocaionkind3='" + vocaionkind3 + '\'' +
                ", vocaionkind4='" + vocaionkind4 + '\'' +
                ", gathertype='" + gathertype + '\'' +
                ", customer_id=" + customer_id +
                '}';
    }
}
