package com.newland.boss.script.performance.randomw.partitionsmallEPput;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.lang.IgniteFuture;

import javax.cache.processor.EntryProcessorResult;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xz on 2020/3/10.
 */
public class PartitionSmallEpPutScriptAsynWork extends PerformanceScriptWork<String, PartitionCustObj> {

    private IgniteCache<String,BinaryObject> ic ;
    public PartitionSmallEpPutScriptAsynWork(EnterParam enterParam, IgniteCache<String, PartitionCustObj> igniteCache, IgniteDataStreamer<String, PartitionCustObj> igniteDataStreamer) {
        super(enterParam, igniteCache, igniteDataStreamer);
        ic = igniteCache.withKeepBinary() ;
    }

    @Override
    public Long call() throws Exception {
        Long l1 = System.currentTimeMillis() ;
        long jian = doing1();
        Long l2 = System.currentTimeMillis() ;
        return l2-l1-jian;
    }

    @Override
    public void doing() throws Exception {

    }

    public long doing1() {
        long jian = 0 ;
        Map<String, BinaryObject> map = new HashMap<>();
        CustObjBuild<PartitionCustObj> build = new CustObjBuild<>(PartitionCustObj.class);
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i + enterParam.getCount() + "";
            PartitionCustObj obj = build.build1k(randomKey + "");
            map.put(obj.getId(), IgniteUtil.toBinary(obj));
        }
        if (map.size() > 0) {
            System.out.println("提交：" + map.size() + "条");
            jian = epCommit(map);
            map.clear();
        }
        return jian ;
    }

    private long epCommit(Map<String, BinaryObject> map) {
        IgniteFuture<Map<String, EntryProcessorResult<Boolean>>> future = ic.invokeAllAsync(map.keySet(), new PutEp1(), map);
        Long l1 = System.currentTimeMillis() ;
        Map<String, EntryProcessorResult<Boolean>> resultMap = future.get();
        Long l2 = System.currentTimeMillis() ;
        resultMap.size();
        return (l2-l1);
    }
}
