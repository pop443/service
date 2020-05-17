package com.newland.boss.entity.performance.cachestore;

import com.newland.boss.utils.Threads;
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
import java.util.Collection;
import java.util.concurrent.*;

/**
 * Created by xz on 2020/2/9.
 */
public class CacheStore2Store extends CacheStoreAdapter<String,CacheStore2> {

    private static String colums = "s01,s02,s03,s04,s05,s06,s07,s08,s09,s10,s11,s12,s13,s14,s15,s16,s17,s18,s19,s20" ;
    private static String colums2 = "s01=?,s02=?,s03=?,s04=?,s05=?,s06=?,s07=?,s08=?,s09=?,s10=?,s11=?,s12=?,s13=?,s14=?,s15=?,s16=?,s17=?,s18=?,s19=?,s20=?" ;
    private static String tableName = "cachestore2" ;
    @LoggerResource
    private IgniteLogger log;
    @CacheStoreSessionResource
    private CacheStoreSession ses;
    @SpringResource(resourceName = "custDataSource")
    private transient CustDataSource custDataSource;

    private void init(){
        CacheConnHelper.getConnection(ses, custDataSource.getMap("mysql1"));
    }

    @Override
    public void loadCache(IgniteBiInClosure<String, CacheStore2> clo, Object... args) {
        int size = 1 ;
        log.info("--------------CacheStore1 loadCache");
        long l1 = System.currentTimeMillis() ;
        ExecutorService executorService = Executors.newFixedThreadPool(size) ;
        BlockingQueue<Future<Boolean>> queue = new LinkedBlockingDeque<>(size);
        //实例化CompletionService
        CompletionService<Boolean> completionService = new ExecutorCompletionService<>(executorService, queue);
        try {
            for (int i = 0; i < size; i++) {
                String sql = "select id,"+colums+" from "+tableName ;
                CacheStore2StoreWork cacheStoreStoreWork = new CacheStore2StoreWork(sql,custDataSource.getMap("mysql1"),clo) ;
                completionService.submit(cacheStoreStoreWork);
            }
            for (int i = 0; i < size; i++) {
                Future<Boolean> future = completionService.take();
                System.out.println(future.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        long l2 = System.currentTimeMillis() ;
        log.info("--------------CacheStore1 loadCache cost:"+(l2-l1));
        Threads.gracefulShutdown(executorService, 1, 1, TimeUnit.MINUTES);

    }
    @Override
    public CacheStore2 load(String key) throws CacheLoaderException {
        log.info("--------------CacheStore2 load");
        CacheStore2 CacheStore2 = null ;
        PreparedStatement pstm = null ;
        ResultSet rs = null ;
        try {
            init();
            Connection conn = ses.attachment();
            pstm = conn.prepareStatement("SELECT id,"+colums+" FROM "+tableName+" WHERE id=?");
            pstm.setString(1,key);
            rs = pstm.executeQuery() ;
            if (rs.next()){
                CacheStore2 = new CacheStore2(rs) ;
            }
        } catch (SQLException e) {
            throw new CacheLoaderException("Failed to load: " + key, e);
        }finally {
            ConnectionUtil.release(rs,pstm);
        }
        return CacheStore2 ;
    }

    @Override
    public void write(Cache.Entry<? extends String, ? extends CacheStore2> entry) throws CacheWriterException {
        System.out.println("--------------CacheStore2 write");
        PreparedStatement pstm = null ;
        ResultSet rs = null ;
        boolean insert = true ;
        try {
            init();
            Connection conn = ses.attachment();
            pstm = conn.prepareStatement("SELECT 1 FROM "+tableName+" WHERE id=?");
            pstm.setString(1,entry.getKey());
            rs = pstm.executeQuery() ;
            if (rs.next()){
                insert = false ;
            }
            String sql = null ;
            CacheStore2 cacheStore2 = entry.getValue() ;
            if (insert){
                System.out.println("--------------CourseCacheStore write insert");
                sql = "INSERT INTO "+tableName+" (id,"+colums+") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" ;
                pstm = conn.prepareStatement(sql) ;
                pstm.setString(1,cacheStore2.getId());
                pstm.setString(2,cacheStore2.getS01());
                pstm.setString(3,cacheStore2.getS02());
                pstm.setString(4,cacheStore2.getS03());
                pstm.setString(5,cacheStore2.getS04());
                pstm.setString(6,cacheStore2.getS05());
                pstm.setString(7,cacheStore2.getS06());
                pstm.setString(8,cacheStore2.getS07());
                pstm.setString(9,cacheStore2.getS08());
                pstm.setString(10,cacheStore2.getS09());
                pstm.setString(11,cacheStore2.getS10());
                pstm.setString(12,cacheStore2.getS11());
                pstm.setString(13,cacheStore2.getS12());
                pstm.setString(14,cacheStore2.getS13());
                pstm.setString(15,cacheStore2.getS14());
                pstm.setString(16,cacheStore2.getS15());
                pstm.setString(17,cacheStore2.getS16());
                pstm.setString(18,cacheStore2.getS17());
                pstm.setString(19,cacheStore2.getS18());
                pstm.setString(20,cacheStore2.getS19());
                pstm.setString(21,cacheStore2.getS20());
            }else{
                System.out.println("--------------CourseCacheStore write update");
                sql = "UPDATE "+tableName+" SET "+colums2+" WHERE id = ?" ;
                pstm = conn.prepareStatement(sql) ;
                pstm.setString(1,cacheStore2.getS01());
                pstm.setString(2,cacheStore2.getS02());
                pstm.setString(3,cacheStore2.getS03());
                pstm.setString(4,cacheStore2.getS04());
                pstm.setString(5,cacheStore2.getS05());
                pstm.setString(6,cacheStore2.getS06());
                pstm.setString(7,cacheStore2.getS07());
                pstm.setString(8,cacheStore2.getS08());
                pstm.setString(9,cacheStore2.getS09());
                pstm.setString(10,cacheStore2.getS10());
                pstm.setString(11,cacheStore2.getS11());
                pstm.setString(12,cacheStore2.getS12());
                pstm.setString(13,cacheStore2.getS13());
                pstm.setString(14,cacheStore2.getS14());
                pstm.setString(15,cacheStore2.getS15());
                pstm.setString(16,cacheStore2.getS16());
                pstm.setString(17,cacheStore2.getS17());
                pstm.setString(18,cacheStore2.getS18());
                pstm.setString(19,cacheStore2.getS19());
                pstm.setString(20,cacheStore2.getS20());
                pstm.setString(21,entry.getKey());
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
    public void writeAll(Collection<Cache.Entry<? extends String, ? extends CacheStore2>> entries) {
        System.out.println("--------------CacheStore2 writeAll");
        PreparedStatement pstm = null ;
        try {
            init();
            Connection conn = ses.attachment();
            String sql = "INSERT INTO "+tableName+" (id,"+colums+") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" ;
            pstm = conn.prepareStatement(sql) ;
            for (Cache.Entry<? extends String, ? extends CacheStore2> entry:entries) {
                CacheStore2 cacheStore2 = entry.getValue();
                pstm.setString(1,cacheStore2.getId());
                pstm.setString(2,cacheStore2.getS01());
                pstm.setString(3,cacheStore2.getS02());
                pstm.setString(4,cacheStore2.getS03());
                pstm.setString(5,cacheStore2.getS04());
                pstm.setString(6,cacheStore2.getS05());
                pstm.setString(7,cacheStore2.getS06());
                pstm.setString(8,cacheStore2.getS07());
                pstm.setString(9,cacheStore2.getS08());
                pstm.setString(10,cacheStore2.getS09());
                pstm.setString(11,cacheStore2.getS10());
                pstm.setString(12,cacheStore2.getS11());
                pstm.setString(13,cacheStore2.getS12());
                pstm.setString(14,cacheStore2.getS13());
                pstm.setString(15,cacheStore2.getS14());
                pstm.setString(16,cacheStore2.getS15());
                pstm.setString(17,cacheStore2.getS16());
                pstm.setString(18,cacheStore2.getS17());
                pstm.setString(19,cacheStore2.getS18());
                pstm.setString(20,cacheStore2.getS19());
                pstm.setString(21,cacheStore2.getS20());
                pstm.addBatch();
            }
            pstm.executeBatch();
        } catch (SQLException e) {
            throw new CacheWriterException("Failed to writeall size:" + entries.size(), e);
        }finally {
            ConnectionUtil.release(pstm);
        }
    }

    @Override
    public void delete(Object o) throws CacheWriterException {
        System.out.println("--------------CacheStore2 delete");
        String key = (String)o ;
        PreparedStatement pstm = null ;
        ResultSet rs = null ;
        try {
            init();
            Connection conn = ses.attachment();
            pstm = conn.prepareStatement("DELETE FROM CacheStore2 WHERE id =?");
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
        System.out.println("----CacheStore2 sessionEnd:"+commit);
        CacheConnHelper.releaseConnection(ses,commit);
    }
}
