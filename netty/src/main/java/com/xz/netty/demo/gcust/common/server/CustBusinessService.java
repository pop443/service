package com.xz.netty.demo.gcust.common.server;

import com.xz.netty.demo.gcust.entity.transmission.BaseBusiness;

/**
 * Created by xz on 2020/2/3.
 */
public interface CustBusinessService <T extends BaseBusiness>{
    void deal(T t);
}
