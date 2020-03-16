package com.newland.boss.script.performance.likequery;

import com.newland.boss.entity.performance.affinity.AffinityItemNo;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import com.newland.boss.utils.DiffString;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.cache.query.FieldsQueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by xz on 2020/3/10.
 */
public class LikeQueryScriptWork extends PerformanceScriptWork<String, AffinityItemNo> {
    public LikeQueryScriptWork(EnterParam enterParam, IgniteCache<String, AffinityItemNo> igniteCache, IgniteDataStreamer<String, AffinityItemNo> igniteDataStreamer) {
        super(enterParam, igniteCache, igniteDataStreamer);
    }

    @Override
    public void doing() {
        Set<String> set = new HashSet<>(enterParam.getCommitSize()) ;
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = DiffString.diffstr(1);
            set.add(randomKey);
            if (set.size()==enterParam.getCommitSize()){
                query(set);
                set.clear();
            }
        }
        if (set.size()>0){
            query(set);
            set.clear();
        }
    }

    private void query(Set<String> set){
        StringBuilder sbSQL = new StringBuilder() ;
        sbSQL.append("select * from AFFINITYITEMNO t where 1=1 ") ;
        for (String string:set) {
            sbSQL.append(" or t.s01 like ('%").append(string).append("%')");
        }
        System.out.println(sbSQL.toString());
        SqlFieldsQuery qry = new SqlFieldsQuery(sbSQL.toString()) ;
        FieldsQueryCursor<List<?>> fieldsQueryCursor = igniteCache.query(qry) ;
        int count = fieldsQueryCursor.getAll().size();
        System.out.println(Thread.currentThread().getName()+"读取"+enterParam.getCommitSize()+"条:实际获取"+count+"条");
    }

}
