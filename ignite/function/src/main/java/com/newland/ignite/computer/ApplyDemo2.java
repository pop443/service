package com.newland.ignite.computer;

import org.apache.ignite.IgniteLogger;
import org.apache.ignite.lang.IgniteClosure;
import org.apache.ignite.resources.LoggerResource;

/**
 * Created by Administrator on 2020/1/6.
 */
public class ApplyDemo2 implements IgniteClosure<String,Integer> {
    @LoggerResource
    private IgniteLogger log;

    @Override
    public Integer apply(String s) {
        String all = s.replaceAll("2","22") ;
        log.info("-----------version 1---------------"+all);
        return all.length()-s.length();
    }
}
