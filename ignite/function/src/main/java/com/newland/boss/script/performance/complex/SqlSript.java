package com.newland.boss.script.performance.complex;

import com.newland.boss.utils.CsvExport;
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
 * Created by xz on 2020/4/22.
 */
public class SqlSript {
    private Ignite ignite ;
    private IgniteCache<String,String> igniteCache ;
    private File file ;
    private String sql ;

    public SqlSript(File file, String sql) {
        ignite = IgniteUtil.getIgnite();
        igniteCache = ignite.getOrCreateCache("sqltemp");
        this.file = file;
        this.sql = sql;
    }

    public void start() {
        List<String> list = queryField2List(sql) ;
        if (sql.startsWith("explain")){
            for (String string:list) {
                System.out.println(string);
            }
            System.out.println("执行计划不输出文件");
        }else {
            String ret = "完成!" ;
            try {
                CsvExport.exportCsv(list,file);
            } catch (Exception e) {
                ret = e.getMessage() ;
            }
            System.out.println(ret);
        }
    }

    private List<String> queryField2List(String sql) {
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
        String filePath = null ;
        String sql = "select id,name,age from FREERESOURCE";

        if (args.length==0){
            System.out.println("第一个参数为 导出文件.csv\r\n第二个参数为 sql");
            return;
        }else if (args.length==1){
            filePath = args[0] ;
        }else if (args.length==2){
            filePath = args[0] ;
            sql = args[1] ;
        }else if (args.length>2){
            System.out.println("参数为 导出文件.csv");
            return;
        }
        System.out.println(sql);
        File file = new File(filePath) ;
        SqlSript scirpt = new SqlSript(file,sql) ;
        scirpt.start();
    }
}
