package net.sniff;

import javafx.util.Pair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xz on 2020/9/14.
 */
public class ignitedb {

    private List<Pair<String,String>> ignites ;
    private List<Pair<String,String>> zks ;
    private List<Pair<String,String>> apps ;

    @Before
    public void before(){
        ignites = new ArrayList<>() ;
        zks = new ArrayList<>() ;
        apps = new ArrayList<>() ;
    }


    @Test
    public void test1(){
        SniffUtil.init("1db.txt",ignites,zks,apps);
        //zk的
        for (Pair<String,String> pair1:ignites) {
            System.out.println("\r\n"+pair1.getKey());
            System.out.println("\r\nzk");
            for (Pair<String,String> pair2:zks) {
                System.out.println(SniffUtil.getCommond(pair1,pair2));
            }
            System.out.println("\r\n应用");
            for (Pair<String,String> pair2:apps) {
                System.out.println(SniffUtil.getCommond(pair1,pair2));
            }
        }

    }
    @Test
    public void test2(){
        SniffUtil.init("2db.txt",ignites,zks,apps);
        //zk的
        for (Pair<String,String> pair1:ignites) {
            System.out.println("\r\n"+pair1.getKey());
            System.out.println("\r\nzk");
            for (Pair<String,String> pair2:zks) {
                System.out.println(SniffUtil.getCommond(pair1,pair2));
            }
            System.out.println("\r\n应用");
            for (Pair<String,String> pair2:apps) {
                System.out.println(SniffUtil.getCommond(pair1,pair2));
            }
        }

    }
    @Test
    public void test3(){
        SniffUtil.init("3db.txt",ignites,zks,apps);
        //zk的
        for (Pair<String,String> pair1:ignites) {
            System.out.println("\r\n"+pair1.getKey());
            System.out.println("\r\nzk");
            for (Pair<String,String> pair2:zks) {
                System.out.println(SniffUtil.getCommond(pair1,pair2));
            }
            System.out.println("\r\n应用");
            for (Pair<String,String> pair2:apps) {
                System.out.println(SniffUtil.getCommond(pair1,pair2));
            }
        }

    }

    @Test
    public void test4(){
        SniffUtil.init("4db.txt",ignites,zks,apps);
        //zk的
        for (Pair<String,String> pair1:ignites) {
            System.out.println("\r\n"+pair1.getKey());
            System.out.println("\r\nzk");
            for (Pair<String,String> pair2:zks) {
                System.out.println(SniffUtil.getCommond(pair1,pair2));
            }
            System.out.println("\r\n应用");
            for (Pair<String,String> pair2:apps) {
                System.out.println(SniffUtil.getCommond(pair1,pair2));
            }
        }

    }

}
