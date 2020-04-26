package com.newland.boss.script.performance.affinity;

import com.newland.boss.entity.performance.affinity.AffinityMain;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.cache.query.FieldsQueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by xz on 2020/3/10.
 */
public class AffinityGetNoScriptWork extends PerformanceScriptWork<String, AffinityMain> {
    public AffinityGetNoScriptWork(EnterParam enterParam, IgniteCache<String, AffinityMain> igniteCache, IgniteDataStreamer<String, AffinityMain> igniteDataStreamer) {
        super(enterParam, igniteCache, igniteDataStreamer);
    }

    @Override
    public void doing() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = random.nextInt(2000) + "";
            list.add(randomKey);
        }
        if (list.size() > 0) {
            query(list);
            list.clear();
        }
    }

    private void query(List<String> list) {
        StringBuilder sbSQL = new StringBuilder();
        sbSQL.append("select * from NEWLAND.AFFINITYMAIN t1,NEWLAND.AFFINITYITEMNO t2 where t1._key = t2.id  and t1._key in(");
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                sbSQL.append(" '" + list.get(i) + "'");
            } else {
                sbSQL.append(" , '" + list.get(i) + "'");
            }
        }
        sbSQL.append(")");
        System.out.println(sbSQL.toString());
        SqlFieldsQuery qry = new SqlFieldsQuery(sbSQL.toString());
        qry.setCollocated(false).setDistributedJoins(true);
        FieldsQueryCursor<List<?>> fieldsQueryCursor = igniteCache.query(qry);
        int count = fieldsQueryCursor.getAll().size();
        System.out.println(Thread.currentThread().getName() + "读取" + enterParam.getCommitSize() + "条:实际获取" + count + "条");
    }

}
