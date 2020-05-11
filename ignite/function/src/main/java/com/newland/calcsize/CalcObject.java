package com.newland.calcsize;

import com.newland.boss.utils.DiffString;
import org.apache.lucene.util.RamUsageEstimator;

/**
 * Created by xz on 2020/3/10.
 */
public class CalcObject {
    public static void main(String[] args) {
        String value = DiffString.diffstr(80);
        value = value+value+value;
        System.out.println(value);
        //计算指定对象及其引用树上的所有对象的综合大小，单位字节
        long l1 = RamUsageEstimator.sizeOf(value) ;
        //计算指定对象本身在堆空间的大小，单位字节
        long l2 = RamUsageEstimator.shallowSizeOf(value) ;
        //计算指定对象及其引用树上的所有对象的综合大小，返回可读的结果，如：2KB
        String s1 = RamUsageEstimator.humanSizeOf(value) ;
        System.out.println(l1*20);
        System.out.println(l2);
        System.out.println(s1);
    }
}
