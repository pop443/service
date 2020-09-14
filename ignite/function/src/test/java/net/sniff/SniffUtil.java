package net.sniff;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xz on 2020/9/14.
 */
public class SniffUtil {
    public static String getCommond(Pair<String,String> pair1,Pair<String,String> pair2){
        StringBuilder sb = new StringBuilder() ;
       // sb
                //.append("登录到 ")
                //.append(pair1.getKey())
                //.append(" 机器上")
                //.append("\r\n");
        sb.append("mtr -a ").append(pair1.getValue()).append(" -c 100 -r ").append(pair2.getKey()) ;
        return sb.toString() ;
    }

    public static void init(String name, List<Pair<String,String>> ignites, List<Pair<String,String>> zks, List<Pair<String,String>> apps) {
        try {
            int type = 1 ;
            String url = ignitedb.class.getClassLoader().getResource(name).getPath() ;
            BufferedReader br = new BufferedReader(new FileReader(url));

            String line = null;
            while ((line = br.readLine()) != null) {
                if ("集群".equals(line)){
                    type = 1 ;
                    continue;
                }else if("zk".equals(line)){
                    type = 2 ;
                    continue;
                }else if("应用".equals(line)){
                    type = 3 ;
                    continue;
                }
                String[] strs = line.split("\t") ;
                Pair<String,String> pair = new Pair<>(strs[0],strs[1]) ;
                if (type==1){
                    ignites.add(pair) ;
                }else if (type==2){
                    zks.add(pair) ;
                }else if (type==3){
                    apps.add(pair) ;
                }
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
