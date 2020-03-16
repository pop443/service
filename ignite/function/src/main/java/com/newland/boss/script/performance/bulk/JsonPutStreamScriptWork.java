package com.newland.boss.script.performance.bulk;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.bulk.JavaObj;
import com.newland.boss.entity.performance.bulk.JsonObj;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xz on 2020/3/10.
 */
public class JsonPutStreamScriptWork extends PerformanceScriptWork<String, String> {
    public JsonPutStreamScriptWork(EnterParam enterParam, IgniteCache<String, String> igniteCache, IgniteDataStreamer<String, String> igniteDataStreamer) {
        super(enterParam, igniteCache, igniteDataStreamer);
    }

    @Override
    public void doing() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper() ;
        Map<String,String> map = new HashMap<>() ;
        CustObjBuild<JsonObj> build = new CustObjBuild<>(JsonObj.class) ;
        for (int i = 0; i < enterParam.getCount(); i++) {
            if (map.size() == enterParam.getCommitSize()) {
                System.out.println("提交：" + map.size() + "条");
                ids.addData(map);
                ids.flush();
                map.clear();
            }
            String randomKey = random.nextInt(enterParam.getCount())+enterParam.getCount()+"" ;
            JsonObj obj = build.build1k(randomKey+"") ;
            String jsonNode = objectMapper.writeValueAsString(obj);
            map.put(obj.getId(),jsonNode) ;
        }
        if (map.size()>0){
            System.out.println("提交：" + map.size() + "条");
            ids.addData(map);
            ids.flush();
            map.clear();
        }
    }
}