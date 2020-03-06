package com.newland.ignite.query.api;

import com.newland.ignite.upload.entity.GrpCrmCustMon;
import org.apache.ignite.Ignite;

/**
 * Created by Administrator on 2020/1/3.
 */
public class GrpCrmCustMonQuery extends BaseQuery<String,GrpCrmCustMon> {
    public GrpCrmCustMonQuery(Ignite ignite) {
        super(ignite, GrpCrmCustMon.class.getSimpleName().toUpperCase());
    }
}
