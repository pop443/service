package com.newland.boss.script.performance.bulk.stringarray;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.bulk.StringObj;
import com.newland.boss.script.performance.EnterParam;
import org.apache.ignite.IgniteCache;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by xz on 2020/3/10.
 */
public class StringPutScriptWork implements Callable<Long> {
    private EnterParam enterParam;
    private IgniteCache<String,String[]> igniteCache ;
    private Integer baseKey;
    public StringPutScriptWork(EnterParam enterParam, IgniteCache<String,String[]> igniteCache, Integer baseKey) {
        this.enterParam = enterParam;
        this.igniteCache = igniteCache ;
        this.baseKey = baseKey ;
    }

    @Override
    public Long call() throws Exception {
        return working();
    }

    private Long working() {
        Map<String,String[]> map = new HashMap<>() ;
        CustObjBuild<StringObj> build1 = new CustObjBuild<>(StringObj.class) ;
        System.out.println("数据构造 start");
            for (int i = 0; i < enterParam.getCount(); i++) {
                String randomKey = i+baseKey+"" ;
                StringObj obj = build1.build1k(randomKey) ;
                map.put(obj.getId(),new String[]{obj.getId(),obj.getS01(),obj.getS02()}) ;
            }
        System.out.println("数据构造 end");
        long l1 = System.currentTimeMillis() ;
        Iterator<Map.Entry<String,String[]>> it = map.entrySet().iterator() ;
        while (it.hasNext()){
            Map.Entry<String,String[]> entry = it.next() ;
            igniteCache.put(entry.getKey(),entry.getValue());
        }
        long l2 = System.currentTimeMillis() ;
        return l2-l1;
    }
}
