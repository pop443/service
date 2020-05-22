package com.newland.boss.entity.performance.cachestore;

import com.newland.boss.utils.Threads;
import com.newland.ignite.cachestore.listen.CacheConnHelper;
import com.newland.ignite.datasource.CustDataSource;
import com.newland.ignite.utils.ConnectionUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCluster;
import org.apache.ignite.IgniteLogger;
import org.apache.ignite.cache.store.CacheStoreAdapter;
import org.apache.ignite.cache.store.CacheStoreSession;
import org.apache.ignite.cluster.ClusterGroup;
import org.apache.ignite.lang.IgniteBiInClosure;
import org.apache.ignite.resources.CacheStoreSessionResource;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.apache.ignite.resources.LoggerResource;
import org.apache.ignite.resources.SpringResource;

import javax.cache.Cache;
import javax.cache.integration.CacheLoaderException;
import javax.cache.integration.CacheWriterException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.concurrent.*;

/**
 * Created by xz on 2020/2/9.
 */
public class CacheStore1Store extends CacheStoreAdapter<String,CacheStore1> {

    @IgniteInstanceResource
    private Ignite ignite ;

    private static String colums = "s01,s02,s03,s04,s05,s06,s07,s08,s09,s10,s11,s12,s13,s14,s15,s16,s17,s18,s19,s20" ;
    private static String colums2 = "s01=?,s02=?,s03=?,s04=?,s05=?,s06=?,s07=?,s08=?,s09=?,s10=?,s11=?,s12=?,s13=?,s14=?,s15=?,s16=?,s17=?,s18=?,s19=?,s20=?" ;
    private static String tableName = "cachestore1" ;
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
    public void loadCache(IgniteBiInClosure<String, CacheStore1> clo, Object... args) {
        log.info("--------------CacheStore1 loadCache");



        Connection conn = null ;
        PreparedStatement pstm = null ;
        ResultSet rs = null ;
        Long count = null ;
        try {
            init();
            conn = ses.attachment();
            pstm = conn.prepareStatement("select count(1) as count from "+tableName) ;
            rs = pstm.executeQuery();
            if (rs.next()){
                count = rs.getLong("count") ;
            }
        } catch (SQLException e) {
            throw new CacheLoaderException("Failed to load values from cache store.", e);
        }finally {
            ConnectionUtil.release(rs,pstm,conn);
        }
        long l1 = System.currentTimeMillis() ;
        int size = 4 ;
        long eachSize = count/size ;
        ExecutorService executorService = Executors.newFixedThreadPool(size) ;
        BlockingQueue<Future<Boolean>> queue = new LinkedBlockingDeque<>(size);
        //实例化CompletionService
        CompletionService<Boolean> completionService = new ExecutorCompletionService<>(executorService, queue);
        try {
            for (int i = 0; i < size; i++) {
                long minId_int = eachSize*i ;
                long maxId_int = eachSize*(i+1) ;
                String sql = "select id,"+colums+" from "+tableName+" t limit "+minId_int+","+maxId_int ;
                log.info(sql);
                CacheStore1StoreWork cacheStoreStoreWork = new CacheStore1StoreWork(sql,custDataSource.getMap("mysql1"),clo) ;
                completionService.submit(cacheStoreStoreWork);
            }
            for (int i = 0; i < size; i++) {
                Future<Boolean> future = completionService.take();
                log.info(future.get()+"");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        long l2 = System.currentTimeMillis() ;
        log.info("--------------CacheStore1 loadCache cost:"+(l2-l1));
        Threads.gracefulShutdown(executorService, 1, 1, TimeUnit.MINUTES);
    }

    @Override
    public CacheStore1 load(String key) throws CacheLoaderException {
        log.info("--------------CacheStore1 load");
        CacheStore1 cacheStore1 = null ;
        PreparedStatement pstm = null ;
        ResultSet rs = null ;
        try {
            init();
            Connection conn = ses.attachment();
            pstm = conn.prepareStatement("SELECT id,"+colums+" FROM "+tableName+" WHERE id=?");
            pstm.setString(1,key);
            rs = pstm.executeQuery() ;
            if (rs.next()){
                cacheStore1 = new CacheStore1(rs) ;
            }
        } catch (SQLException e) {
            throw new CacheLoaderException("Failed to load: " + key, e);
        }finally {
            ConnectionUtil.release(rs,pstm);
        }
        return cacheStore1 ;
    }


    @Override
    public void write(Cache.Entry<? extends String, ? extends CacheStore1> entry) throws CacheWriterException {
        System.out.println("--------------CacheStore1 write");
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
            CacheStore1 cacheStore1 = entry.getValue() ;
            if (insert){
                System.out.println("--------------CourseCacheStore write insert");
                sql = "INSERT INTO "+tableName+" (id,"+colums+") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" ;
                pstm = conn.prepareStatement(sql) ;
                pstm.setString(1,cacheStore1.getId());
                pstm.setString(2,cacheStore1.getS01());
                pstm.setString(3,cacheStore1.getS02());
                pstm.setString(4,cacheStore1.getS03());
                pstm.setString(5,cacheStore1.getS04());
                pstm.setString(6,cacheStore1.getS05());
                pstm.setString(7,cacheStore1.getS06());
                pstm.setString(8,cacheStore1.getS07());
                pstm.setString(9,cacheStore1.getS08());
                pstm.setString(10,cacheStore1.getS09());
                pstm.setString(11,cacheStore1.getS10());
                pstm.setString(12,cacheStore1.getS11());
                pstm.setString(13,cacheStore1.getS12());
                pstm.setString(14,cacheStore1.getS13());
                pstm.setString(15,cacheStore1.getS14());
                pstm.setString(16,cacheStore1.getS15());
                pstm.setString(17,cacheStore1.getS16());
                pstm.setString(18,cacheStore1.getS17());
                pstm.setString(19,cacheStore1.getS18());
                pstm.setString(20,cacheStore1.getS19());
                pstm.setString(21,cacheStore1.getS20());
            }else{
                System.out.println("--------------CourseCacheStore write update");
                sql = "UPDATE "+tableName+" SET "+colums2+" WHERE id = ?" ;
                pstm = conn.prepareStatement(sql) ;
                pstm.setString(1,cacheStore1.getS01());
                pstm.setString(2,cacheStore1.getS02());
                pstm.setString(3,cacheStore1.getS03());
                pstm.setString(4,cacheStore1.getS04());
                pstm.setString(5,cacheStore1.getS05());
                pstm.setString(6,cacheStore1.getS06());
                pstm.setString(7,cacheStore1.getS07());
                pstm.setString(8,cacheStore1.getS08());
                pstm.setString(9,cacheStore1.getS09());
                pstm.setString(10,cacheStore1.getS10());
                pstm.setString(11,cacheStore1.getS11());
                pstm.setString(12,cacheStore1.getS12());
                pstm.setString(13,cacheStore1.getS13());
                pstm.setString(14,cacheStore1.getS14());
                pstm.setString(15,cacheStore1.getS15());
                pstm.setString(16,cacheStore1.getS16());
                pstm.setString(17,cacheStore1.getS17());
                pstm.setString(18,cacheStore1.getS18());
                pstm.setString(19,cacheStore1.getS19());
                pstm.setString(20,cacheStore1.getS20());
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
    public void writeAll(Collection<Cache.Entry<? extends String, ? extends CacheStore1>> entries) {
        System.out.println("--------------CacheStore1 writeAll");
        PreparedStatement pstm = null ;
        try {
            init();
            Connection conn = ses.attachment();
            String sql = "INSERT INTO "+tableName+" (id,"+colums+") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" ;
            pstm = conn.prepareStatement(sql) ;
            for (Cache.Entry<? extends String, ? extends CacheStore1> entry:entries) {
                CacheStore1 cacheStore1 = entry.getValue();
                pstm.setString(1,cacheStore1.getId());
                pstm.setString(2,cacheStore1.getS01());
                pstm.setString(3,cacheStore1.getS02());
                pstm.setString(4,cacheStore1.getS03());
                pstm.setString(5,cacheStore1.getS04());
                pstm.setString(6,cacheStore1.getS05());
                pstm.setString(7,cacheStore1.getS06());
                pstm.setString(8,cacheStore1.getS07());
                pstm.setString(9,cacheStore1.getS08());
                pstm.setString(10,cacheStore1.getS09());
                pstm.setString(11,cacheStore1.getS10());
                pstm.setString(12,cacheStore1.getS11());
                pstm.setString(13,cacheStore1.getS12());
                pstm.setString(14,cacheStore1.getS13());
                pstm.setString(15,cacheStore1.getS14());
                pstm.setString(16,cacheStore1.getS15());
                pstm.setString(17,cacheStore1.getS16());
                pstm.setString(18,cacheStore1.getS17());
                pstm.setString(19,cacheStore1.getS18());
                pstm.setString(20,cacheStore1.getS19());
                pstm.setString(21,cacheStore1.getS20());
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
        System.out.println("--------------CacheStore1 delete");
        String key = (String)o ;
        PreparedStatement pstm = null ;
        ResultSet rs = null ;
        try {
            init();
            Connection conn = ses.attachment();
            pstm = conn.prepareStatement("DELETE FROM CacheStore1 WHERE id =?");
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
        System.out.println("----CacheStore1 sessionEnd:"+commit);
        CacheConnHelper.releaseConnection(ses,commit);
    }

    public static void main(String[] args) {
        String minId = "2003" ;
        String maxId = "3998" ;
        int size = 4 ;
        int eachSize = (Integer.parseInt(maxId)-Integer.parseInt(minId))/size ;
        System.out.println(eachSize);
        for (int i = 0; i < size ; i++) {
            int minId_int = Integer.parseInt(minId)+eachSize*i ;
            int maxId_int = Integer.parseInt(minId)+eachSize*(i+1) ;
            String sql = "select id,"+colums+" from "+tableName+" where t.id>'"+minId_int+"' and t.id<'"+maxId_int+"'" ;
            System.out.println(sql);
        }

    }
}
