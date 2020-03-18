package com.newland.boss.script.features;

import com.newland.boss.entity.user.User;
import com.newland.boss.entity.user.UserConfiguration;
import com.newland.ignite.query.api.BaseQuery;

/**
 * 3.2.1 基本sql能力 完成
 */
public class TestSqlScript extends BaseScript<String,User> {
    public TestSqlScript() {
        super(new UserConfiguration());
    }

    @Override
    public void work() {

        //删除全部
        System.out.println(">>> remove all");
        igniteCache.removeAll();
        // KV对插入
        User user1 = new User("1","1",1) ;
        igniteCache.putIfAbsent(user1.getId(),user1) ;
        System.out.println(">>> save one KV");
        // sql方式插入
        User user2 = new User("2","2",2) ;
        BaseQuery<String,User> baseQuery = new BaseQuery<>(ignite,cacheName) ;
        String sql = " insert into USER(_key,id,name,age) values (?,?,?,?)" ;
        Long insertRet = baseQuery.insert(sql,new Object[]{user2.getId(),user2.getId(), user2.getName(), user2.getAge()});

        System.out.println(">>> save sql insertRet:"+insertRet);
        getAll();

        System.out.println(">>> update user name and age :");
        user2.setName("22");
        user2.setAge(22);
        String updateSql = "update User set name=?,age=? where _key = ?" ;
        Long updateRet =  baseQuery.update(updateSql,new Object[]{user2.getName(),user2.getAge(), user2.getId()});
        System.out.println(">>> save sql updateRet:"+updateRet);

        getAll();

        igniteCache.remove("2");
        getAll();
        igniteCache.removeAll();
        getAll();

    }

    private void getAll(){
        System.out.println(">>> all user start :");
        Iterable<User> it = super.findAll();
        it.forEach(System.out::println);
        System.out.println(">>> all user end :");
    }

    public static void main(String[] args) {
        TestSqlScript scirpt = new TestSqlScript() ;
        scirpt.start();
    }

}
