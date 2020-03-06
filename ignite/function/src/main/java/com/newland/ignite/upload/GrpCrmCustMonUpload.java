package com.newland.ignite.upload;

import com.newland.ignite.upload.entity.GrpCrmCustMon;
import com.newland.ignite.upload.utils.BeanUtil;
import com.newland.ignite.utils.IdGen;

import java.sql.Timestamp;

/**
 * 10,000,000 1000万 集团客户资料（新）月 总数据 3893MB 测试使用50W数据
 */
public class GrpCrmCustMonUpload extends BaseUpload<String,GrpCrmCustMon> {
    public GrpCrmCustMonUpload() {
        super(500000L);
    }

    @Override
    protected GrpCrmCustMon getBean() {
        GrpCrmCustMon grpCrmCustMon = new GrpCrmCustMon();
        //100个地区
        grpCrmCustMon.setArea_id(BeanUtil.randomNumber(100) + 1000);
        //5个状态
        grpCrmCustMon.setStatus(BeanUtil.randomNumber(5) + "");
        grpCrmCustMon.setCreatetimestamp(new Timestamp(System.currentTimeMillis()));
        //20个渠道
        grpCrmCustMon.setOrgid(BeanUtil.randomNumber(20) + "");
        //100个手机号
        grpCrmCustMon.setLinkphone(BeanUtil.msisdn(100) + "");
        grpCrmCustMon.setCustmgr("custmgr");
        grpCrmCustMon.setAddress("新城科技园企业加速器");
        grpCrmCustMon.setCustmgrname("经理名");
        grpCrmCustMon.setCountryid(210000 + BeanUtil.randomNumber(10) + "");
        grpCrmCustMon.setAnnual_sales(100000L + BeanUtil.randomNumber(100));
        grpCrmCustMon.setAreatype("区域类型");
        grpCrmCustMon.setCorpscope("");
        //100个部门
        grpCrmCustMon.setDeparttype(1000 + BeanUtil.randomNumber(100) + "");
        //公司成员数 10000多人
        grpCrmCustMon.setEmplyeenum(10000 + BeanUtil.randomNumber(100));
        //100个聚类市场编码
        grpCrmCustMon.setGathermarketid(1000 + BeanUtil.randomNumber(100) + "");
        grpCrmCustMon.setGathertype("聚类类型" + grpCrmCustMon.getGathermarketid());
        //100个聚类市场编码
        grpCrmCustMon.setGroupid(1000 + BeanUtil.randomNumber(100) + "");
        grpCrmCustMon.setGroupname("集团名称" + grpCrmCustMon.getGroupid());
        grpCrmCustMon.setGroupvalue("市场价值" + 1000 + BeanUtil.randomNumber(100));
        //100个入网时间
        grpCrmCustMon.setInnettimestamp(BeanUtil.randomNumber(100));
        //是否聚类市场
        grpCrmCustMon.setIsgathermarket(BeanUtil.randomNumber(2) + "");
        grpCrmCustMon.setIsphoto(BeanUtil.randomNumber(2) + "");
        //10个付费类型
        grpCrmCustMon.setPaymode(BeanUtil.randomNumber(10) + "");
        grpCrmCustMon.setIstrue(BeanUtil.randomNumber(2));
        grpCrmCustMon.setMainproduct("主题产品");
        //12个月分区
        grpCrmCustMon.setMonth_number(BeanUtil.randomNumber(12));
        //34个省
        grpCrmCustMon.setProvince(BeanUtil.randomNumber(34) + "");
        //300个地域
        grpCrmCustMon.setRegion(BeanUtil.randomNumber(300));
        grpCrmCustMon.setStat_month(grpCrmCustMon.getMonth_number());
        //10个状态
        grpCrmCustMon.setStatus(BeanUtil.randomNumber(10) + "");
        grpCrmCustMon.setUnitkind("单位类型");
        //100000个客户编码
        grpCrmCustMon.setCustomer_id(10000000L + BeanUtil.randomNumber(100000));


        return grpCrmCustMon;
    }


    @Override
    protected String getKey(GrpCrmCustMon grpCrmCustMon) {
        return IdGen.uuid();
    }
}
