package com.newland.ignite.affinitydata;

import org.apache.ignite.cache.affinity.AffinityKeyMapped;
import org.apache.ignite.cache.query.annotations.QuerySqlField;

/**
 * Created by Administrator on 2019/12/25.
 */
public class PersonKey {
    @QuerySqlField
    private String personKeyId;
    @AffinityKeyMapped
    @QuerySqlField
    private String otherId;

    public PersonKey() {
    }

    public PersonKey(String personKeyId, String otherId) {
        this.personKeyId = personKeyId;
        this.otherId = otherId;
    }

    public String getPersonKeyId() {
        return personKeyId;
    }

    public void setPersonKeyId(String personKeyId) {
        this.personKeyId = personKeyId;
    }

    public String getOtherId() {
        return otherId;
    }

    public void setOtherId(String otherId) {
        this.otherId = otherId;
    }

    @Override
    public String toString() {
        return "PersonKey{" +
                "personKeyId='" + personKeyId + '\'' +
                ", otherId='" + otherId + '\'' +
                '}';
    }
}
