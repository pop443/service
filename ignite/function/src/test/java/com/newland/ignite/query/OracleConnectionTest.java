package com.newland.ignite.query;

import com.newland.ignite.query.mysql.MysqlConnection;
import com.newland.ignite.query.oracle.OracleConnection;
import com.newland.ignite.utils.ConnectionUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xz on 2020/3/6.
 */
public class OracleConnectionTest {

    private Connection conn = null;
    private PreparedStatement pstm = null;
    private ResultSet rs = null;

    @Before
    public void Before() {

    }

    @Test
    public void Test() {
        try {
            DataSource druidDataSource = OracleConnection.getDruidDataSource();
                conn = druidDataSource.getConnection() ;
                pstm = conn.prepareStatement("select count(1) from account_base_cfg") ;
                rs = pstm.executeQuery() ;
                while (rs.next()){
                    System.out.println(rs.getLong(1));
                }
            Thread.sleep(20000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void After() {
        ConnectionUtil.release(rs, pstm, conn);
    }

}