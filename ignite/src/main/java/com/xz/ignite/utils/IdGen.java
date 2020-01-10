package com.xz.ignite.utils;

import java.util.Calendar;
import java.util.UUID;

/**
 * Created by Administrator on 2019/12/27.
 */
public class IdGen {
    public static String uuid() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "") ;
        return System.currentTimeMillis()+uuid;
    }

    public static void main(String[] args) {
        IdGen.uuid() ;
    }
}
