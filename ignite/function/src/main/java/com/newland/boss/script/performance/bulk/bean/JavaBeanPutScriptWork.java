package com.newland.boss.script.performance.bulk.bean;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.bulk.JavaObj;
import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.script.performance.EnterParam;
import org.apache.ignite.IgniteCache;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by xz on 2020/3/10.
 */
public class JavaBeanPutScriptWork implements Callable<Long> {
    private EnterParam enterParam;
    private IgniteCache<String,JavaObj> igniteCache ;
    private Integer baseKey;
    public JavaBeanPutScriptWork(EnterParam enterParam, IgniteCache<String,JavaObj> igniteCache, Integer baseKey) {
        this.enterParam = enterParam;
        this.igniteCache = igniteCache ;
        this.baseKey = baseKey ;
    }

    @Override
    public Long call() throws Exception {
        return working();
    }

    private Long working() {
        List<JavaObj> list = new ArrayList<>() ;
        CustObjBuild<JavaObj> build1 = new CustObjBuild<>(JavaObj.class) ;
        System.out.println("数据构造 start");
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i+baseKey+"" ;
            JavaObj obj = build1.build1k(randomKey) ;
            list.add(obj) ;
        }
        System.out.println("数据构造 end");
        long l1 = System.currentTimeMillis() ;
        for (JavaObj javaObj:list) {
            igniteCache.put(javaObj.getId(),javaObj);
        }
        long l2 = System.currentTimeMillis() ;
        return l2-l1;
    }
}
