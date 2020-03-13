package com.newland.boss.script.performance.randomwr.nearsmallpg;

import com.newland.boss.entity.performance.obj.NearSmallCustObj;
import com.newland.boss.entity.performance.obj.NearSmallCustObjConfiguration;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScript;
import com.newland.boss.script.performance.PerformanceScriptWork;
import com.newland.boss.script.performance.randomr.nearsmallget.NearSmallGetScriptWork;
import com.newland.boss.script.performance.randomw.nearsmallput.NearSmallPutScriptWork;

/**
 * Created by xz on 2020/3/14.
 */
public class NearSmallPGScript_3 extends PerformanceScript<String,NearSmallCustObj> {
    NearSmallPGScript_3(EnterParam enterParam, Class<? extends PerformanceScriptWork<String, NearSmallCustObj>>... cz) {
        super(new NearSmallCustObjConfiguration(2), enterParam, cz);
    }

    @Override
    protected void afterInitIgnite() {
        ignite.destroyCache(cacheName);
    }

    public static void main(String[] args) throws Exception{
        EnterParam enterParam = EnterParam.getEnterParam(args);
        System.out.println("Near get&put 1主2备："+enterParam.toString());
        NearSmallPGScript_3 scirpt = new NearSmallPGScript_3(enterParam,NearSmallPutScriptWork.class, NearSmallGetScriptWork.class) ;
        scirpt.start();
    }}
