package com.newland.boss.script.performance.likequery;

import com.newland.boss.entity.performance.affinity.AffinityItemNo;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import com.newland.boss.utils.DiffString;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.cache.query.FieldsQueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;

import java.util.*;

/**
 * Created by xz on 2020/3/10.
 */
public class LikeQueryScriptWork extends PerformanceScriptWork<String, AffinityItemNo> {
    private Random random ;
    public LikeQueryScriptWork(EnterParam enterParam, IgniteCache<String, AffinityItemNo> igniteCache, IgniteDataStreamer<String, AffinityItemNo> igniteDataStreamer,Integer baseKey) {
        super(enterParam, igniteCache, igniteDataStreamer,baseKey);
        random = new Random() ;
    }

    @Override
    public long doing() {
        long cost = 0 ;
        String string = null ;
        for (int i = 0; i < 1; i++) {
            string = baseKey+i+"";
        }
        if (string!=null){
            long l1 = System.currentTimeMillis() ;
            query(string);
            long l2 = System.currentTimeMillis() ;
            cost = cost+(l2-l1);
        }
        return cost ;
    }

    private void query(String string){
        StringBuilder sbSQL = new StringBuilder() ;
        sbSQL.append("select * from NEWLAND.AFFINITYITEMNO t where t._key like ('"+string+"%')") ;
        System.out.println(sbSQL.toString());
        SqlFieldsQuery qry = new SqlFieldsQuery(sbSQL.toString()) ;
        FieldsQueryCursor<List<?>> fieldsQueryCursor = igniteCache.query(qry) ;
        int count = fieldsQueryCursor.getAll().size();
        System.out.println(Thread.currentThread().getName()+"读取"+enterParam.getCommitSize()+"条:实际获取"+count+"条");
    }

}
