package com.xz.ignite.basefunction.query.util;

import javafx.util.Pair;
import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/11/28.
 */
public class ClassCache {
    private static Map<Class,List<Field>> map = new HashMap<>();

    private static <V> void init(V v){
        Class cz = v.getClass() ;
        if (!map.containsKey(cz)){
            Field[] fields = cz.getDeclaredFields() ;
            List<Field> list = new ArrayList<>() ;
            for (Field field:fields) {
                if (!field.isAnnotationPresent(QuerySqlField.class)){
                    continue;
                }
                field.setAccessible(true);
                list.add(field) ;
            }
            map.put(cz,list) ;
        }
    }

    /**
     * key 查询条件 value 查询参数值
     * @param v
     * @param <V>
     * @return
     */
    public static <V> Pair<String,Object[]> getBySqlQuery(V v){
        init(v);
        StringBuilder sb = new StringBuilder() ;
        List<Object> values = new ArrayList<>() ;
        List<Field> list = map.get(v.getClass()) ;
        list.forEach(field -> {
            try {
                Object o = field.get(v) ;
                if (o!=null){
                    values.add(o) ;
                    sb.append(" and ").append(field.getName()+"=? ") ;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        String retStr = sb.length()>0?sb.substring(4):sb.toString() ;
        Object[] objects = values.toArray(new Object[values.size()]) ;
        Pair<String,Object[]> pair = new Pair<>(retStr,objects) ;
        return pair ;
    }

    /**
     * key 查询整条语句 value 查询参数值
     * @param v
     * @param <V>
     * @return
     */
    public static <V> Pair<String,Object[]> getBySqlFieldsQuery(V v){
        Class cz = v.getClass() ;
        init(v);
        StringBuilder select = new StringBuilder() ;
        StringBuilder sb = new StringBuilder() ;
        List<Object> values = new ArrayList<>() ;
        List<Field> list = map.get(cz) ;
        list.forEach(field -> {
            try {
                Object o = field.get(v) ;
                String name = field.getName() ;
                select.append(" , ").append(name) ;
                if (o!=null){
                    values.add(o) ;
                    sb.append(" and ").append(name+"=? ") ;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        String selectStr = select.substring(2) ;
        String whereStr = sb.length()>0?sb.substring(4):sb.toString() ;
        String retStr = sb.length()>0?"select "+selectStr+" from "+cz.getSimpleName().toUpperCase()+" where "+whereStr:selectStr ;
        Object[] objects = values.toArray(new Object[values.size()]) ;
        Pair<String,Object[]> pair = new Pair<>(retStr,objects) ;
        return pair ;
    }


    public static <V> V construct(List<?> item, V v) {
        Class cz = v.getClass() ;
        List<Field> list = map.get(cz) ;
        V v2 = null;
        try {
            v2 = (V)v.getClass().newInstance();
            for (int i = 0; i < list.size(); i++) {
                Field field = list.get(i) ;
                field.set(v2,item.get(i));
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return v2 ;
    }
}
