package com.xz.ignite.basefunction.computer;

import com.xz.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCompute;

/**
 * Created by Administrator on 2020/1/6.
 */
public class Main {

    public static void main(String[] args) {
        Ignite ignite = IgniteUtil.getIgnite() ;
        IgniteCompute igniteCompute = ignite.compute() ;
        Integer integer = igniteCompute.apply(new ApplyDemo1(),"123123134");
        System.out.println("result----------"+integer);
        IgniteUtil.release(ignite);
    }

}
