package com.newland.ignite.query.api;

import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.query.FieldsQueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by xz on 2020/5/9.
 */
public class CommonQuery {
    private Ignite ignite ;
    private IgniteCache<String,String> igniteCache ;
    private String sql ;

    public CommonQuery(String sql) {
        ignite = IgniteUtil.getIgnite();
        igniteCache = ignite.getOrCreateCache("executSql");
        this.sql = sql;
    }

    public void start(){
        SqlFieldsQuery qry = new SqlFieldsQuery(sql) ;
        FieldsQueryCursor<List<?>> fieldsQueryCursor = null ;
        try {
            fieldsQueryCursor = igniteCache.query(qry) ;
            Iterator<List<?>> it = fieldsQueryCursor.iterator() ;
            while (it.hasNext()){
                List<?> item = it.next() ;
                System.out.println(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (fieldsQueryCursor!=null){
                fieldsQueryCursor.close();
            }
        }
        destory();
    }

    private void destory() {
        if (ignite!=null){
            ignite.close();
        }
    }


    public static void main(String[] args) {
        String sql = "SELECT KEYID, KEYUSERID, KEYUSERID2, KEYUSERSTR, KEYUSERSTR2, COMPLEXKEYITEM, ITEMID, ITEMUSERID, ITEMUSERID2, ITEMUSERSTR, ITEMUSERSTR2, COMPLEXVALUEITEM1, ITEM1ID, ITSTRINGS, OBJECTS, COMPLEXVALUEITEM2, ITEM2ID, ITEM2NAME, ID FROM PUBLIC.COMPLEXVALUE" ;
        CommonQuery commonQuery = new CommonQuery(sql) ;
        commonQuery.start();
    }

}
