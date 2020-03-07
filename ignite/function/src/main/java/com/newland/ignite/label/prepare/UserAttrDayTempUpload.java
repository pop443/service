package com.newland.ignite.label.prepare;

import com.newland.ignite.label.entity.UserAttrDayTemp;
import com.newland.ignite.label.utils.BeanUtil;
import com.newland.ignite.label.utils.IdGen;

/**
 * 80,000,000 8000W 测试使用40W数据
 */
public class UserAttrDayTempUpload extends BaseUpload<String,UserAttrDayTemp> {
    public UserAttrDayTempUpload() {
        super(400000L);
    }

    public UserAttrDayTempUpload(Long allSize) {
        super(allSize);
    }

    @Override
    protected UserAttrDayTemp getBean() {
        UserAttrDayTemp userAttrDayTemp = new UserAttrDayTemp();
        //30 天分区
        userAttrDayTemp.setDay_number(BeanUtil.randomNumber(30));
        userAttrDayTemp.setStat_date(Integer.parseInt("201912" + String.format("%02d", userAttrDayTemp.getDay_number())));
        userAttrDayTemp.setUser_id(BeanUtil.user_id());
        userAttrDayTemp.setName("名称" + userAttrDayTemp.getUser_id());
        //100000个客户编码
        userAttrDayTemp.setCustomer_id(10000000L + BeanUtil.randomNumber(100000));
        userAttrDayTemp.setMsisdn(BeanUtil.msisdn());
        // 50种用户品牌
        userAttrDayTemp.setBrand_id(BeanUtil.randomNumber(50));
        //3大品牌
        userAttrDayTemp.setBig_brand_p(BeanUtil.randomNumber(3));
        //100产品大类
        userAttrDayTemp.setProduct_class(BeanUtil.randomNumber(100));
        //1000产品小类
        userAttrDayTemp.setProduct_id(Long.parseLong(BeanUtil.randomNumber(1000) + ""));
        userAttrDayTemp.setArea_id(BeanUtil.randomNumber(100) + 1000);
        userAttrDayTemp.setIc_no("320106199005050505");
        userAttrDayTemp.setAge(BeanUtil.randomNumber(50));
        userAttrDayTemp.setSex(BeanUtil.randomNumber(2));
        //8用户类型
        userAttrDayTemp.setUser_status(BeanUtil.randomNumber(8));
        userAttrDayTemp.setInnet_date(20101101);
        userAttrDayTemp.setApply_date(20101102);
        userAttrDayTemp.setSuspend_date(0);
        userAttrDayTemp.setTerminate_date(0);
        userAttrDayTemp.setIs_full(BeanUtil.randomNumber(2));
        userAttrDayTemp.setFirstcall_date(0);
        userAttrDayTemp.setFirstcall_flag(1);
        //10000 营业点
        userAttrDayTemp.setUser_site(Long.parseLong(BeanUtil.randomNumber(10000) + ""));
        userAttrDayTemp.setUser_type(BeanUtil.randomNumber(100));
        userAttrDayTemp.setPublic_flag(BeanUtil.randomNumber(2));
        userAttrDayTemp.setIs_group_user(BeanUtil.randomNumber(2));
        userAttrDayTemp.setIs_vip(BeanUtil.randomNumber(2));
        //20个等级的VIP
        userAttrDayTemp.setVip_grade(BeanUtil.randomNumber(20));
        //20个等级的VIP类型
        userAttrDayTemp.setVip_type(BeanUtil.randomNumber(20));
        userAttrDayTemp.setFee_consume(1000D + BeanUtil.randomNumber(1000));
        userAttrDayTemp.setTown_id(BeanUtil.randomNumber(1000) + "");

        userAttrDayTemp.setContect_tel(userAttrDayTemp.getMsisdn() + "");
        userAttrDayTemp.setContect_addr("地址：" + userAttrDayTemp.getMsisdn());
        userAttrDayTemp.setContect_email(userAttrDayTemp.getMsisdn() + "@139.com");
        userAttrDayTemp.setAccount_id(userAttrDayTemp.getMsisdn());
        userAttrDayTemp.setImsi(userAttrDayTemp.getMsisdn());
        //3证件类型
        userAttrDayTemp.setIc_type(BeanUtil.randomNumber(3));
        //10客户类型
        userAttrDayTemp.setCustomer_type(BeanUtil.randomNumber(10));
        userAttrDayTemp.setIc_addr("证件地址" + (10000L + BeanUtil.randomNumber(1000)));

        userAttrDayTemp.setVip_intype(BeanUtil.randomNumber(2));
        userAttrDayTemp.setVip_indate(20121230);
        userAttrDayTemp.setRegstatus(BeanUtil.randomNumber(2));

        userAttrDayTemp.setCertno("320106199005050505");
        //10个付费类型
        userAttrDayTemp.setPaymode(BeanUtil.randomNumber(10) + "");

        userAttrDayTemp.setEnums(userAttrDayTemp.getUser_id() + "");
        userAttrDayTemp.setIs_mobile_user(BeanUtil.randomNumber(2));

        userAttrDayTemp.setSubsname("用户姓名" + userAttrDayTemp.getUser_id());

        //300个地域
        userAttrDayTemp.setRegion(BeanUtil.randomNumber(300));
        userAttrDayTemp.setOpening_cust_iD(userAttrDayTemp.getUser_id());
        userAttrDayTemp.setUsing_cust_id(userAttrDayTemp.getUser_id());
        userAttrDayTemp.setAppdstatus(Long.parseLong(BeanUtil.randomNumber(2) + ""));
        userAttrDayTemp.setBes_status(BeanUtil.randomNumber(2) + "");
        userAttrDayTemp.setModify_dept_id(0L);
        userAttrDayTemp.setInnet_months(Long.parseLong(BeanUtil.randomNumber(50) + ""));
        userAttrDayTemp.setTrans_status(BeanUtil.randomNumber(3));

        return userAttrDayTemp;
    }

    @Override
    protected String getKey(UserAttrDayTemp userAttrDayTemp) {
        return IdGen.uuid();
    }
}
