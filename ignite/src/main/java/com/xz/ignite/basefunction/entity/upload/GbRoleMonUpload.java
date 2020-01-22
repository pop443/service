package com.xz.ignite.basefunction.entity.upload;

import com.xz.ignite.basefunction.entity.GbRoleMon;
import com.xz.ignite.utils.BeanUtil;
import com.xz.ignite.utils.IdGen;

/**
 * 6,000,000,000 60亿 GB信令解析规则前15位（月）测试使用60W数据
 */
public class GbRoleMonUpload extends BaseUpload<String,GbRoleMon> {
    public GbRoleMonUpload() {
        super(600000L);
    }

    public GbRoleMonUpload(Long allSize) {
        super(allSize);
    }

    @Override
    protected GbRoleMon getBean() {
        GbRoleMon gbRoleMon = new GbRoleMon();
        gbRoleMon.setMonth_number(BeanUtil.month_number(12));
        gbRoleMon.setStat_month(gbRoleMon.getMonth_number());
        gbRoleMon.setUser_id(BeanUtil.user_id(10000));
        //100个手机号
        gbRoleMon.setMsisdn(BeanUtil.msisdn(100) + "");
        gbRoleMon.setRule_id(BeanUtil.randomString(15));
        gbRoleMon.setTraffic_cnt(BeanUtil.traffic_cnt(1000));
        gbRoleMon.setGb_cnt(BeanUtil.randomNumber(100));
        gbRoleMon.setApp_cnt(BeanUtil.randomNumber(7));
        return gbRoleMon;
    }


    @Override
    protected String getKey(GbRoleMon gbRoleMon) {
        return IdGen.uuid();
    }
}
