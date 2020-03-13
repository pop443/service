package com.newland.boss.script.performance.randomw.partitionbigstreamput;

import com.newland.boss.entity.performance.Constant;
import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.obj.PartitionBigCustObj;
import com.newland.boss.entity.performance.obj.PartitionSmallCustObj;
import org.apache.ignite.IgniteDataStreamer;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;

/**
 * Created by xz on 2020/3/10.
 */
public class PartitionBigSteamPutScriptWork implements Callable<Long> {
    private int eachSize ;
    private int commitSize ;
    private IgniteDataStreamer<String, PartitionBigCustObj> ids ;
    private Random random ;
    private int count ;
    public PartitionBigSteamPutScriptWork(int eachSize, int count, IgniteDataStreamer<String, PartitionBigCustObj> ids) {
        this.eachSize = eachSize ;
        this.random = new Random() ;
        this.count = count ;
        this.ids = ids ;
        this.commitSize = Constant.batchSize ;
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
        Map<String,PartitionBigCustObj> map = new HashMap<>() ;
        CustObjBuild<PartitionBigCustObj> build = new CustObjBuild<>(PartitionBigCustObj.class) ;
        for (int i = 0; i < eachSize; i++) {
            String randomKey = random.nextInt(count)+count+"" ;
            if (map.size()==commitSize){
                ids.addData(map);
                ids.flush();
                map.clear();
                System.out.println("提交"+commitSize+"条");
            }
            PartitionBigCustObj obj = build.build1k(randomKey+"") ;
            map.put(obj.getId(),obj) ;
        }
        System.out.println("----"+map.size());
        if (map.size()>0){
            ids.addData(map);
            ids.flush();
            map.clear();
        }
    }
}
