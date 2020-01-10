package com.xz.ignite.basefunction.entity.upload.affinity;

import org.apache.ignite.cache.affinity.AffinityKeyMapped;

/**
 * Created by Administrator on 2020/1/2.
 */
public class GrpMemberMon2Key {
    private String id ;
    @AffinityKeyMapped
    private Long customer_id ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Long customer_id) {
        this.customer_id = customer_id;
    }
}
