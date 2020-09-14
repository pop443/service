package com.newland.boss.script.performance.keylike;

import com.newland.boss.entity.performance.obj.PartitionCustObj;
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
public class GetScriptWork extends PerformanceScriptWork<String, PartitionCustObj> {
    public GetScriptWork(EnterParam enterParam, IgniteCache<String, PartitionCustObj> igniteCache, IgniteDataStreamer<String, PartitionCustObj> igniteDataStreamer, Integer baseKey) {
        super(enterParam, igniteCache, igniteDataStreamer,baseKey+1000000);
    }

    @Override
    public long doing() {
        long l1 = System.currentTimeMillis() ;
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i+baseKey+"" ;
            query(randomKey);
        }
        long l2 = System.currentTimeMillis() ;
        return l2-l1 ;
    }

    private void query(String string){
        StringBuilder sbSQL = new StringBuilder() ;
        sbSQL.append("select _key,_val from NEWLAND.PARTITIONCUSTOBJ t where t._key like ('"+string+"%')") ;
        SqlFieldsQuery qry = new SqlFieldsQuery(sbSQL.toString()) ;
        FieldsQueryCursor<List<?>> fieldsQueryCursor = igniteCache.query(qry) ;
        int count = fieldsQueryCursor.getAll().size();
    }
}
