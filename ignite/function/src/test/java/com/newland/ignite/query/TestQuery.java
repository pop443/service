package com.newland.ignite.query;

import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.query.FieldsQueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

/**
 * Created by xz on 2020/6/19.
 */
public class TestQuery {
    private Ignite ignite ;
    private IgniteCache igniteCache = null ;
    @Before
    public void Before(){
        ignite = IgniteUtil.getIgnite();
        //缓存名称
        igniteCache = ignite.cache("tttt") ;
    }
    @Test
    public void query(){
        //缓存映射sql名称
        String sql = "select _key,_val from NEWLAND.PARTITIONCUSTOBJ where _key like '3000%'" ;
        SqlFieldsQuery qry = new SqlFieldsQuery(sql) ;
        FieldsQueryCursor<List<?>> fieldsQueryCursor = null ;
        try {
            fieldsQueryCursor = igniteCache.query(qry) ;
            Iterator<List<?>> it = fieldsQueryCursor.iterator() ;
            while (it.hasNext()){
                List<?> item = it.next() ;
                if (item.size()==2){
                    String key = (String)item.get(0) ;
                    PartitionCustObj partitionCustObj = (PartitionCustObj)item.get(1) ;
                    System.out.println(key+"--"+partitionCustObj.toString());
                }else{
                    System.out.println("不正常");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //关闭游标
            if (fieldsQueryCursor!=null){
                fieldsQueryCursor.close();
            }
        }
    }

    @After
    public void After(){
        IgniteUtil.release(ignite);
    }
}
