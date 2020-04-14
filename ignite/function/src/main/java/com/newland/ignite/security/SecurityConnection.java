package com.newland.ignite.security;

import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xz on 2020/4/13.
 */
public class SecurityConnection {

    private Ignite ignite ;
    private IgniteCache<String,Security> igniteCache ;

    public SecurityConnection() {
        ignite = IgniteUtil.getIgniteByXml("ssl/node-config-manyDS-security.xml") ;
        igniteCache = ignite.getOrCreateCache(new SecurityConfiguration().getCacheConfiguration()) ;
    }

    private void start() {
        /*Map<String,Security> map = new HashMap<>() ;
        for (int i = 0; i < 10; i++) {
            String key = i+"" ;
            Security security = new Security(key,key) ;
            map.put(security.getId(),security) ;
        }
        igniteCache.putAll(map);*/

        System.out.println(igniteCache.get("8"));


        destory();
    }

    private void destory() {
        if (igniteCache!=null){
            igniteCache.close();
        }
        if (ignite!=null){
            ignite.close();
        }
    }


    public static void main(String[] args) {
        SecurityConnection connection = new SecurityConnection() ;
        connection.start();
    }


}
