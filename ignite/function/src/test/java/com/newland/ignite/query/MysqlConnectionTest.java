package com.newland.ignite.query;

import com.newland.ignite.query.mysql.MysqlConnection;
import com.newland.ignite.utils.ConnectionUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by xz on 2020/3/6.
 */
public class MysqlConnectionTest {

    private Connection conn = null ;
    private PreparedStatement pstm = null ;
    private ResultSet rs = null ;
    @Before
    public void Before(){

    }
    @Test
    public void Test(){
        try {
            conn = MysqlConnection.getConnection() ;
            pstm = conn.prepareStatement("SELECT COUNT(1) FROM sys.metrics") ;
            rs = pstm.executeQuery() ;
            while (rs.next()){
                System.out.println(rs.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @After
    public void After(){
        ConnectionUtil.release(rs,pstm,conn);
    }

}