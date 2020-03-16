package com.newland.boss.script.performance.rangecalc;

import com.newland.boss.entity.performance.affinity.AffinityItemNo;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
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
public class RangeCalcNoIndexScriptWork extends PerformanceScriptWork<String, AffinityItemNo> {
    public RangeCalcNoIndexScriptWork(EnterParam enterParam, IgniteCache<String, AffinityItemNo> igniteCache, IgniteDataStreamer<String, AffinityItemNo> igniteDataStreamer) {
        super(enterParam, igniteCache, igniteDataStreamer);
    }

    @Override
    public void doing() {
        Set<String> set = new HashSet<>(enterParam.getCommitSize()) ;
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = random.nextInt(enterParam.getCount())+enterParam.getCount()+"" ;
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
        SqlFieldsQuery qry = new SqlFieldsQuery("select * from AFFINITYITEMNO t where t.range1 between 1 and "+enterParam.getCount()) ;
        FieldsQueryCursor<List<?>> fieldsQueryCursor = igniteCache.query(qry) ;
        int count = fieldsQueryCursor.getAll().size();
        System.out.println(Thread.currentThread().getName()+"读取"+enterParam.getCommitSize()+"条:实际获取"+count+"条");
    }

}
