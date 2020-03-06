package com.newland.ignite.upload.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Administrator on 2019/12/27.
 */
public class BeanUtil {
    public static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static Random random = new Random();
    private static Calendar calendar = Calendar.getInstance();
    private static AtomicLong user_id = new AtomicLong(2000000000000000L) ;
    private static AtomicLong msisdn = new AtomicLong(13675110430L) ;


    public static Integer month_number(int range) {
        return randomNumber(range);
    }

    public static Integer randomNumber(int range) {
        return random.nextInt(range);
    }

    //可重复
    public static Long user_id(int range) {
        Long l = 2000000000000000L;
        return l + random.nextInt(range);
    }
    //不可重复
    public static Long user_id() {
        return user_id.addAndGet(1);
    }
    //可重复
    public static Long msisdn(int range) {
        Long l = 13675110430L;
        return l + random.nextInt(range) ;
    }
    //不可重复
    public static Long msisdn() {
        return msisdn.addAndGet(1) ;
    }

    public static Long traffic_cnt(int range) {
        Long l = 10000L;
        return l + random.nextInt(range);
    }



    public static String randomString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
        }
        return sb.toString();
    }

    public static synchronized Integer randomDate(int range) {
        int num = randomNumber(range) ;
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, num);
        return Integer.parseInt(calendar.get(Calendar.YEAR) +String.format("%02d", calendar.get(Calendar.MONTH)+1) +String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)));
    }

    public static void main(String[] args) {
        System.out.println(randomDate(10));
        System.out.println(randomDate(10));
    }

}
