package com.newland.boss.script.features.export;

import com.newland.boss.entity.resource.FreeResource;
import com.newland.boss.entity.resource.FreeResourceConfiguration;
import com.newland.boss.script.features.BaseScript;
import com.newland.boss.utils.CsvExport;
import com.newland.ignite.query.api.BaseQuery;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.query.FieldsQueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 3.1.1 导出能力
 */
public class ExportScript2 {
    private Ignite ignite ;
    private IgniteCache igniteCache ;

    public ExportScript2() {
        ignite = IgniteUtil.getIgnite() ;
        igniteCache = ignite.getOrCreateCache("temp") ;
    }

    public void start() {
        List<String> list = queryField2List("SELECT _key,LIST1, LIST2, STRINGS, OBJECTS FROM PUBLIC.COMPLEXLIST ") ;
        list.forEach(System.out::println);
        close();
    }
    private void close (){
        if (ignite!=null){
            ignite.close();
        }
    }

    public List<String> queryField2List(String sql) {
        SqlFieldsQuery qry = new SqlFieldsQuery(sql) ;

        List<String> retList = new ArrayList<>() ;
        FieldsQueryCursor<List<?>> fieldsQueryCursor = igniteCache.query(qry) ;
        try {
            Iterator<List<?>> it = fieldsQueryCursor.iterator() ;
            while (it.hasNext()){
                List<?> item = it.next() ;
                StringBuilder sb = new StringBuilder() ;
                item.forEach(x->{
                    sb.append(",\"").append(x).append("\"");
                });
                sb.append("\r\n");
                retList.add(sb.substring(1));
            }
        } finally {
            fieldsQueryCursor.close();
        }
        return retList ;
    }

    public static void main(String[] args) throws Exception {
        ExportScript2 scirpt = new ExportScript2() ;
        scirpt.start();
    }

}
