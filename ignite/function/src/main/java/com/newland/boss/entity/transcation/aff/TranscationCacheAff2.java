package com.newland.boss.entity.transcation.aff;

import com.newland.boss.entity.performance.CustObj;
import org.apache.ignite.cache.query.annotations.QuerySqlField;

/**
 * Created by xz on 2020/3/25.
 */
public class TranscationCacheAff2 extends CustObj{
    @QuerySqlField
    private String age ;
    public TranscationCacheAff2(String id, String bytes) {
        super(id, bytes);
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
