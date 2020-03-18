package com.newland.boss.script.features;

import com.newland.boss.entity.expired.Expired;
import com.newland.boss.entity.expired.ExpiredConfiguration;

/**
 * 测试的脚本
 */
public class TestExpired extends BaseScript<String,Expired> {

    public TestExpired() {
        super(new ExpiredConfiguration());
    }

    @Override
    public void work() {
        System.out.println(">>> 删除所有失效缓存数据");
        igniteCache.removeAll();

        Expired expired = new Expired("1");
        igniteCache.put(expired.getData(),expired);
        System.out.println(">>> 插入失效数据："+expired);
        try {
            Expired expired1 = igniteCache.get("1") ;
            int i = 0 ;
            while (expired1!=null){
                i++;
                System.out.println("第"+i+"秒");
                Thread.sleep(1000L);
                expired1 = igniteCache.get("1") ;
                System.out.println("失效缓存数据为："+expired1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        System.out.println("默认失效时间20秒");
        TestExpired scirpt = new TestExpired() ;
        scirpt.start();
    }

}
