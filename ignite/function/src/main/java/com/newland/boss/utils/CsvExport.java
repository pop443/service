package com.newland.boss.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * Created by xz on 2020/3/3.
 */
public class CsvExport {

    /**
     * 需要拼接好每一行的数据 只负责写入文件
     * @param list
     * @param file
     */
    public static void exportCsv(List<String> list, File file) throws Exception{
        if (!file.getName().endsWith(".csv")){
            throw new Exception("格式错误");
        }
        if (!file.getParentFile().exists()){
            file.getParentFile().mkdirs() ;
        }
        if (file.exists()){
            file.delete();
        }
        FileOutputStream fos = null ;
        OutputStreamWriter osw = null ;

        try {
            fos = new FileOutputStream(file) ;
            osw = new OutputStreamWriter(fos, "GBK");
            for (String s:list) {
                osw.write(s);
            }
        } catch (IOException e) {
            throw new Exception("导出错误");
        }finally {
            if (osw!=null){
                osw.flush();
                osw.close();
            }
            if (fos!=null){
                fos.close();
            }

        }

    }
}
