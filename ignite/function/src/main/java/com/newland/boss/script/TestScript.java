package com.newland.boss.script;

import com.newland.boss.entity.test.TestIgnite;
import com.newland.boss.entity.test.TestIgniteConfiguration;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.binary.BinaryObject;

/**
 * 测试的脚本
 */
public class TestScript extends BaseScript<String,TestIgnite> {

    public TestScript() {
        super(new TestIgniteConfiguration());
    }

    @Override
    protected void work() {
        TestIgnite testIgnite = new TestIgnite("1","1",1) ;
        igniteCache.remove(testIgnite.getId()) ;
        igniteCache.putIfAbsent(testIgnite.getId(),testIgnite);
        igniteCache.withKeepBinary();
        IgniteCache<String,BinaryObject> ic = igniteCache.withKeepBinary() ;
        BinaryObject binaryObject = ic.get("2") ;
        System.out.println("测试结果:"+binaryObject.field("remark"));
        TestIgnite ret =igniteCache.get("2");
        System.out.println("测试结果:"+ret);
    }

    public static void main(String[] args) {
        TestScript scirpt = new TestScript() ;
        scirpt.start();
    }

}
