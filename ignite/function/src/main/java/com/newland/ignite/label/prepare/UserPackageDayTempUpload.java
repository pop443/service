package com.newland.ignite.label.prepare;

import com.newland.ignite.label.entity.UserPackageDayTemp;
import com.newland.ignite.label.utils.BeanUtil;
import com.newland.ignite.label.utils.IdGen;

import java.sql.Timestamp;

/**
 * 400,000,000 4亿数据      测试使用40W数据
 */
public class UserPackageDayTempUpload extends BaseUpload<String,UserPackageDayTemp> {
    public UserPackageDayTempUpload() {
        super(400000L);
    }

    public UserPackageDayTempUpload(Long allSize) {
        super(allSize);
    }

    @Override
    protected UserPackageDayTemp getBean() {
        UserPackageDayTemp userPackageDayTemp = new UserPackageDayTemp();

        // 300 渠道标识
        userPackageDayTemp.setChannel_id("渠道" + BeanUtil.randomNumber(300));
        // 20 渠道类型
        userPackageDayTemp.setChannel_type("渠道类型" + BeanUtil.randomNumber(20));
        //商品实体编码
        userPackageDayTemp.setOffering_inst_id(BeanUtil.user_id());
        userPackageDayTemp.setCancel_time(userPackageDayTemp.getEnd_time());
        userPackageDayTemp.setCancelorgid(BeanUtil.randomNumber(20) + "");
        //5个取消渠道类型
        userPackageDayTemp.setCancelaccesstype("" + BeanUtil.randomNumber(5));
        userPackageDayTemp.setCanceldate(20151212);
        userPackageDayTemp.setCanceloid(System.currentTimeMillis());
        userPackageDayTemp.setCanceloperid("取消员" + userPackageDayTemp.getCanceloid());

        userPackageDayTemp.setEnd_date(20101102);
        userPackageDayTemp.setEnd_time(new Timestamp(System.currentTimeMillis()));
        userPackageDayTemp.setApply_date(20101102);
        userPackageDayTemp.setApply_time(userPackageDayTemp.getEnd_time());
        userPackageDayTemp.setStart_date(20101102);
        userPackageDayTemp.setStart_time(userPackageDayTemp.getEnd_time());
        //10天分区
        userPackageDayTemp.setDay_number(BeanUtil.randomNumber(10));
        userPackageDayTemp.setState_date(20191230);

        userPackageDayTemp.setUser_id(BeanUtil.user_id());
        userPackageDayTemp.setArea_id(BeanUtil.randomNumber(100) + 1000);
        userPackageDayTemp.setPackage_code(BeanUtil.randomNumber(1000) + 10000L);
        // 5种状态
        userPackageDayTemp.setState(BeanUtil.randomNumber(5));
        userPackageDayTemp.setOperator_id(userPackageDayTemp.getUser_id());

        userPackageDayTemp.setBusiness_id(BeanUtil.randomNumber(100) + 1000L);
        //10000 产品编码
        userPackageDayTemp.setProdid("产品编码" + BeanUtil.randomNumber(10000));

        userPackageDayTemp.setNet_type("网络类型" + BeanUtil.randomNumber(20));
        userPackageDayTemp.setGrpsubsid(BeanUtil.randomNumber(10000) + 10000L);
        userPackageDayTemp.setRegtype("注册类型" + BeanUtil.randomNumber(20));

        userPackageDayTemp.setApplyoid(userPackageDayTemp.getUser_id());
        return userPackageDayTemp;
    }

    @Override
    protected String getKey(UserPackageDayTemp userPackageDayTemp) {
        return IdGen.uuid();
    }
}
