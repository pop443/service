package com.newland.boss.script.features.export;

import com.newland.boss.entity.resource.FreeResource;
import com.newland.boss.entity.resource.FreeResourceConfiguration;
import com.newland.boss.script.features.BaseScript;
import com.newland.boss.utils.CsvExport;
import com.newland.ignite.query.api.BaseQuery;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 3.1.1 导出能力
 */
public class ExportScript extends BaseScript<String,FreeResource> {
    private File file ;
    private String sql ;

    public ExportScript(File file,String sql) {
        super(new FreeResourceConfiguration());
        this.file = file;
        this.sql = sql;
    }

    @Override
    public void work() {
        BaseQuery<String,FreeResource> baseQuery = new BaseQuery<>(ignite,cacheName) ;
        List<String> list = baseQuery.queryField2List(sql) ;

        String ret = "完成!" ;
        try {
            CsvExport.exportCsv(list,file);
        } catch (Exception e) {
            ret = e.getMessage() ;
        }
        System.out.println(ret);
    }

    public static void main(String[] args) throws Exception {
        String filePath = null ;
        String sql = "select id,name,age from FREERESOURCE";

        if (args.length==0){
            System.out.println("第一个参数为 导出文件.csv\r\n第二个参数为 sql谓词");
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
        ExportScript scirpt = new ExportScript(file,sql) ;
        scirpt.start();
    }

}
