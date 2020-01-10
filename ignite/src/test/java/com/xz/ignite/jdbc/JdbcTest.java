package com.xz.ignite.jdbc;

import com.xz.ignite.basefunction.query.jdbc.ConnectionUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Administrator on 2020/1/3.
 */
public class JdbcTest {
    private Connection conn ;
    @Before
    public void before(){
        try {
            conn = ConnectionUtil.getConnection() ;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void upload(){
        PreparedStatement pstm = null ;
        ResultSet rs = null ;
        try {
            String sql = "SELECT t.STATUS,count(1) AS COUNTS FROM GRPCRMCUSTMON t GROUP BY t.STATUS  HAVING t.STATUS> ? " ;
            pstm = conn.prepareStatement(sql) ;
            pstm.setInt(1,3);
            rs = pstm.executeQuery() ;
            while (rs.next()){
                String status = rs.getString(1) ;
                Long counts = rs.getLong(2) ;
                System.out.println(status+"---"+counts);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionUtil.release(rs,pstm);
        }


    }

    @After
    public void after(){
        ConnectionUtil.release(conn);
    }

}
