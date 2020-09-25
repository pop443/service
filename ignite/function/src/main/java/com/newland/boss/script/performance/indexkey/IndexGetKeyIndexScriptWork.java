package com.newland.boss.script.performance.indexkey;

import com.newland.boss.entity.performance.affinity.AffinityItemNo;
import com.newland.boss.entity.performance.affinity.AffinityItemYes;
import com.newland.boss.entity.performance.affinity.AffinityItemYesKey;
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
public class IndexGetKeyIndexScriptWork extends PerformanceScriptWork<AffinityItemYesKey,AffinityItemYes> {
    public IndexGetKeyIndexScriptWork(EnterParam enterParam, IgniteCache<AffinityItemYesKey,AffinityItemYes> igniteCache, IgniteDataStreamer<AffinityItemYesKey,AffinityItemYes> igniteDataStreamer, Integer baseKey) {
        super(enterParam, igniteCache, igniteDataStreamer,baseKey);
    }

    @Override
    public long doing() {
        long l1 = System.currentTimeMillis() ;
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i+baseKey+"" ;
            SqlFieldsQuery sqlFieldsQuery = new SqlFieldsQuery(" select _key,_val from NEWLAND.AffinityItemYes WHERE itemId="+randomKey) ;
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
