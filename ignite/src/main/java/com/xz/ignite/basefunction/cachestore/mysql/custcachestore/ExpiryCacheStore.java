package com.xz.ignite.basefunction.cachestore.mysql.custcachestore;

import com.xz.ignite.basefunction.cachestore.entity.Expiry;
import com.xz.ignite.basefunction.cachestore.mysql.config.MysqlConnection;
import org.apache.ignite.IgniteLogger;
import org.apache.ignite.cache.store.CacheStoreAdapter;
import org.apache.ignite.cache.store.CacheStoreSession;
import org.apache.ignite.resources.CacheStoreSessionResource;
import org.apache.ignite.resources.LoggerResource;

import javax.cache.Cache;
import javax.cache.integration.CacheLoaderException;
import javax.cache.integration.CacheWriterException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

/**
 * Created by xz on 2020/2/9.
 */
public class ExpiryCacheStore extends CacheStoreAdapter<String,Expiry> {

    @LoggerResource
    private IgniteLogger log;

    @CacheStoreSessionResource
    private CacheStoreSession ses;

    @Override
    public Expiry load(String key) throws CacheLoaderException {
        log.info("--------------ExpiryCacheStore load");
        Expiry expiry = null ;
        PreparedStatement pstm = null ;
        ResultSet rs = null ;
        try {
            Connection conn = ses.attachment();
            pstm = conn.prepareStatement("SELECT id,NAME,remark FROM expiry WHERE id=?");
            pstm.setString(1,key);
            rs = pstm.executeQuery() ;
            if (rs.next()){
                expiry = new Expiry(rs) ;
            }
        } catch (SQLException e) {
            throw new CacheLoaderException("Failed to load: " + key, e);
        }finally {
            MysqlConnection.release(rs,pstm);
        }
        return expiry ;
    }

    @Override
    public void write(Cache.Entry<? extends String, ? extends Expiry> entry) throws CacheWriterException {
        PreparedStatement pstm = null ;
        ResultSet rs = null ;
        boolean insert = true ;
        try {
            Connection conn = ses.attachment();
            pstm = conn.prepareStatement("SELECT 1 FROM expiry WHERE id=?");
            pstm.setString(1,entry.getKey());
            rs = pstm.executeQuery() ;
            if (rs.next()){
                insert = false ;
            }
            String sql = null ;
            Expiry expiry = entry.getValue() ;
            if (insert){
                log.info("--------------ExpiryCacheStore write insert");
                sql = "INSERT INTO expiry (id,NAME,remark) VALUES (?,?,?)" ;
                pstm = conn.prepareStatement(sql) ;
                pstm.setString(1,expiry.getId());
                pstm.setString(2,expiry.getName());
                pstm.setString(3,expiry.getRemark());
            }else{
                log.info("--------------ExpiryCacheStore write update");
                sql = "UPDATE expiry SET NAME=?,remark=? WHERE id = ?" ;
                pstm = conn.prepareStatement(sql) ;
                pstm.setString(1,expiry.getName());
                pstm.setString(2,expiry.getRemark());
                pstm.setString(3,expiry.getId());
            }
            int i = pstm.executeUpdate() ;
            if (i!=1){
                throw new CacheWriterException("----executeUpdate write num:" + i);
            }
        } catch (SQLException e) {
            throw new CacheWriterException("Failed to write [key=" + entry.getKey() + ", val=" + entry.getValue() + ']', e);
        }finally {
            MysqlConnection.release(rs,pstm);
        }
    }

    @Override
    public void delete(Object o) throws CacheWriterException {
        log.info("--------------ExpiryCacheStore delete");
        String key = (String)o ;
        PreparedStatement pstm = null ;
        ResultSet rs = null ;
        try {
            Connection conn = ses.attachment();
            pstm = conn.prepareStatement("DELETE FROM expiry WHERE id =?");
            pstm.setString(1,key);
            int i = pstm.executeUpdate() ;
            if (i!=0){
                throw new CacheWriterException("----executeUpdate delete num:" + i);
            }
        } catch (SQLException e) {
            throw new CacheWriterException("Failed to delete [key=" + key, e);
        }finally {
            MysqlConnection.release(rs,pstm);
        }
    }
}
