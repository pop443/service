package com.newland.boss.script.performance.rangecalc;

import com.newland.boss.entity.performance.affinity.AffinityItemNo;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.cache.query.FieldsQueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by xz on 2020/3/10.
 */
public class RangeCalcNoIndexScriptWork extends PerformanceScriptWork<String, AffinityItemNo> {
    private Random random ;
    public RangeCalcNoIndexScriptWork(EnterParam enterParam, IgniteCache<String, AffinityItemNo> igniteCache, IgniteDataStreamer<String, AffinityItemNo> igniteDataStreamer,Integer baseKey) {
        super(enterParam, igniteCache, igniteDataStreamer,baseKey);
        random = new Random() ;
    }

    @Override
    public long doing() {
        long cost = 0 ;
        List<String> list = new ArrayList<>() ;
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = random.nextInt(baseKey)+"";
            list.add(randomKey);
        }
        if (list.size()>0){
            long l1 = System.currentTimeMillis() ;
            query(list);
            long l2 = System.currentTimeMillis() ;
            cost = cost+(l2-l1);
            list.clear();
        }
        return cost ;
    }

    private void query(List<String> list){
        StringBuilder sbSQL = new StringBuilder() ;
        sbSQL.append("select * from NEWLAND.AFFINITYITEMNO t where 1=1 and t.range1 in (") ;
        for (int i = 0; i < list.size(); i++) {
            if (i==0){
                sbSQL.append(list.get(i)) ;
            }else{
                sbSQL.append(","+list.get(i)) ;
            }
        }
        sbSQL.append(")");
        System.out.println(sbSQL.toString());
        SqlFieldsQuery qry = new SqlFieldsQuery(sbSQL.toString()) ;
        FieldsQueryCursor<List<?>> fieldsQueryCursor = igniteCache.query(qry) ;
        int count = fieldsQueryCursor.getAll().size();
        System.out.println(Thread.currentThread().getName()+"读取"+enterParam.getCommitSize()+"条:实际获取"+count+"条");
    }

}
