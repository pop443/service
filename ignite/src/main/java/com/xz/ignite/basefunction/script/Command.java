package com.xz.ignite.basefunction.script;

import com.xz.ignite.basefunction.cachestore.entity.Course;
import com.xz.ignite.basefunction.cachestore.entity.UserInfo;
import org.apache.ignite.IgniteTransactions;
import org.apache.ignite.internal.commandline.CommandHandler;
import org.apache.ignite.transactions.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by xz on 2020/2/18.
 */
public class Command {
    private CommandHandler hnd = null;
    private String uuid = null ;
    @Before
    public void before(){
        uuid = "4315d415-fae2-45f9-acf6-2ce7d8ed2836" ;
        hnd = new CommandHandler();
    }
    @Test
    public void cache(){
        String temp = "--host 172.32.148.243 --port 11211 --cache list .* "+uuid ;
        hnd.execute(Arrays.asList(temp.split(" ")));
    }




    @After
    public void after(){

    }
}
