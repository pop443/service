package com.xz.ignite.affinity;

import org.apache.ignite.cache.affinity.AffinityKeyMapped;

/**
 * Created by Administrator on 2019/12/25.
 */
public class PersonKey {
    private String personId;
    @AffinityKeyMapped
    private String otherId;

    public PersonKey() {
    }

    public PersonKey(String personId, String otherId) {
        this.personId = personId;
        this.otherId = otherId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getOtherId() {
        return otherId;
    }

    public void setOtherId(String otherId) {
        this.otherId = otherId;
    }
}
