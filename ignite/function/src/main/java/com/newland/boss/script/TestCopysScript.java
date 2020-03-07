package com.newland.boss.script;

import com.newland.boss.entity.user.User;
import com.newland.boss.entity.user.UserConfiguration;

/**
 * 3.3.1 多副本测试
 */
public class TestCopysScript extends BaseScript<String,User> {
    public TestCopysScript() {
        super(new UserConfiguration());
    }

    @Override
    public void work() {
        User user = new User("1","1",1) ;
        if (!igniteCache.containsKey(user.getId())){
            igniteCache.putIfAbsent(user.getId(),user) ;
        }
        User one = igniteCache.get(user.getId());
        System.out.println(">>> get user:"+one);

    }

    public static void main(String[] args) {
        TestCopysScript scirpt = new TestCopysScript() ;
        scirpt.start();
    }

}
