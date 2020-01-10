package com.xz.ignite.basefunction.entity;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

/**
 * 用户属性日中间层
 */
public class UserAttrDayTemp {
    /**
     * 日分区标识
     */
    @QuerySqlField
    private Integer day_number;
    /**
     * 统计日期
     */
    @QuerySqlField
    private Integer stat_date;
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
     * 客户标识
     */
    @QuerySqlField
    private Long customer_id;
    /**
     * 手机号码
     */
    @QuerySqlField
    private Long msisdn;
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
    private Long product_id;
    /**
     * 归属县市
     */
    @QuerySqlField
    private Integer area_id;
    /**
     * 身份证号
     */
    @QuerySqlField
    private String ic_no;
    /**
     * 用户年龄
     */
    @QuerySqlField
    private Integer age;
    /**
     * 性别
     */
    @QuerySqlField
    private Integer sex;
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
     * 开户时间
     */
    @QuerySqlField
    private Integer apply_date;
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
     * 资料完整性标识
     */
    @QuerySqlField
    private Integer is_full;
    /**
     * 第一次通话时间
     */
    @QuerySqlField
    private Integer firstcall_date;
    /**
     * 第一次通话标识
     */
    @QuerySqlField
    private Integer firstcall_flag;
    /**
     * 开户营业点
     */
    @QuerySqlField
    private Long user_site;
    /**
     * 用户类型
     */
    @QuerySqlField
    private Integer user_type;
    /**
     * 是否移动公话
     */
    @QuerySqlField
    private Integer public_flag;
    /**
     * 是否集团用户
     */
    @QuerySqlField
    private Integer is_group_user;
    /**
     * 是否为大客户
     */
    @QuerySqlField
    private Integer is_vip;
    /**
     * 大客户等级
     */
    @QuerySqlField
    private Integer vip_grade;
    /**
     * 大客户类型
     */
    @QuerySqlField
    private Integer vip_type;
    /**
     * 消费费用
     */
    @QuerySqlField
    private Double fee_consume;
    /**
     * 归属乡镇
     */
    @QuerySqlField
    private String town_id;
    /**
     * 联系电话
     */
    @QuerySqlField
    private String contect_tel;
    /**
     * 联系地址
     */
    @QuerySqlField
    private String contect_addr;
    /**
     * 联系email
     */
    @QuerySqlField
    private String contect_email;
    /**
     * 账户标识
     */
    @QuerySqlField
    private Long account_id;
    /**
     * IMSI号
     */
    @QuerySqlField
    private Long imsi;
    /**
     * 证件类型
     */
    @QuerySqlField
    private Integer ic_type;
    /**
     * 客户类型
     */
    @QuerySqlField
    private Integer customer_type;
    /**
     * 证件地址
     */
    @QuerySqlField
    private String ic_addr;
    /**
     * 客户升级
     */
    @QuerySqlField
    private Integer vip_intype;
    /**
     * 成为VIP日期
     */
    @QuerySqlField
    private Integer vip_indate;
    /**
     * 注册状态
     */
    @QuerySqlField
    private Integer regstatus;
    /**
     * 身份证
     */
    @QuerySqlField
    private String certno;
    /**
     * 付费方式
     */
    @QuerySqlField
    private String paymode;
    /**
     * SIM卡号
     */
    @QuerySqlField
    private String enums;
    /**
     * 是否移动手机
     */
    @QuerySqlField
    private Integer is_mobile_user;
    /**
     * 用户姓名
     */
    @QuerySqlField
    private String subsname;
    /**
     * 用户归属区县
     */
    @QuerySqlField
    private Integer region;
    /**
     * 开户客户标识
     */
    @QuerySqlField
    private Long opening_cust_iD;
    /**
     * 使用客户标识
     */
    @QuerySqlField
    private Long using_cust_id;
    /**
     * 资料补录标识(1：是，0：否）
     */
    @QuerySqlField
    private Long appdstatus;
    /**
     * 用户状态（BES）
     */
    @QuerySqlField
    private String bes_status;
    /**
     * 修改部门编码
     */
    @QuerySqlField
    private Long modify_dept_id;
    /**
     * 在网时长（月）
     */
    @QuerySqlField
    private Long innet_months;
    /**
     * 备1
     */
    @QuerySqlField
    private Integer remark_1;
    /**
     * 备2
     */
    @QuerySqlField
    private Integer remark_2;
    /**
     * 备3
     */
    @QuerySqlField
    private Integer remark_3;
    /**
     * 备4
     */
    @QuerySqlField
    private Integer remark_4;
    /**
     * 备5
     */
    @QuerySqlField
    private String remark_5;
    /**
     * 备6
     */
    @QuerySqlField
    private String remark_6;
    /**
     * 备7
     */
    @QuerySqlField
    private String remark_7;
    /**
     * 携转状态 1 携号转入 2 携号转出 3 携回
     */
    @QuerySqlField
    private Integer trans_status;

    public Integer getDay_number() {
        return day_number;
    }

    public void setDay_number(Integer day_number) {
        this.day_number = day_number;
    }

    public Integer getStat_date() {
        return stat_date;
    }

    public void setStat_date(Integer stat_date) {
        this.stat_date = stat_date;
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

    public Long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Long customer_id) {
        this.customer_id = customer_id;
    }

    public Long getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(Long msisdn) {
        this.msisdn = msisdn;
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

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public Integer getArea_id() {
        return area_id;
    }

    public void setArea_id(Integer area_id) {
        this.area_id = area_id;
    }

    public String getIc_no() {
        return ic_no;
    }

    public void setIc_no(String ic_no) {
        this.ic_no = ic_no;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
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

    public Integer getApply_date() {
        return apply_date;
    }

    public void setApply_date(Integer apply_date) {
        this.apply_date = apply_date;
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

    public Integer getIs_full() {
        return is_full;
    }

    public void setIs_full(Integer is_full) {
        this.is_full = is_full;
    }

    public Integer getFirstcall_date() {
        return firstcall_date;
    }

    public void setFirstcall_date(Integer firstcall_date) {
        this.firstcall_date = firstcall_date;
    }

    public Integer getFirstcall_flag() {
        return firstcall_flag;
    }

    public void setFirstcall_flag(Integer firstcall_flag) {
        this.firstcall_flag = firstcall_flag;
    }

    public Long getUser_site() {
        return user_site;
    }

    public void setUser_site(Long user_site) {
        this.user_site = user_site;
    }

    public Integer getUser_type() {
        return user_type;
    }

    public void setUser_type(Integer user_type) {
        this.user_type = user_type;
    }

    public Integer getPublic_flag() {
        return public_flag;
    }

    public void setPublic_flag(Integer public_flag) {
        this.public_flag = public_flag;
    }

    public Integer getIs_group_user() {
        return is_group_user;
    }

    public void setIs_group_user(Integer is_group_user) {
        this.is_group_user = is_group_user;
    }

    public Integer getIs_vip() {
        return is_vip;
    }

    public void setIs_vip(Integer is_vip) {
        this.is_vip = is_vip;
    }

    public Integer getVip_grade() {
        return vip_grade;
    }

    public void setVip_grade(Integer vip_grade) {
        this.vip_grade = vip_grade;
    }

    public Integer getVip_type() {
        return vip_type;
    }

    public void setVip_type(Integer vip_type) {
        this.vip_type = vip_type;
    }

    public Double getFee_consume() {
        return fee_consume;
    }

    public void setFee_consume(Double fee_consume) {
        this.fee_consume = fee_consume;
    }

    public String getTown_id() {
        return town_id;
    }

    public void setTown_id(String town_id) {
        this.town_id = town_id;
    }

    public String getContect_tel() {
        return contect_tel;
    }

    public void setContect_tel(String contect_tel) {
        this.contect_tel = contect_tel;
    }

    public String getContect_addr() {
        return contect_addr;
    }

    public void setContect_addr(String contect_addr) {
        this.contect_addr = contect_addr;
    }

    public String getContect_email() {
        return contect_email;
    }

    public void setContect_email(String contect_email) {
        this.contect_email = contect_email;
    }

    public Long getAccount_id() {
        return account_id;
    }

    public void setAccount_id(Long account_id) {
        this.account_id = account_id;
    }

    public Long getImsi() {
        return imsi;
    }

    public void setImsi(Long imsi) {
        this.imsi = imsi;
    }

    public Integer getIc_type() {
        return ic_type;
    }

    public void setIc_type(Integer ic_type) {
        this.ic_type = ic_type;
    }

    public Integer getCustomer_type() {
        return customer_type;
    }

    public void setCustomer_type(Integer customer_type) {
        this.customer_type = customer_type;
    }

    public String getIc_addr() {
        return ic_addr;
    }

    public void setIc_addr(String ic_addr) {
        this.ic_addr = ic_addr;
    }

    public Integer getVip_intype() {
        return vip_intype;
    }

    public void setVip_intype(Integer vip_intype) {
        this.vip_intype = vip_intype;
    }

    public Integer getVip_indate() {
        return vip_indate;
    }

    public void setVip_indate(Integer vip_indate) {
        this.vip_indate = vip_indate;
    }

    public Integer getRegstatus() {
        return regstatus;
    }

    public void setRegstatus(Integer regstatus) {
        this.regstatus = regstatus;
    }

    public String getCertno() {
        return certno;
    }

    public void setCertno(String certno) {
        this.certno = certno;
    }

    public String getPaymode() {
        return paymode;
    }

    public void setPaymode(String paymode) {
        this.paymode = paymode;
    }

    public String getEnums() {
        return enums;
    }

    public void setEnums(String enums) {
        this.enums = enums;
    }

    public Integer getIs_mobile_user() {
        return is_mobile_user;
    }

    public void setIs_mobile_user(Integer is_mobile_user) {
        this.is_mobile_user = is_mobile_user;
    }

    public String getSubsname() {
        return subsname;
    }

    public void setSubsname(String subsname) {
        this.subsname = subsname;
    }

    public Integer getRegion() {
        return region;
    }

    public void setRegion(Integer region) {
        this.region = region;
    }

    public Long getOpening_cust_iD() {
        return opening_cust_iD;
    }

    public void setOpening_cust_iD(Long opening_cust_iD) {
        this.opening_cust_iD = opening_cust_iD;
    }

    public Long getUsing_cust_id() {
        return using_cust_id;
    }

    public void setUsing_cust_id(Long using_cust_id) {
        this.using_cust_id = using_cust_id;
    }

    public Long getAppdstatus() {
        return appdstatus;
    }

    public void setAppdstatus(Long appdstatus) {
        this.appdstatus = appdstatus;
    }

    public String getBes_status() {
        return bes_status;
    }

    public void setBes_status(String bes_status) {
        this.bes_status = bes_status;
    }

    public Long getModify_dept_id() {
        return modify_dept_id;
    }

    public void setModify_dept_id(Long modify_dept_id) {
        this.modify_dept_id = modify_dept_id;
    }

    public Long getInnet_months() {
        return innet_months;
    }

    public void setInnet_months(Long innet_months) {
        this.innet_months = innet_months;
    }

    public Integer getRemark_1() {
        return remark_1;
    }

    public void setRemark_1(Integer remark_1) {
        this.remark_1 = remark_1;
    }

    public Integer getRemark_2() {
        return remark_2;
    }

    public void setRemark_2(Integer remark_2) {
        this.remark_2 = remark_2;
    }

    public Integer getRemark_3() {
        return remark_3;
    }

    public void setRemark_3(Integer remark_3) {
        this.remark_3 = remark_3;
    }

    public Integer getRemark_4() {
        return remark_4;
    }

    public void setRemark_4(Integer remark_4) {
        this.remark_4 = remark_4;
    }

    public String getRemark_5() {
        return remark_5;
    }

    public void setRemark_5(String remark_5) {
        this.remark_5 = remark_5;
    }

    public String getRemark_6() {
        return remark_6;
    }

    public void setRemark_6(String remark_6) {
        this.remark_6 = remark_6;
    }

    public String getRemark_7() {
        return remark_7;
    }

    public void setRemark_7(String remark_7) {
        this.remark_7 = remark_7;
    }

    public Integer getTrans_status() {
        return trans_status;
    }

    public void setTrans_status(Integer trans_status) {
        this.trans_status = trans_status;
    }
}
