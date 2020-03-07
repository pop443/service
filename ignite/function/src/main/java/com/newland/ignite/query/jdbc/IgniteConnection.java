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
        String url = properties.getProperty("ignite.url") ;
        String username = properties.getProperty("ignite.username") ;
        String password = properties.getProperty("ignite.password") ;
        return DriverManager.getConnection(url,username,password) ;
    }

}
