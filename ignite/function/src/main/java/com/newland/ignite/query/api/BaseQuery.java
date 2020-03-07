package com.newland.ignite.query.api;

import com.newland.boss.entity.resource.FreeResource;
import com.newland.ignite.query.util.ClassCache;
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

    public BaseQuery(Ignite ignite, String name) {
        this.tempCache = ignite.cache(name);
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }


    private SqlFieldsQuery getSqlFieldsQuery(V v) {
        Pair<String,Object[]> pair = ClassCache.getBySqlFieldsQuery(v) ;
        String sql = pair.getKey() ;
        if (StringUtils.isNotBlank(order)){
            sql = sql +" "+ order ;
        }
        SqlFieldsQuery sqlFieldsQuery = new SqlFieldsQuery(sql) ;
        sqlFieldsQuery.setArgs(pair.getValue());
        return sqlFieldsQuery;
    }


    public List<V> queryField2List( V v ){
        SqlFieldsQuery qry = getSqlFieldsQuery(v) ;
        List<V> retList = new ArrayList<>() ;
        FieldsQueryCursor<List<?>> fieldsQueryCursor = tempCache.query(qry) ;
        try {
            Iterator<List<?>> it = fieldsQueryCursor.iterator() ;
            while (it.hasNext()){
                List<?> item = it.next() ;
                V v2 = ClassCache.construct(item,v ) ;
                retList.add(v2) ;
            }
        } finally {
            fieldsQueryCursor.close();
        }
        return retList ;
    }

    public List<V> queryField2List(String sql, Class<V> cz) {
        SqlFieldsQuery qry = new SqlFieldsQuery(sql) ;
        List<V> retList = new ArrayList<>() ;
        FieldsQueryCursor<List<?>> fieldsQueryCursor = tempCache.query(qry) ;
        try {
            Iterator<List<?>> it = fieldsQueryCursor.iterator() ;
            while (it.hasNext()){
                List<?> item = it.next() ;
                V v2 = ClassCache.construct(item,cz ) ;
                retList.add(v2) ;
            }
        } finally {
            fieldsQueryCursor.close();
        }
        return retList ;
    }

    public Long insert(String sql, Object[] params) {
        SqlFieldsQuery qry = new SqlFieldsQuery(sql) ;
        qry.setArgs(params);
        FieldsQueryCursor<List<?>> fieldsQueryCursor = tempCache.query(qry) ;
        return (Long) fieldsQueryCursor.getAll().get(0).get(0) ;
    }
    public Long update(String sql, Object[] params) {
        SqlFieldsQuery qry = new SqlFieldsQuery(sql) ;
        qry.setArgs(params);
        FieldsQueryCursor<List<?>> fieldsQueryCursor = tempCache.query(qry) ;
        return (Long) fieldsQueryCursor.getAll().get(0).get(0) ;
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
        countQuery.setArgs(pair.getValue());
        return (Long)tempCache.query(countQuery).getAll().get(0).get(0);
    }

    public void release(){
        tempCache.close();
    }



}
