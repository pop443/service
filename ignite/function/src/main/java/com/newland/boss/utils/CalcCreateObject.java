package com.newland.boss.utils;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.obj.NearSmallCustObj;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by xz on 2020/3/27.
 */
public class CalcCreateObject {
    public static void main(String[] args) {
        Random random = new Random() ;
        Long l1 = System.currentTimeMillis();
        Map<String,NearSmallCustObj> map = new HashMap<>() ;
        CustObjBuild<NearSmallCustObj> build = new CustObjBuild<>(NearSmallCustObj.class) ;
        for (int i = 0; i < 10000; i++) {
            String randomKey = random.nextInt(10000)+10000+"" ;
            NearSmallCustObj obj = build.build4k(randomKey+"") ;
            map.put(obj.getId(),obj) ;
        }
        Long l2 = System.currentTimeMillis();
        System.out.println(l2-l1);
    }
}
