package com.newland.ignite.cachestore.adapter;

import com.newland.ignite.cachestore.entity.Course;
import com.newland.ignite.cachestore.listen.CacheConnHelper;
import com.newland.ignite.utils.ConnectionUtil;
import org.apache.ignite.IgniteLogger;
import org.apache.ignite.cache.store.CacheStoreAdapter;
import org.apache.ignite.cache.store.CacheStoreSession;
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
import java.util.Collection;

/**
 * Created by xz on 2020/2/9.
 */
public class CourseCacheStore extends CacheStoreAdapter<String,Course> {

    @LoggerResource
    private IgniteLogger log;
    @CacheStoreSessionResource
    private CacheStoreSession ses;
    @SpringResource(resourceName = "druidDataSource")
    private transient DataSource dataSource;

    private void init(){
        CacheConnHelper.getConnection(ses,dataSource);
    }

    @Override
    public Course load(String key) throws CacheLoaderException {
        log.info("--------------CourseCacheStore load");
        Course course = null ;
        PreparedStatement pstm = null ;
        ResultSet rs = null ;
        try {
            init();
            Connection conn = ses.attachment();
            pstm = conn.prepareStatement("SELECT id,NAME,uid FROM course WHERE id=?");
            pstm.setString(1,key);
            rs = pstm.executeQuery() ;
            if (rs.next()){
                course = new Course(rs) ;
            }
        } catch (SQLException e) {
            throw new CacheLoaderException("Failed to load: " + key, e);
        }finally {
            ConnectionUtil.release(rs,pstm);
        }
        return course ;
    }

    @Override
    public void write(Cache.Entry<? extends String, ? extends Course> entry) throws CacheWriterException {
        System.out.println("--------------CourseCacheStore write");
        PreparedStatement pstm = null ;
        ResultSet rs = null ;
        boolean insert = true ;
        try {
            init();
            Connection conn = ses.attachment();
            pstm = conn.prepareStatement("SELECT 1 FROM course WHERE id=?");
            pstm.setString(1,entry.getKey());
            rs = pstm.executeQuery() ;
            if (rs.next()){
                insert = false ;
            }
            String sql = null ;
            Course course = entry.getValue() ;
            if (insert){
                System.out.println("--------------CourseCacheStore write insert");
                sql = "INSERT INTO course (id,NAME, uid) VALUES (?,?,?)" ;
                pstm = conn.prepareStatement(sql) ;
                pstm.setString(1,course.getId());
                pstm.setString(2,course.getName());
                pstm.setString(3,course.getUid());
            }else{
                System.out.println("--------------CourseCacheStore write update");
                sql = "UPDATE course SET NAME=?,uid=? WHERE id = ?" ;
                pstm = conn.prepareStatement(sql) ;
                pstm.setString(1,course.getName());
                pstm.setString(2,course.getUid());
                pstm.setString(3,entry.getKey());
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
    public void writeAll(Collection<Cache.Entry<? extends String, ? extends Course>> entries) {
        super.writeAll(entries);
    }

    @Override
    public void delete(Object o) throws CacheWriterException {
        System.out.println("--------------CourseCacheStore delete");
        String key = (String)o ;
        PreparedStatement pstm = null ;
        ResultSet rs = null ;
        try {
            init();
            Connection conn = ses.attachment();
            pstm = conn.prepareStatement("DELETE FROM course WHERE id =?");
            pstm.setString(1,key);
            int i = pstm.executeUpdate() ;
            if (i!=0){
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
        System.out.println("----Coursecachestore sessionEnd:"+commit);
        CacheConnHelper.releaseConnection(ses,commit);
    }
}
