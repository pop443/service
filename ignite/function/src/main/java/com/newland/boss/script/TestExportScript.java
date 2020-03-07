package com.newland.boss.script;

import com.newland.boss.entity.resource.FreeResource;
import com.newland.boss.entity.resource.FreeResourceConfiguration;
import com.newland.boss.utils.CsvExport;
import com.newland.ignite.query.api.BaseQuery;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 3.1.1 导出能力
 */
public class TestExportScript extends BaseScript<String,FreeResource> {
    private File file ;

    public TestExportScript(File file) {
        super(new FreeResourceConfiguration());
        this.file = file;
    }

    @Override
    public void work() {
        igniteCache.removeAll();
        Map<String,FreeResource> map = new HashMap<>() ;
        for (int i = 0; i < 10; i++) {
            String key = i+"" ;
            map.put(key,new FreeResource(key,key,i%2)) ;
        }
        igniteCache.putAll(map);

        FreeResource queryItem = new FreeResource() ;
        queryItem.setAge(1);
        BaseQuery<String,FreeResource> baseQuery = new BaseQuery<>(ignite,cacheName) ;
        String sql = " select * from FREERESOURCE where age>0 " ;
        List<FreeResource> list = baseQuery.queryField2List(sql,FreeResource.class) ;

        List<String> exportList = new ArrayList<>() ;
        exportList.add("id,名称,年龄\n");
        list.forEach(freeResource->{
            exportList.add(freeResource.getId()+","+freeResource.getName()+","+freeResource.getAge()+"\n");
        });
        String ret = "完成!" ;
        try {
            CsvExport.exportCsv(exportList,file);
        } catch (Exception e) {
            ret = e.getMessage() ;
        }
        System.out.println(ret);
    }

    public static void main(String[] args) {
        if (args.length==0){
            System.out.println("参数为 导出文件.csv");
            return;
        }
        File file = new File(args[0]) ;
        TestExportScript scirpt = new TestExportScript(file) ;
        scirpt.start();
    }

}
