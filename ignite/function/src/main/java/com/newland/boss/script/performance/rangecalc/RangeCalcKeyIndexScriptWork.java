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
public class RangeCalcKeyIndexScriptWork extends PerformanceScriptWork<String, AffinityItemNo> {
    private Random random ;
    public RangeCalcKeyIndexScriptWork(EnterParam enterParam, IgniteCache<String, AffinityItemNo> igniteCache, IgniteDataStreamer<String, AffinityItemNo> igniteDataStreamer) {
        super(enterParam, igniteCache, igniteDataStreamer);
        random = new Random();
    }

    @Override
    public void doing() {
        List<String> list = new ArrayList<>() ;
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = random.nextInt(2000)+"";
            list.add(randomKey);
        }
        if (list.size()>0){
            query(list);
            list.clear();
        }
    }

    private void query(List<String> list){
        StringBuilder sbSQL = new StringBuilder() ;
        sbSQL.append("select * from NEWLAND.AFFINITYITEMNO t where 1=1 ") ;
        for (int i = 0; i < list.size(); i++) {
            if (i==0){
                sbSQL.append(" and t._key = "+list.get(i)) ;
            }else{
                sbSQL.append(" or t._key = "+list.get(i)) ;
            }
        }
        System.out.println(sbSQL.toString());
        SqlFieldsQuery qry = new SqlFieldsQuery(sbSQL.toString()) ;
        FieldsQueryCursor<List<?>> fieldsQueryCursor = igniteCache.query(qry) ;
        int count = fieldsQueryCursor.getAll().size();
        System.out.println(Thread.currentThread().getName()+"读取"+enterParam.getCommitSize()+"条:实际获取"+count+"条");
    }

}
