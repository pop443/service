package com.newland.ignite.spring;

import com.newland.ignite.spring.entity.cust.Spring1EntityCacheConfig;
import com.newland.ignite.spring.entity.cust.SpringEntity;
import com.newland.ignite.spring.support.CustIgniteRepositoryImpl;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.springdata.repository.config.EnableIgniteRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xz on 2020/2/27.
 */
@Configuration
@EnableIgniteRepositories(repositoryBaseClass = CustIgniteRepositoryImpl.class)
public class SpringAppCfg {

    @Bean
    public Ignite igniteInstance() {
        IgniteConfiguration cfg = IgniteUtil.getIgniteConfiguration() ;
        cfg.setIgniteInstanceName("springDataNode");
        CacheConfiguration<Integer,SpringEntity> cacheConfiguration = new Spring1EntityCacheConfig().getCacheConfiguration();
        cfg.setCacheConfiguration(cacheConfiguration) ;
        return Ignition.start(cfg);
    }

    /*@Bean
    public IgniteConfiguration igniteCfg() {
        IgniteConfiguration cfg = IgniteUtil.getIgniteConfiguration() ;
        cfg.setIgniteInstanceName("springDataNode");
        CacheConfiguration<Integer,SpringEntity> cacheConfiguration = new Spring1EntityCacheConfig().getCacheConfig();
        cfg.setCacheConfiguration(cacheConfiguration) ;
        return cfg;
    }*/
}
