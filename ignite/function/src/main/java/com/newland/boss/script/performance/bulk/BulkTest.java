package com.newland.boss.script.performance.bulk;

import com.newland.boss.entity.performance.bulk.*;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.query.FieldsQueryCursor;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.cache.query.TextQuery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.cache.Cache;
import java.util.Iterator;
import java.util.List;


/**
 * Created by xz on 2020/2/9.
 */
public class BulkTest {
    private Ignite ignite = null ;
    private IgniteCache<String,JavaObj> igniteCache1 = null ;
    private IgniteCache<String,byte[]> igniteCache2 = null ;
    private IgniteCache<String,String> igniteCache3 = null ;
    private IgniteCache<String,TextObj> igniteCache4 = null ;

    @Before
    public void before(){
        ignite = IgniteUtil.getIgnite() ;
        igniteCache1 = ignite.getOrCreateCache(new JavaObjConfiguration().getCacheConfiguration()) ;
        igniteCache2 = ignite.getOrCreateCache(new ByteObjConfiguration().getCacheConfiguration()) ;
        igniteCache3 = ignite.getOrCreateCache(new JsonObjConfiguration().getCacheConfiguration()) ;
        igniteCache4 = ignite.getOrCreateCache(new TextObjConfiguration().getCacheConfiguration()) ;
    }

    @Test
    public void bytetest1(){
        byte[] bytes = igniteCache2.get("2000") ;
        System.out.println(new String(bytes));
    }

    @Test
    public void jsontest1(){
        String json = igniteCache3.get("2000") ;
        System.out.println(json);
    }

    @Test
    public void jsontest2(){
        SqlFieldsQuery sqlFieldsQuery = new SqlFieldsQuery("select * from NEWLAND.STRING limit 10 ") ;
        FieldsQueryCursor<List<?>> cursor = igniteCache3.query(sqlFieldsQuery) ;
        Iterator<List<?>> it = cursor.iterator() ;
        while (it.hasNext()){
            List<?> list = it.next() ;
            System.out.println(list.get(0)+"--"+list.get(1));
        }
        cursor.close();
    }

    @Test
    public void jsontest3(){
        TextQuery<String,String> textQuery = new TextQuery<>(String.class,"大",10) ;
        QueryCursor<Cache.Entry<String,String>> cursor = igniteCache3.query(textQuery) ;
        Iterator<Cache.Entry<String,String>> it = cursor.iterator() ;
        while (it.hasNext()){
            Cache.Entry<String,String> entry = it.next() ;
            System.out.println(entry.getKey()+"--"+entry.getValue());
        }
        cursor.close();
    }

    @Test
    public void texttest1(){
        TextObj json = igniteCache4.get("2000") ;
        System.out.println(json);
    }
    @Test
    public void texttest2(){
        SqlFieldsQuery sqlFieldsQuery = new SqlFieldsQuery("select * from NEWLAND.TEXTOBJ limit 10 ") ;
        FieldsQueryCursor<List<?>> cursor = igniteCache4.query(sqlFieldsQuery) ;
        Iterator<List<?>> it = cursor.iterator() ;
        while (it.hasNext()){
            List<?> list = it.next() ;
            System.out.println(list.get(0)+"--"+list.get(1));
        }
        cursor.close();
    }

    @Test
    public void texttest3(){
        TextQuery<String,TextObj> textQuery = new TextQuery<>(TextObj.class,"大",10) ;
        QueryCursor<Cache.Entry<String,TextObj>> cursor = igniteCache4.query(textQuery) ;
        Iterator<Cache.Entry<String,TextObj>> it = cursor.iterator() ;
        while (it.hasNext()){
            Cache.Entry<String,TextObj> entry = it.next() ;
            System.out.println(entry.getKey()+"--"+entry.getValue());
        }
        cursor.close();

    }

    @Test
    public void beantest1(){
        JavaObj javaObj = igniteCache1.get("2000") ;
        System.out.println(javaObj);
    }
    @Test
    public void beantest2(){
        SqlFieldsQuery sqlFieldsQuery = new SqlFieldsQuery("select * from NEWLAND.JAVAOBJ limit 10 ") ;
        FieldsQueryCursor<List<?>> cursor = igniteCache1.query(sqlFieldsQuery) ;
        Iterator<List<?>> it = cursor.iterator() ;
        while (it.hasNext()){
            List<?> list = it.next() ;
            System.out.println(list.get(0)+"--"+list.get(1));
        }
        cursor.close();
    }

    @Test
    public void beantest3(){
        TextQuery<String,JavaObj> textQuery = new TextQuery<>(JavaObj.class,"培元个随",10) ;
        QueryCursor<Cache.Entry<String,JavaObj>> cursor = igniteCache1.query(textQuery) ;
        Iterator<Cache.Entry<String,JavaObj>> it = cursor.iterator() ;
        while (it.hasNext()){
            Cache.Entry<String,JavaObj> entry = it.next() ;
            System.out.println(entry.getKey()+"--"+entry.getValue());
        }
        cursor.close();

    }


    @After
    public void after(){
        if (ignite!=null){
            IgniteUtil.release(ignite);
        }
    }


}
