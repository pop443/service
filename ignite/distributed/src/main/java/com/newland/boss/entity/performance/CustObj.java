package com.newland.boss.entity.performance;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by xz on 2020/3/10.
 */
public class CustObj {
    @QuerySqlField(index = true)
    private String id ;
    @QuerySqlField
    private String s01 ;
    @QuerySqlField
    private String s02 ;
    @QuerySqlField
    private String s03 ;
    @QuerySqlField
    private String s04 ;
    @QuerySqlField
    private String s05 ;
    @QuerySqlField
    private String s06 ;
    @QuerySqlField
    private String s07 ;
    @QuerySqlField
    private String s08 ;
    @QuerySqlField
    private String s09 ;
    @QuerySqlField
    private String s10 ;
    @QuerySqlField
    private String s11 ;
    @QuerySqlField
    private String s12 ;
    @QuerySqlField
    private String s13 ;
    @QuerySqlField
    private String s14;
    @QuerySqlField
    private String s15 ;
    @QuerySqlField
    private String s16 ;
    @QuerySqlField
    private String s17 ;
    @QuerySqlField
    private String s18 ;
    @QuerySqlField
    private String s19 ;
    @QuerySqlField
    private String s20 ;

    public CustObj(String id, String bytes) {
        this.id = id;
        this.s01 = bytes;
        this.s02 = bytes;
        this.s03 = bytes;
        this.s04 = bytes;
        this.s05 = bytes;
        this.s06 = bytes;
        this.s07 = bytes;
        this.s08 = bytes;
        this.s09 = bytes;
        this.s10 = bytes;
        this.s11 = bytes;
        this.s12 = bytes;
        this.s13 = bytes;
        this.s14 = bytes;
        this.s15 = bytes;
        this.s16 = bytes;
        this.s17 = bytes;
        this.s18 = bytes;
        this.s19 = bytes;
        this.s20 = bytes;
    }

    public CustObj(ResultSet rs) throws SQLException {
        this.id = rs.getString("id");
        this.s01 = rs.getString("s01");
        this.s02 = rs.getString("s02");
        this.s03 = rs.getString("s03");
        this.s04 = rs.getString("s04");
        this.s05 = rs.getString("s05");
        this.s06 = rs.getString("s06");
        this.s07 = rs.getString("s07");
        this.s08 = rs.getString("s08");
        this.s09 = rs.getString("s09");
        this.s10 = rs.getString("s10");
        this.s11 = rs.getString("s11");
        this.s12 = rs.getString("s12");
        this.s13 = rs.getString("s13");
        this.s14 = rs.getString("s14");
        this.s15 = rs.getString("s15");
        this.s16 = rs.getString("s16");
        this.s17 = rs.getString("s17");
        this.s18 = rs.getString("s18");
        this.s19 = rs.getString("s19");
        this.s20 = rs.getString("s20");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getS01() {
        return s01;
    }

    public void setS01(String s01) {
        this.s01 = s01;
    }

    public String getS02() {
        return s02;
    }

    public void setS02(String s02) {
        this.s02 = s02;
    }

    public String getS03() {
        return s03;
    }

    public void setS03(String s03) {
        this.s03 = s03;
    }

    public String getS04() {
        return s04;
    }

    public void setS04(String s04) {
        this.s04 = s04;
    }

    public String getS05() {
        return s05;
    }

    public void setS05(String s05) {
        this.s05 = s05;
    }

    public String getS06() {
        return s06;
    }

    public void setS06(String s06) {
        this.s06 = s06;
    }

    public String getS07() {
        return s07;
    }

    public void setS07(String s07) {
        this.s07 = s07;
    }

    public String getS08() {
        return s08;
    }

    public void setS08(String s08) {
        this.s08 = s08;
    }

    public String getS09() {
        return s09;
    }

    public void setS09(String s09) {
        this.s09 = s09;
    }

    public String getS10() {
        return s10;
    }

    public void setS10(String s10) {
        this.s10 = s10;
    }

    public String getS11() {
        return s11;
    }

    public void setS11(String s11) {
        this.s11 = s11;
    }

    public String getS12() {
        return s12;
    }

    public void setS12(String s12) {
        this.s12 = s12;
    }

    public String getS13() {
        return s13;
    }

    public void setS13(String s13) {
        this.s13 = s13;
    }

    public String getS14() {
        return s14;
    }

    public void setS14(String s14) {
        this.s14 = s14;
    }

    public String getS15() {
        return s15;
    }

    public void setS15(String s15) {
        this.s15 = s15;
    }

    public String getS16() {
        return s16;
    }

    public void setS16(String s16) {
        this.s16 = s16;
    }

    public String getS17() {
        return s17;
    }

    public void setS17(String s17) {
        this.s17 = s17;
    }

    public String getS18() {
        return s18;
    }

    public void setS18(String s18) {
        this.s18 = s18;
    }

    public String getS19() {
        return s19;
    }

    public void setS19(String s19) {
        this.s19 = s19;
    }

    public String getS20() {
        return s20;
    }

    public void setS20(String s20) {
        this.s20 = s20;
    }

    @Override
    public String toString() {
        return "CustObj{" +
                "id='" + id + '\'' +
                ", s01='" + s01 + '\'' +
                ", s02='" + s02 + '\'' +
                ", s03='" + s03 + '\'' +
                ", s04='" + s04 + '\'' +
                ", s05='" + s05 + '\'' +
                ", s06='" + s06 + '\'' +
                ", s07='" + s07 + '\'' +
                ", s08='" + s08 + '\'' +
                ", s09='" + s09 + '\'' +
                ", s10='" + s10 + '\'' +
                ", s11='" + s11 + '\'' +
                ", s12='" + s12 + '\'' +
                ", s13='" + s13 + '\'' +
                ", s14='" + s14 + '\'' +
                ", s15='" + s15 + '\'' +
                ", s16='" + s16 + '\'' +
                ", s17='" + s17 + '\'' +
                ", s18='" + s18 + '\'' +
                ", s19='" + s19 + '\'' +
                ", s20='" + s20 + '\'' +
                '}';
    }
}
