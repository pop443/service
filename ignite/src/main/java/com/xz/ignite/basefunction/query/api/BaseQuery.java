package com.xz.ignite.basefunction.query.api;

import com.xz.ignite.basefunction.query.util.ClassCache;
import javafx.util.Pair;
import org.apache.commons.lang3.StringUtils;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.query.FieldsQueryCursor;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.cache.query.SqlQuery;

import javax.cache.Cache;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2019/11/28.
 */
public class BaseQuery<K,V>{

    private IgniteCache<K, V> tempCache ;

    private String order ;

    BaseQuery(Ignite ignite, String name) {
        this.tempCache = ignite.cache(name);
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    private SqlQuery<K, V> getSqlQuery(V v) {
        Pair<String,Object[]> pair = ClassCache.getBySqlQuery(v) ;
        String sql = pair.getKey() ;
        if (StringUtils.isNotBlank(order)){
            sql = sql +" "+ order ;
        }
        SqlQuery<K, V> cSqlQuery = new SqlQuery<>(v.getClass(), sql) ;
        cSqlQuery.setReplicatedOnly(true).setArgs(pair.getValue());
        return cSqlQuery;
    }

    private SqlFieldsQuery getSqlFieldsQuery(V v) {
        Pair<String,Object[]> pair = ClassCache.getBySqlFieldsQuery(v) ;
        String sql = pair.getKey() ;
        if (StringUtils.isNotBlank(order)){
            sql = sql +" "+ order ;
        }
        SqlFieldsQuery sqlFieldsQuery = new SqlFieldsQuery(sql) ;
        sqlFieldsQuery.setReplicatedOnly(true).setArgs(pair.getValue());
        return sqlFieldsQuery;
    }

    /**
     * 简单按条件查询
     * @param v
     * @return
     */
    public List<Cache.Entry<K,V>> query( V v ){
        SqlQuery<K, V> cSqlQuery = getSqlQuery(v);
        return tempCache.query(cSqlQuery).getAll();
    }

    public List<V> query2List( V v ){
        SqlQuery<K, V> cSqlQuery = getSqlQuery(v);
        QueryCursor queryCursor = tempCache.query(cSqlQuery);
        List<V> retList = new ArrayList<>() ;
        try {
            Iterator<Cache.Entry<K,V>> it = queryCursor.iterator() ;
            while (it.hasNext()){
                Cache.Entry<K,V> entry = it.next() ;
                retList.add(entry.getValue()) ;
            }
        } finally {
            queryCursor.close();
        }
        return retList;
    }

    public List<V> queryField2List( V v ){
        SqlFieldsQuery qry = getSqlFieldsQuery(v) ;
        List<V> retList = new ArrayList<>() ;
        FieldsQueryCursor<List<?>> fieldsQueryCursor = tempCache.query(qry) ;
        try {
            Iterator<List<?>> it = fieldsQueryCursor.iterator() ;
            while (it.hasNext()){
                List<?> item = it.next() ;
                V v2 = ClassCache.construct(item,v) ;
                retList.add(v2) ;
            }
        } finally {
            fieldsQueryCursor.close();
        }
        return retList ;
    }
    /**
     * 简单按条件查询数量
     * @param v
     * @return
     */
    public Long count( V v ){
        Pair<String,Object[]> pair = ClassCache.getBySqlQuery(v) ;
        String countSql = "select count(1) from "+tempCache.getName() ;

        if (!"".equals(pair.getKey().trim())){
            countSql = countSql+" where "+pair.getKey() ;
        }
        System.out.println("----------"+countSql);
        SqlFieldsQuery countQuery = new SqlFieldsQuery(countSql) ;
        countQuery.setReplicatedOnly(true).setArgs(pair.getValue());
        return (Long)tempCache.query(countQuery).getAll().get(0).get(0);
    }

    public void release(){
        tempCache.close();
    }
}
