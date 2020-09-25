package com.newland.boss.script.performance.affinityindexkey.cache1;

import com.newland.boss.entity.performance.affinity.AffinityItemYes;
import com.newland.boss.entity.performance.affinity.AffinityItemYesKey;
import com.newland.boss.entity.performance.affinitykey.Cache1Key;
import com.newland.boss.entity.performance.affinitykey.Cache1Value;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.cache.query.FieldsQueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;

import java.util.List;

/**
 * Created by xz on 2020/3/10.
 */
public class IndexGetKeyIndexScriptWork extends PerformanceScriptWork<Cache1Key,Cache1Value> {
    public IndexGetKeyIndexScriptWork(EnterParam enterParam, IgniteCache<Cache1Key,Cache1Value> igniteCache, IgniteDataStreamer<Cache1Key,Cache1Value> igniteDataStreamer, Integer baseKey) {
        super(enterParam, igniteCache, igniteDataStreamer,baseKey);
    }

    @Override
    public long doing() {
        long l1 = System.currentTimeMillis() ;
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i+"" ;
            SqlFieldsQuery sqlFieldsQuery = new SqlFieldsQuery(" select _key,_val from NEWLAND.Cache1Value WHERE affinityKey='"+randomKey+"'") ;
            FieldsQueryCursor<List<?>> fieldsQueryCursor = igniteCache.query(sqlFieldsQuery) ;
            List<List<?>> lists = fieldsQueryCursor.getAll();
            /*String key = (String)lists.get(0).get(0);
            AffinityItemNo value = (AffinityItemNo)lists.get(0).get(1);
            System.out.println(value);*/

        }
        long l2 = System.currentTimeMillis() ;
        return l2-l1 ;
    }
}
