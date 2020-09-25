package com.newland.boss.entity.performance.affinitykey;

import org.apache.ignite.cache.affinity.AffinityKeyMapped;
import org.apache.ignite.cache.query.annotations.QuerySqlField;

/**
 * Created by xz on 2020/9/23.
 */
public class Cache2Key {
    @QuerySqlField
    private String kid ;

    @QuerySqlField(index = true)
    private String affinityKey ;

    public Cache2Key(String kid, String affinityKey) {
        this.kid = kid;
        this.affinityKey = affinityKey;
    }

    public String getKid() {
        return kid;
    }

    public void setKid(String kid) {
        this.kid = kid;
    }

    public String getAffinityKey() {
        return affinityKey;
    }

    public void setAffinityKey(String affinityKey) {
        this.affinityKey = affinityKey;
    }
}
