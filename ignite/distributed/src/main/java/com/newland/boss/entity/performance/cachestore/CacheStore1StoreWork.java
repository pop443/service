package com.newland.boss.entity.performance.cachestore;

import com.newland.ignite.cachestore.listen.CacheConnHelper;
import com.newland.ignite.utils.ConnectionUtil;
import org.apache.ignite.IgniteLogger;
import org.apache.ignite.cache.store.CacheStoreAdapter;
import org.apache.ignite.cache.store.CacheStoreSession;
import org.apache.ignite.lang.IgniteBiInClosure;
import org.apache.ignite.resources.CacheStoreSessionResource;
import org.apache.ignite.resources.LoggerResource;
import org.apache.ignite.resources.SpringResource;

import javax.cache.Cache;
import javax.cache.integration.CacheLoaderException;
import javax.cache.integration.CacheWriterException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by xz on 2020/2/9.
 */
public class CacheStore1StoreWork implements Runnable{
    private String sql ;
    private DataSource dataSource ;
    private IgniteBiInClosure<String, CacheStore1> clo ;

    public CacheStore1StoreWork(String sql, DataSource dataSource, IgniteBiInClosure<String, CacheStore1> clo) {
        this.sql = sql;
        this.dataSource = dataSource;
        this.clo = clo;
    }

    @Override
    public void run() {
        Connection conn = null ;
        PreparedStatement pstm = null ;
        ResultSet rs = null ;
        try {
            conn = dataSource.getConnection() ;
            System.out.println("cachestore1 执行SQL:"+sql);
            pstm = conn.prepareStatement(sql) ;
            rs = pstm.executeQuery() ;
            while (rs.next()){
                CacheStore1 cacheStore = new CacheStore1(rs) ;
                if (clo!=null){
                    clo.apply(cacheStore.getId(),cacheStore);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionUtil.release(rs,pstm,conn);
        }
    }

}
