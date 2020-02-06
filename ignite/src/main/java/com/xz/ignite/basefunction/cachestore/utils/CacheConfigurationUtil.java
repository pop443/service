package com.xz.ignite.basefunction.cachestore.utils;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.CacheRebalanceMode;
import org.apache.ignite.cache.store.jdbc.CacheJdbcPojoStoreFactory;
import org.apache.ignite.cache.store.jdbc.dialect.MySQLDialect;
import org.apache.ignite.configuration.CacheConfiguration;

/**
 * Created by xz on 2020/2/5.
 */
public class CacheConfigurationUtil {

    /*public static <K,V>  CacheConfiguration getCacheStoreConfig(Class<K> keyClass, Class<V> valueClass){
        CacheConfiguration<K,V> cacheConfiguration = new CacheConfiguration<>() ;
        cacheConfiguration.setName(valueClass.getSimpleName().toUpperCase()) ;
        cacheConfiguration.setCacheMode(CacheMode.PARTITIONED) ;
        cacheConfiguration.setAtomicityMode(CacheAtomicityMode.ATOMIC);
        //备份数量
        cacheConfiguration.setBackups(1) ;
        cacheConfiguration.setSqlSchema("cachestore") ;
        cacheConfiguration.setIndexedTypes( keyClass ,valueClass ) ;
        //默认为异步的再平衡
        cacheConfiguration.setRebalanceMode(CacheRebalanceMode.ASYNC) ;
        //cachestore
        //mysql
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setURL("jdbc:mysql://localhost:3306/qsjtec?useUnicode=true&characterEncoding=utf-8");
        mysqlDataSource.setUser("root");
        mysqlDataSource.setPassword("1qaz!QAZ");

        CacheJdbcPojoStoreFactory<K,V> cacheJdbcPojoStoreFactory = new CacheJdbcPojoStoreFactory<>();
        cacheJdbcPojoStoreFactory.setDataSource(mysqlDataSource);
        cacheJdbcPojoStoreFactory.setDialect(new MySQLDialect());


        cacheConfiguration

        return cacheConfiguration ;
    }*/
}
