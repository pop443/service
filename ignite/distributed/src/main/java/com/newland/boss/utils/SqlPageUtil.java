package com.newland.boss.utils;

/**
 * Created by xz on 2020/6/1.
 */
public class SqlPageUtil {
    public static String getPageSql(String sql,String type,Long min,Long max){
        if (type.equals("mysql")){
            return sql+" limit "+min+","+max ;
        }else if (type.equals("oracle")){
            return "select * from (select rownum r,t.* from ("+sql+") t where rownum<="+max+" ) where r>"+min ;
        }
        return sql ;
    }
}
