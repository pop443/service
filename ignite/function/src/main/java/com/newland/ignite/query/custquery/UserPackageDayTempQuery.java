package com.newland.ignite.query.custquery;

import com.newland.ignite.label.entity.GrpCrmCustMon;
import com.newland.ignite.label.entity.UserPackageDayTemp;
import com.newland.ignite.query.api.BaseQuery;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;

import java.util.List;

/**
 * Created by Administrator on 2020/1/3.
 */
public class UserPackageDayTempQuery extends BaseQuery<String,UserPackageDayTemp> {
    public UserPackageDayTempQuery(Ignite ignite) {
        super(ignite, UserPackageDayTemp.class.getSimpleName().toUpperCase());
    }

    public static void main(String[] args) {
        Ignite ignite = IgniteUtil.getIgnite() ;
        UserPackageDayTempQuery query = new UserPackageDayTempQuery(ignite) ;
        List<String> list = query.queryField2List("explain SELECT _key,user_id,user_id2 FROM NEWLAND.USERPACKAGEDAYTEMP t WHERE t._key LIKE '200000000000000%' ") ;
        list.forEach(System.out::println);
        System.out.println("end");
        ignite.close();
    }

}
