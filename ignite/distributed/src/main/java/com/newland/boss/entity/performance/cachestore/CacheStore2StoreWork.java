package com.newland.boss.entity.performance.cachestore;

import com.newland.ignite.utils.ConnectionUtil;
import org.apache.ignite.lang.IgniteBiInClosure;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by xz on 2020/2/9.
 */
public class CacheStore2StoreWork implements Runnable{
    private String sql ;
    private DataSource dataSource ;
    private IgniteBiInClosure<String, CacheStore2> clo ;

    public CacheStore2StoreWork(String sql, DataSource dataSource, IgniteBiInClosure<String, CacheStore2> clo) {
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
                CacheStore2 cacheStore = new CacheStore2(rs) ;
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
