package com.xz.ignite.basefunction.cachestore.mysql.config;

import com.xz.ignite.basefunction.cachestore.entity.UserInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.cache.integration.CacheWriterException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * Created by xz on 2020/2/9.
 */
public class MysqlConnectionTest {
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        conn = MysqlConnection.getConnection();
    }

    @After
    public void tearDown() throws Exception {
        MysqlConnection.release(conn);
    }

    @Test
    public void getConnection() throws Exception {
        PreparedStatement pstm = conn.prepareStatement("select * from userinfo");
        ResultSet rs = pstm.executeQuery();
        while (rs.next()) {
            UserInfo userInfo = new UserInfo(rs);
            System.out.println(userInfo.toString());
        }
        MysqlConnection.release(rs, pstm);
    }

    @Test
    public void testmerge() throws Exception {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String key = "2";
        boolean insert = true;

        try {
            pstm = conn.prepareStatement("SELECT 1 FROM userinfo WHERE id=?");
            pstm.setString(1, key);
            rs = pstm.executeQuery();
            if (rs.next()) {
                insert = false;
            }
            String sql = null;
            UserInfo userInfo = new UserInfo("3", "3", "3", "3");
            if (insert) {
                sql = "INSERT INTO userinfo (id,NAME, PASSWORD, remark) VALUES (?,?,?,?)";
                pstm = conn.prepareStatement(sql);
                pstm.setString(1, userInfo.getId());
                pstm.setString(2, userInfo.getName());
                pstm.setString(3, userInfo.getPassword());
                pstm.setString(4, userInfo.getRemark());
            } else {
                sql = "UPDATE userinfo SET NAME=?,PASSWORD=?,remark=? WHERE id = ?";
                pstm = conn.prepareStatement(sql);
                pstm.setString(1, userInfo.getName());
                pstm.setString(2, userInfo.getPassword());
                pstm.setString(3, userInfo.getRemark());
                pstm.setString(4, key);
            }
            int i = pstm.executeUpdate();
            System.out.println(i);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            MysqlConnection.release(rs,pstm);
        }
    }

}