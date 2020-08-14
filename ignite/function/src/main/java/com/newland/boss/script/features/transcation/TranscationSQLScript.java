package com.newland.boss.script.features.transcation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Random;

/**
 * 3.1.1 导出能力
 */
public class TranscationSQLScript {

    public TranscationSQLScript() {

    }

    public void start() throws Exception {
        Random random = new Random() ;
        boolean bo = random.nextBoolean() ;
        bo = false ;
        Class.forName("org.apache.ignite.IgniteJdbcThinDriver") ;
        Connection conn = DriverManager.getConnection("jdbc:ignite:thin://172.32.148.244:10800") ;
        conn.setAutoCommit(false);

        PreparedStatement pstm = conn.prepareStatement("DELETE  FROM NEWLAND.TRANSCATIONCACHE1 ");
        pstm.execute();
        pstm = conn.prepareStatement("DELETE  FROM NEWLAND.TRANSCATIONCACHE2 ");
        pstm.execute();
        conn.commit();

        try {
            pstm = conn.prepareStatement("INSERT INTO NEWLAND.TRANSCATIONCACHE1 (_key,id,age) VALUES ('2','2',2)");
            pstm.executeUpdate();

            if (bo){
                throw new Exception("");
            }

            pstm = conn.prepareStatement("INSERT INTO NEWLAND.TRANSCATIONCACHE2 (_key,id,age) VALUES ('2','2',2)");
            pstm.executeUpdate();
            conn.commit();
            System.out.println("------执行程序 正常提交-----");
        } catch (Exception e) {
            System.out.println("------中断程序 回滚-----");
        }finally {
            conn.rollback();
            pstm.close();
            conn.close();

        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("---sql 乐观锁---");
        TranscationSQLScript scirpt = new TranscationSQLScript();
        scirpt.start();

    }

}
