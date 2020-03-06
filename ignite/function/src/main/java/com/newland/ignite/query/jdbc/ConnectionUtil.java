package com.newland.ignite.query.jdbc;

import java.sql.*;
import java.util.Properties;

/**
 * Created by Administrator on 2019/11/26.
 */
public class ConnectionUtil {


    public static Connection getConnection() throws SQLException{
        Properties properties = DBConfig.getProperties("igniteJDBC.properties") ;
        String url = properties.getProperty("ignite.url") ;
        String username = properties.getProperty("ignite.username") ;
        String password = properties.getProperty("ignite.password") ;
        return DriverManager.getConnection(url,username,password) ;
    }


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
