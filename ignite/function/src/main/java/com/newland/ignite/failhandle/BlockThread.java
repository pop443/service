package com.newland.ignite.failhandle;

import com.newland.boss.entity.performance.obj.PartitionCustObj;
import com.newland.boss.entity.performance.obj.PartitionCustObjConfiguration;
import com.newland.boss.script.features.BaseScript;
import com.newland.ignite.entryprocessor.LongSleepEp;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.transactions.TransactionException;

/**
 * Created by xz on 2020/8/18.
 */
public class BlockThread extends BaseScript<String,PartitionCustObj> {
    public BlockThread() {
        super(new PartitionCustObjConfiguration());
    }
    @Override
    public void work() {
        try {
            IgniteCache<String,BinaryObject> ic = igniteCache.withKeepBinary();
            PartitionCustObj partitionCustObj = new PartitionCustObj("1","1") ;
            boolean bo = ic.invoke("1",new LongSleepEp(), IgniteUtil.toBinary(partitionCustObj));
            System.out.println(bo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BlockThread scirpt = new BlockThread() ;
        scirpt.start();
    }}
