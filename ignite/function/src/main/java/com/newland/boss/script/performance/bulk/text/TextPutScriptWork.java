package com.newland.boss.script.performance.bulk.text;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.bulk.JavaObj;
import com.newland.boss.entity.performance.bulk.TextObj;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.utils.DiffString;
import org.apache.ignite.IgniteCache;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;

/**
 * Created by xz on 2020/3/10.
 */
public class TextPutScriptWork implements Callable<Long> {
    private EnterParam enterParam;
    private IgniteCache<String, TextObj> igniteCache;
    private Integer baseKey;

    public TextPutScriptWork(EnterParam enterParam, IgniteCache<String, TextObj> igniteCache, Integer baseKey) {
        this.enterParam = enterParam;
        this.igniteCache = igniteCache;
        this.baseKey = baseKey;
    }

    @Override
    public Long call() throws Exception {
        return working();
    }

    private Long working() {
        List<TextObj> list = new ArrayList<>();
        System.out.println("数据构造 start");
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i + baseKey + "";
            String value = DiffString.diffstr(4);
            TextObj obj = new TextObj(randomKey, value);
            list.add(obj);
        }
        System.out.println("数据构造 end");
        long l1 = System.currentTimeMillis();
        for (TextObj textObj:list) {
            igniteCache.put(textObj.getId(), textObj);
        }
        long l2 = System.currentTimeMillis();
        return l2 - l1;
    }
}
