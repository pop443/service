package com.newland.boss.entity.transcation.aff;

import com.newland.boss.entity.performance.CustObj;
import org.apache.ignite.cache.affinity.AffinityKeyMapped;
import org.apache.ignite.cache.query.annotations.QuerySqlField;

/**
 * Created by xz on 2020/3/25.
 */
public class TranscationCacheAff1Key{
    @QuerySqlField
    private String affId;
    @AffinityKeyMapped
    @QuerySqlField
    private String otherId;

    public TranscationCacheAff1Key() {
    }

    public TranscationCacheAff1Key(String affId, String otherId) {
        this.affId = affId;
        this.otherId = otherId;
    }

    public String getAffId() {
        return affId;
    }

    public void setAffId(String affId) {
        this.affId = affId;
    }

    public String getOtherId() {
        return otherId;
    }

    public void setOtherId(String otherId) {
        this.otherId = otherId;
    }
}
