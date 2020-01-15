package com.xz.ignite.basefunction.computer;

import org.apache.ignite.IgniteLogger;
import org.apache.ignite.lang.IgniteClosure;
import org.apache.ignite.resources.LoggerResource;

/**
 * Created by Administrator on 2020/1/6.
 */
public class ApplyDemo1 implements IgniteClosure<String,Integer> {
    @LoggerResource
    private IgniteLogger log;

    @Override
    public Integer apply(String s) {
        String all = s.replaceAll("1","11") ;
        log.info("-----------version 111---------------"+all);
        return all.length()-s.length();
    }
}
