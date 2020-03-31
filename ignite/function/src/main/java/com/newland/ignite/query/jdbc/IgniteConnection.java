package com.newland.ignite.query.jdbc;

import com.newland.ignite.utils.ConnectionUtil;
import com.newland.ignite.utils.PropertiesConfig;

import java.sql.*;
import java.util.Properties;

/**
 * Created by Administrator on 2019/11/26.
 */
public class IgniteConnection extends ConnectionUtil {
    private static Properties properties = PropertiesConfig.getProperties("igniteJDBC.properties");

    public static Connection getConnection() throws SQLException{
        String driver = properties.getProperty("ignite.driver") ;
        String url = properties.getProperty("ignite.url") ;
        String username = properties.getProperty("ignite.username") ;
        String password = properties.getProperty("ignite.password") ;
        return DriverManager.getConnection(url,username,password) ;
    }

    public static void main(String[] args) throws SQLException{
        Connection conn = IgniteConnection.getConnection() ;
        PreparedStatement pstm = conn.prepareStatement("select 1,2");
        ResultSet rs = pstm.executeQuery() ;
        while (rs.next()){
            System.out.println(rs.getInt(1)+"--"+rs.getInt(2));
        }
        ConnectionUtil.release(rs,pstm,conn);
    }

}
