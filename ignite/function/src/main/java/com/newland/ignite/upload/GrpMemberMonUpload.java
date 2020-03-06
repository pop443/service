package com.newland.ignite.upload;

import com.newland.ignite.upload.entity.GrpMemberMon;
import com.newland.ignite.upload.utils.BeanUtil;
import com.newland.ignite.utils.IdGen;

/**
 * 40,000,000 4000万 测试使用40W数据
 */
public class GrpMemberMonUpload extends BaseUpload<String,GrpMemberMon> {
    public GrpMemberMonUpload() {
        super(400000L);
    }

    public GrpMemberMonUpload(Long allSize) {
        super(allSize);
    }

    @Override
    protected GrpMemberMon getBean() {
        GrpMemberMon grpMemberMon = new GrpMemberMon();
        grpMemberMon.setTown_id("归属乡镇");
        //10000 个手机号
        grpMemberMon.setPhone_number(BeanUtil.msisdn(10000) + "");
        //100 个号码类型
        grpMemberMon.setPhone_type(BeanUtil.randomNumber(100));
        //15 个成员类别
        grpMemberMon.setMember_class(BeanUtil.randomNumber(15));
        //10个成员类型
        grpMemberMon.setMember_type(BeanUtil.randomNumber(10));
        grpMemberMon.setKey_flag(BeanUtil.randomNumber(2));
        //100天
        grpMemberMon.setStart_date(BeanUtil.randomDate(100));
        grpMemberMon.setEnd_date(BeanUtil.randomDate(100));
        //100000个客户编码
        grpMemberMon.setCustomer_id(10000000L + BeanUtil.randomNumber(100000));
        //12个月分区
        grpMemberMon.setMonth_number(BeanUtil.randomNumber(12));
        // 50种用户品牌
        grpMemberMon.setBrand_id(BeanUtil.randomNumber(50));
        //3大品牌
        grpMemberMon.setBig_brand_p(BeanUtil.randomNumber(3));
        //100产品大类
        grpMemberMon.setProduct_class(BeanUtil.randomNumber(100));
        //1000产品小类
        grpMemberMon.setProduct_id(BeanUtil.randomNumber(1000));
        //8用户类型
        grpMemberMon.setUser_status(BeanUtil.randomNumber(8));
        grpMemberMon.setInnet_date(20101101);
        grpMemberMon.setSuspend_date(0);
        grpMemberMon.setTerminate_date(0);
        //20个等级的VIP
        grpMemberMon.setVip_grade(BeanUtil.randomNumber(20));
        //0~50年龄区间
        grpMemberMon.setAge(BeanUtil.randomNumber(50));
        grpMemberMon.setFee_consume(10000D + BeanUtil.randomNumber(20000));
        grpMemberMon.setFee_df(7500D + BeanUtil.randomNumber(3000));
        grpMemberMon.setPay_type(BeanUtil.randomNumber(2));

        grpMemberMon.setAmount_uplimit(0D);
        grpMemberMon.setStat_month(0);

        grpMemberMon.setUser_id(BeanUtil.user_id());
        grpMemberMon.setName("名称" + grpMemberMon.getUser_id());
        grpMemberMon.setArea_id(BeanUtil.randomNumber(100) + 1000);
        return grpMemberMon;
    }

    @Override
    protected String getKey(GrpMemberMon grpMemberMon) {
        return IdGen.uuid();
    }
}
