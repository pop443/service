package com.newland.boss.script;

import com.newland.boss.entity.test.TestIgnite;
import com.newland.boss.entity.test.TestIgniteConfiguration;
import org.apache.ignite.IgniteCache;

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
        igniteCache.putIfAbsent(testIgnite.getId(),testIgnite); ;
        TestIgnite ret = igniteCache.get(testIgnite.getId()) ;
        System.out.println("测试结果:"+ret);
    }

    public static void main(String[] args) {
        TestScript scirpt = new TestScript() ;
        scirpt.start();
    }

}
