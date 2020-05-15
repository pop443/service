package com.newland.boss.script.performance.randomwr.partitionsmallEPPG;

import com.newland.boss.entity.performance.CustObjBuild;
import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import com.newland.boss.script.performance.randomw.partitionsmallEPput.GetEp1;
import com.newland.boss.script.performance.randomw.partitionsmallEPput.PutEp1;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.binary.BinaryObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xz on 2020/3/10.
 */
public class PartitionSmallEpPutGetScriptWork extends PerformanceScriptWork<String, PartitionCustObj> {
    private IgniteCache<String,BinaryObject> ic ;
    public PartitionSmallEpPutGetScriptWork(EnterParam enterParam, IgniteCache<String, PartitionCustObj> igniteCache, IgniteDataStreamer<String, PartitionCustObj> igniteDataStreamer, Integer baseKey) {
        super(enterParam, igniteCache, igniteDataStreamer,baseKey);
        ic = igniteCache.withKeepBinary() ;
    }

    @Override
    public long doing() {
        CustObjBuild<PartitionCustObj> build = new CustObjBuild<>(PartitionCustObj.class);
        List<PartitionCustObj> list = new ArrayList<>() ;
        System.out.println("数据构造 start");
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i + baseKey + "";
            PartitionCustObj obj1 = build.build1k(randomKey );
            list.add(obj1);
        }
        System.out.println("数据构造 end");
        long l1 = System.currentTimeMillis() ;
        for (PartitionCustObj obj:list) {
            ic.invoke(obj.getId()+ "-1",new PutEp1(),IgniteUtil.toBinary(obj)) ;
            ic.invoke(obj.getId()+ "-2",new PutEp1(),IgniteUtil.toBinary(obj)) ;
            ic.invoke(obj.getId()+ "-3",new PutEp1(),IgniteUtil.toBinary(obj)) ;
            ic.invoke(obj.getId()+ "-4",new PutEp1(),IgniteUtil.toBinary(obj)) ;
            ic.invoke(obj.getId()+ "-1",new GetEp1()) ;
            ic.invoke(obj.getId()+ "-2",new GetEp1()) ;
            ic.invoke(obj.getId()+ "-3",new GetEp1()) ;
            ic.invoke(obj.getId()+ "-4",new GetEp1()) ;
        }
        long l2 = System.currentTimeMillis() ;
        return l2-l1 ;
    }

}
