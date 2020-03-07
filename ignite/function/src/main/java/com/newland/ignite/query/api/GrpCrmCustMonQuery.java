package com.newland.ignite.query.api;

import com.newland.ignite.label.entity.GrpCrmCustMon;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;

import java.util.List;

/**
 * Created by Administrator on 2020/1/3.
 */
public class GrpCrmCustMonQuery extends BaseQuery<String,GrpCrmCustMon> {
    public GrpCrmCustMonQuery(Ignite ignite) {
        super(ignite, GrpCrmCustMon.class.getSimpleName().toUpperCase());
    }

    public static void main(String[] args) {
        Ignite ignite = IgniteUtil.getIgnite() ;
        GrpCrmCustMonQuery query = new GrpCrmCustMonQuery(ignite) ;
        GrpCrmCustMon grpCrmCustMon = new GrpCrmCustMon() ;
        grpCrmCustMon.setArea_id(1031);
        List<GrpCrmCustMon> list = query.queryField2List(grpCrmCustMon) ;
        list.forEach(System.out::println);
        System.out.println("end");
    }

}
