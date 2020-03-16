package com.newland.boss.script.performance.affinity;

import com.newland.boss.entity.performance.affinity.AffinityMain;
import com.newland.boss.entity.performance.obj.NearSmallCustObj;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.cache.query.FieldsQueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by xz on 2020/3/10.
 */
public class AffinityGetScriptWork extends PerformanceScriptWork<String, AffinityMain> {
    public AffinityGetScriptWork(EnterParam enterParam, IgniteCache<String, AffinityMain> igniteCache, IgniteDataStreamer<String, AffinityMain> igniteDataStreamer) {
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
        sbSQL.append("select * from AFFINITYMAIN t1,AFFINITYITEMYES t2 where t1.id = t2.id and t2.id in (") ;
        for (String string:set) {
            sbSQL.append("'").append(string).append("',");
        }
        sbSQL.append("'1')");
        SqlFieldsQuery qry = new SqlFieldsQuery("select * from AFFINITYMAIN t1,AFFINITYITEMYES t2 where t1.ID = t2.ID") ;
        qry.setCollocated(true);
        FieldsQueryCursor<List<?>> fieldsQueryCursor = igniteCache.query(qry) ;
        int count = fieldsQueryCursor.getAll().size();
        System.out.println(Thread.currentThread().getName()+"读取"+enterParam.getCommitSize()+"条:实际获取"+count+"条");
    }

}
