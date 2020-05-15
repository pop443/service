package com.newland.boss.script.performance.affinity;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.affinity.*;
import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.entity.performance.obj.PartitionCustObj2;
import com.newland.boss.script.performance.EnterParam;
import com.newland.ignite.label.utils.IdGen;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;

/**
 * Created by xz on 2020/3/10.
 */
public class AffinityStreamPutSmallScriptWork implements Callable<Long> {
    private EnterParam enterParam;
    private IgniteDataStreamer<String,AffinityMain> igniteCacheS ;
    private IgniteDataStreamer<AffinityItemYesKey,AffinityItemYes> igniteCacheyesS ;
    private IgniteDataStreamer<String,AffinityItemNo> igniteCachenoS ;
    private Random random;

    public AffinityStreamPutSmallScriptWork(EnterParam enterParam, IgniteDataStreamer<String, AffinityMain> igniteCacheS, IgniteDataStreamer<AffinityItemYesKey, AffinityItemYes> igniteCacheyesS, IgniteDataStreamer<String, AffinityItemNo> igniteCachenoS) {
        this.random = new Random();
        this.enterParam = enterParam;
        this.igniteCacheS = igniteCacheS;
        this.igniteCacheyesS = igniteCacheyesS;
        this.igniteCachenoS = igniteCachenoS;
    }

    @Override
    public Long call() throws Exception {
        Long l1 = System.currentTimeMillis() ;
        try {
            working();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Long l2 = System.currentTimeMillis() ;
        return l2-l1;
    }

    private void working() {
        Map<String,AffinityMain> mainMap = new HashMap<>() ;
        CustObjBuild<AffinityMain> mainBuild = new CustObjBuild<>(AffinityMain.class) ;

        Map<AffinityItemYesKey,AffinityItemYes> yesMap = new HashMap<>() ;
        CustObjBuild<AffinityItemYes> yesBuild = new CustObjBuild<>(AffinityItemYes.class) ;

        Map<String,AffinityItemNo> noMap = new HashMap<>() ;
        CustObjBuild<AffinityItemNo> noBuild = new CustObjBuild<>(AffinityItemNo.class) ;
        for (int i = 0; i < enterParam.getCount(); i++) {
            if (mainMap.size() == enterParam.getBatchSize()) {
                System.out.println("提交：" + mainMap.size() + "条");
                igniteCacheS.addData(mainMap);
                igniteCacheS.flush();

                igniteCacheyesS.addData(yesMap);
                igniteCacheyesS.flush();

                igniteCachenoS.addData(noMap);
                igniteCachenoS.flush();
                mainMap.clear();
                yesMap.clear();
                noMap.clear();
            }
            //String randomKey1 = random.nextInt(enterParam.getCount()) + enterParam.getCount() + "";
            //String randomKey2 = random.nextInt(enterParam.getCount()) + enterParam.getCount() + "";
            //String randomKey3 = random.nextInt(enterParam.getCount()) + enterParam.getCount() + "";
            String randomKey = i+ "";
            String randomKey1 = IdGen.uuid()+"" ;
            String randomKey2 = IdGen.uuid()+"" ;
            String randomKey3 = IdGen.uuid()+"" ;
            AffinityMain mainObj = mainBuild.build1k(randomKey+"") ;
            mainMap.put(randomKey1+"",mainObj) ;

            AffinityItemYes yesObj = yesBuild.build1k(randomKey+"") ;
            AffinityItemYesKey yesKey = new AffinityItemYesKey(randomKey1+"",randomKey2+"");
            yesMap.put(yesKey,yesObj) ;

            AffinityItemNo noObj = noBuild.build1k(randomKey+"") ;
            noObj.setRange1(i);
            noObj.setRange2(i);
            noObj.setItemNoItem(new AffinityItemNoItem(i));
            noMap.put(randomKey3+"",noObj) ;

        }
        if (mainMap.size() > 0) {
            System.out.println("提交：" + mainMap.size() + "条");
            igniteCacheS.addData(mainMap);
            igniteCacheS.flush();

            igniteCacheyesS.addData(yesMap);
            igniteCacheyesS.flush();

            igniteCachenoS.addData(noMap);
            igniteCachenoS.flush();
            mainMap.clear();
            yesMap.clear();
            noMap.clear();
        }
    }
}
