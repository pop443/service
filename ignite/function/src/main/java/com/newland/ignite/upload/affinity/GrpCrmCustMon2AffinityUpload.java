package com.newland.ignite.upload.affinity;

import com.newland.ignite.upload.BaseUpload;
import com.newland.ignite.upload.entity.GrpCrmCustMon2;
import com.newland.ignite.upload.utils.BeanUtil;

import java.sql.Timestamp;

public class GrpCrmCustMon2AffinityUpload extends BaseUpload<Long,GrpCrmCustMon2> {
    public GrpCrmCustMon2AffinityUpload() {
        super(500000L);
    }

    @Override
    protected GrpCrmCustMon2 getBean() {
        GrpCrmCustMon2 grpCrmCustMon2 = new GrpCrmCustMon2();
        //100000个客户编码
        grpCrmCustMon2.setCustomer_id(BeanUtil.user_id());
        //100个地区
        grpCrmCustMon2.setArea_id(BeanUtil.randomNumber(100) + 1000);
        //5个状态
        grpCrmCustMon2.setStatus(BeanUtil.randomNumber(5) + "");
        grpCrmCustMon2.setCreatetimestamp(new Timestamp(System.currentTimeMillis()));
        //20个渠道
        grpCrmCustMon2.setOrgid(BeanUtil.randomNumber(20) + "");
        //100个手机号
        grpCrmCustMon2.setLinkphone(BeanUtil.msisdn(100) + "");
        grpCrmCustMon2.setCustmgr("custmgr");
        grpCrmCustMon2.setAddress("新城科技园企业加速器");
        grpCrmCustMon2.setCustmgrname("经理名");
        grpCrmCustMon2.setCountryid(210000 + BeanUtil.randomNumber(10) + "");
        grpCrmCustMon2.setAnnual_sales(100000L + BeanUtil.randomNumber(100));
        grpCrmCustMon2.setAreatype("区域类型");
        grpCrmCustMon2.setCorpscope("");
        //100个部门
        grpCrmCustMon2.setDeparttype(1000 + BeanUtil.randomNumber(100) + "");
        //公司成员数 10000多人
        grpCrmCustMon2.setEmplyeenum(10000 + BeanUtil.randomNumber(100));
        //100个聚类市场编码
        grpCrmCustMon2.setGathermarketid(1000 + BeanUtil.randomNumber(100) + "");
        grpCrmCustMon2.setGathertype("聚类类型" + grpCrmCustMon2.getGathermarketid());
        //100个聚类市场编码
        grpCrmCustMon2.setGroupid(1000 + BeanUtil.randomNumber(100) + "");
        grpCrmCustMon2.setGroupname("集团名称" + grpCrmCustMon2.getGroupid());
        grpCrmCustMon2.setGroupvalue("市场价值" + 1000 + BeanUtil.randomNumber(100));
        //100个入网时间
        grpCrmCustMon2.setInnettimestamp(BeanUtil.randomNumber(100));
        //是否聚类市场
        grpCrmCustMon2.setIsgathermarket(BeanUtil.randomNumber(2) + "");
        grpCrmCustMon2.setIsphoto(BeanUtil.randomNumber(2) + "");
        //10个付费类型
        grpCrmCustMon2.setPaymode(BeanUtil.randomNumber(10) + "");
        grpCrmCustMon2.setIstrue(BeanUtil.randomNumber(2));
        grpCrmCustMon2.setMainproduct("主题产品");
        //12个月分区
        grpCrmCustMon2.setMonth_number(BeanUtil.randomNumber(12));
        //34个省
        grpCrmCustMon2.setProvince(BeanUtil.randomNumber(34) + "");
        //300个地域
        grpCrmCustMon2.setRegion(BeanUtil.randomNumber(300));
        grpCrmCustMon2.setStat_month(grpCrmCustMon2.getMonth_number());
        //10个状态
        grpCrmCustMon2.setStatus(BeanUtil.randomNumber(10) + "");
        grpCrmCustMon2.setUnitkind("单位类型");

        return grpCrmCustMon2 ;
    }

    @Override
    protected Long getKey(GrpCrmCustMon2 grpCrmCustMon2) {
        return grpCrmCustMon2.getCustomer_id();
    }
}
