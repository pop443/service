package com.newland.boss.script.features.show;

import com.newland.boss.entity.performance.affinity.AffinityItemNo;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.configuration.CacheConfiguration;

import javax.cache.configuration.Configuration;

/**
 * Created by xz on 2020/4/26.
 */
public class ShowCacheConfig {
    private Ignite ignite ;
    public ShowCacheConfig() {
        ignite = IgniteUtil.getIgnite() ;
    }

    public void start(){
        IgniteCache ic = ignite.cache("AFFINITYITEMNO") ;
        Configuration<String,AffinityItemNo> cacheConfiguration = ic.getConfiguration(CacheConfiguration.class) ;
        System.out.println(1);
    }
    public void close(){
        if (ignite!=null){
            ignite.close();
        }
    }
    public static void main(String[] args) {
        ShowCacheConfig showCacheConfig = new ShowCacheConfig() ;
        try {
            showCacheConfig.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        showCacheConfig.close();
    }
}
