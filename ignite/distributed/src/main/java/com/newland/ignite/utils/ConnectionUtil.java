package com.newland.ignite.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by xz on 2020/3/6.
 */
public class ConnectionUtil {

    public static void release(Connection conn){
        if (conn!=null){
            try {
                if (!conn.isClosed()){
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void release(PreparedStatement pstm){
        if (pstm!=null){
            try {
                if (!pstm.isClosed()){
                    pstm.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                try {
                    pstm.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void release(ResultSet rs){
        if (rs!=null){
            try {
                if (!rs.isClosed()){
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void release(ResultSet rs,PreparedStatement pstm,Connection conn){
        release(rs);
        release(pstm);
        release(conn);
    }


    public static void release(ResultSet rs, PreparedStatement pstm) {
        release(rs);
        release(pstm);
    }
}
