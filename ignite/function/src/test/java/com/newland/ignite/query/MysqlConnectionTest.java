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
import java.util.ArrayList;
import java.util.List;

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
            List<String> list = new ArrayList<>() ;
            list.add("1") ;
            list.add("2") ;
            list.add("30") ;
            conn = MysqlConnection.getConnection() ;
            pstm = conn.prepareStatement("SELECT id ,name ,uidd FROM ignite.course where id=?") ;
            for (int i = 0; i < list.size(); i++) {
                pstm.setString(1,list.get(i));
                pstm.addBatch();
            }
            pstm.execute();
            rs = pstm.getResultSet() ;
            while (rs.next()){
                System.out.println(rs.getString(1)+"--"+rs.getString(2)+"--"+rs.getString(3));
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