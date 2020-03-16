package com.newland.boss.script.performance.bulk;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.bulk.JavaObj;
import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xz on 2020/3/10.
 */
public class JavaPutStreamScriptWork extends PerformanceScriptWork<String, JavaObj> {
    public JavaPutStreamScriptWork(EnterParam enterParam, IgniteCache<String, JavaObj> igniteCache, IgniteDataStreamer<String, JavaObj> igniteDataStreamer) {
        super(enterParam, igniteCache, igniteDataStreamer);
    }

    @Override
    public void doing() {
        Map<String,JavaObj> map = new HashMap<>() ;
        CustObjBuild<JavaObj> build = new CustObjBuild<>(JavaObj.class) ;
        for (int i = 0; i < enterParam.getCount(); i++) {
            if (map.size() == enterParam.getCommitSize()) {
                System.out.println("提交：" + map.size() + "条");
                ids.addData(map);
                ids.flush();
                map.clear();
            }
            String randomKey = random.nextInt(enterParam.getCount())+enterParam.getCount()+"" ;
            JavaObj obj = build.build1k(randomKey+"") ;
            map.put(obj.getId(),obj) ;
        }
        if (map.size()>0){
            System.out.println("提交：" + map.size() + "条");
            ids.addData(map);
            ids.flush();
            map.clear();
        }
    }
}