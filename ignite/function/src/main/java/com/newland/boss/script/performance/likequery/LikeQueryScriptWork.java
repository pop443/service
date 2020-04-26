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
    public LikeQueryScriptWork(EnterParam enterParam, IgniteCache<String, AffinityItemNo> igniteCache, IgniteDataStreamer<String, AffinityItemNo> igniteDataStreamer) {
        super(enterParam, igniteCache, igniteDataStreamer);
        random = new Random() ;
    }

    @Override
    public void doing() {
        String string = null ;
        for (int i = 0; i < 1; i++) {
            string = random.nextInt(20)+"";
        }
        if (string!=null){
            query(string);
        }
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
