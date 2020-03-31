package com.newland.ignite.cachestore.adapter;

import com.newland.ignite.cachestore.entity.UserInfo;
import com.newland.ignite.cachestore.listen.CacheConnHelper;
import com.newland.ignite.datasource.CustDataSource;
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
import java.util.Map;

/**
 * Created by xz on 2020/2/5.
 */
public class UserInfoCacheStore extends CacheStoreAdapter<String, UserInfo> {

    @LoggerResource
    private IgniteLogger log;

    @CacheStoreSessionResource
    private CacheStoreSession ses;

    @SpringResource(resourceName = "custDataSource")
    private transient CustDataSource custDataSource;

    private void init(){
        CacheConnHelper.getConnection(ses, custDataSource.getMap("mysql"));
    }

    @Override
    public void loadCache(IgniteBiInClosure<String, UserInfo> clo, Object... args) {
        log.info("--------------UserInfoCacheStore loadCache");
        int count = 0 ;
        if (args == null || args.length == 0 || args[0] == null){
            count = 1 ;
        }else{
            count = (Integer)args[0];
        }

        PreparedStatement pstm = null ;
        ResultSet rs = null ;
        try {
            init();
            Connection conn = ses.attachment();
            pstm = conn.prepareStatement("select * from userinfo limit ?") ;
            pstm.setInt(1,count);
            rs = pstm.executeQuery();
            while (rs.next()){
                UserInfo userInfo = new UserInfo(rs) ;
                clo.apply(userInfo.getId(),userInfo);
            }
        } catch (SQLException e) {
            throw new CacheLoaderException("Failed to load values from cache store.", e);
        }finally {
            ConnectionUtil.release(rs,pstm);
        }
    }

    @Override
    public UserInfo load(String key) throws CacheLoaderException {
        log.info("--------------UserInfoCacheStore load");
        UserInfo userInfo = null ;
        PreparedStatement pstm = null ;
        ResultSet rs = null ;
        try {
            init();
            Connection conn = ses.attachment();
            pstm = conn.prepareStatement("SELECT id,NAME,PASSWORD,remark FROM userinfo WHERE id=?");
            pstm.setString(1,key);
            rs = pstm.executeQuery() ;
            if (rs.next()){
                userInfo = new UserInfo(rs) ;
            }
        } catch (SQLException e) {
            throw new CacheLoaderException("Failed to load: " + key, e);
        }finally {
            ConnectionUtil.release(rs,pstm);
        }
        return userInfo ;
    }

    @Override
    public void write(Cache.Entry<? extends String, ? extends UserInfo> entry) throws CacheWriterException {
        log.info("--------------UserInfoCacheStore write");
        PreparedStatement pstm = null ;
        ResultSet rs = null ;
        boolean insert = true ;
        try {
            init();
            Connection conn = ses.attachment();
            pstm = conn.prepareStatement("SELECT 1 FROM userinfo WHERE id=?");
            pstm.setString(1,entry.getKey());
            rs = pstm.executeQuery() ;
            if (rs.next()){
                insert = false ;
            }
            String sql = null ;
            UserInfo userInfo = entry.getValue() ;
            if (insert){
                sql = "INSERT INTO userinfo (id,NAME, PASSWORD, remark) VALUES (?,?,?,?)" ;
                pstm = conn.prepareStatement(sql) ;
                pstm.setString(1,userInfo.getId());
                pstm.setString(2,userInfo.getName());
                pstm.setString(3,userInfo.getPassword());
                pstm.setString(4,userInfo.getRemark());
            }else{
                sql = "UPDATE userinfo SET NAME=?,PASSWORD=?,remark=? WHERE id = ?" ;
                pstm = conn.prepareStatement(sql) ;
                pstm.setString(1,userInfo.getName());
                pstm.setString(2,userInfo.getPassword());
                pstm.setString(3,userInfo.getRemark());
                pstm.setString(4,entry.getKey());
            }
            int i = pstm.executeUpdate() ;
            if (i!=1){
                throw new CacheWriterException("----executeUpdate write num:" + i);
            }
        } catch (SQLException e) {
            throw new CacheWriterException("Failed to write [key=" + entry.getKey() + ", val=" + entry.getValue() + ']', e);
        }finally {
            ConnectionUtil.release(rs,pstm);
        }
    }

    @Override
    public Map<String, UserInfo> loadAll(Iterable<? extends String> keys) {
        log.info("--------------UserInfoCacheStore loadAll");
        return super.loadAll(keys);
    }

    @Override
    public void delete(Object o) throws CacheWriterException {
        log.info("--------------UserInfoCacheStore delete");
        String key = (String)o ;
        PreparedStatement pstm = null ;
        ResultSet rs = null ;
        try {
            init();
            Connection conn = ses.attachment();
            pstm = conn.prepareStatement("DELETE FROM userinfo WHERE id =?");
            pstm.setString(1,key);
            int i = pstm.executeUpdate() ;
            if (i!=1){
                throw new CacheWriterException("----executeUpdate delete num:" + i);
            }
        } catch (SQLException e) {
            throw new CacheWriterException("Failed to delete [key=" + key, e);
        }finally {
            ConnectionUtil.release(rs,pstm);
        }
    }

    @Override
    public void sessionEnd(boolean commit) {
        log.info("----UserInfoCacheStore sessionEnd:"+commit);
        CacheConnHelper.releaseConnection(ses,commit);
    }



}
