package com.newland.ignite.upload.affinity;

import com.newland.ignite.upload.BaseUpload;
import com.newland.ignite.upload.entity.GrpMemberMon2;
import com.newland.ignite.upload.utils.BeanUtil;
import com.newland.ignite.utils.IdGen;

public class GrpMemberMon2AffinityUpload extends BaseUpload<GrpMemberMon2Key,GrpMemberMon2> {
    public GrpMemberMon2AffinityUpload() {
        super(400000L);
    }

    @Override
    protected GrpMemberMon2 getBean() {
        GrpMemberMon2 grpMemberMon2 = new GrpMemberMon2();
        //100000个客户编码
        grpMemberMon2.setCustomer_id(BeanUtil.user_id());
        grpMemberMon2.setTown_id("归属乡镇");
        //10000 个手机号
        grpMemberMon2.setPhone_number(BeanUtil.msisdn(10000) + "");
        //100 个号码类型
        grpMemberMon2.setPhone_type(BeanUtil.randomNumber(100));
        //15 个成员类别
        grpMemberMon2.setMember_class(BeanUtil.randomNumber(15));
        //10个成员类型
        grpMemberMon2.setMember_type(BeanUtil.randomNumber(10));
        grpMemberMon2.setKey_flag(BeanUtil.randomNumber(2));
        //100天
        grpMemberMon2.setStart_date(BeanUtil.randomDate(100));
        grpMemberMon2.setEnd_date(BeanUtil.randomDate(100));
        //12个月分区
        grpMemberMon2.setMonth_number(BeanUtil.randomNumber(12));
        // 50种用户品牌
        grpMemberMon2.setBrand_id(BeanUtil.randomNumber(50));
        //3大品牌
        grpMemberMon2.setBig_brand_p(BeanUtil.randomNumber(3));
        //100产品大类
        grpMemberMon2.setProduct_class(BeanUtil.randomNumber(100));
        //1000产品小类
        grpMemberMon2.setProduct_id(BeanUtil.randomNumber(1000));
        //8用户类型
        grpMemberMon2.setUser_status(BeanUtil.randomNumber(8));
        grpMemberMon2.setInnet_date(20101101);
        grpMemberMon2.setSuspend_date(0);
        grpMemberMon2.setTerminate_date(0);
        //20个等级的VIP
        grpMemberMon2.setVip_grade(BeanUtil.randomNumber(20));
        //0~50年龄区间
        grpMemberMon2.setAge(BeanUtil.randomNumber(50));
        grpMemberMon2.setFee_consume(10000D + BeanUtil.randomNumber(20000));
        grpMemberMon2.setFee_df(7500D + BeanUtil.randomNumber(3000));
        grpMemberMon2.setPay_type(BeanUtil.randomNumber(2));

        grpMemberMon2.setAmount_uplimit(0D);
        grpMemberMon2.setStat_month(0);

        grpMemberMon2.setUser_id(BeanUtil.user_id());
        grpMemberMon2.setName("名称" + grpMemberMon2.getUser_id());
        grpMemberMon2.setArea_id(BeanUtil.randomNumber(100) + 1000);
        return grpMemberMon2;
    }

    @Override
    protected GrpMemberMon2Key getKey(GrpMemberMon2 grpMemberMon2) {
        GrpMemberMon2Key grpMemberMon2Key = new GrpMemberMon2Key() ;
        grpMemberMon2Key.setId(IdGen.uuid());
        grpMemberMon2Key.setCustomer_id(grpMemberMon2.getCustomer_id());
        return grpMemberMon2Key;
    }
}
