package com.newland.boss.entity.performance.bulk;

import com.newland.boss.utils.DiffString;
import org.apache.ignite.cache.query.annotations.QueryTextField;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by xz on 2020/8/7.
 */
public class TextObj {

    @QueryTextField
    private String id ;
    @QueryTextField
    private String s01 ;
    @QueryTextField
    private String s02 ;
    @QueryTextField
    private String s03 ;
    @QueryTextField
    private String s04 ;
    @QueryTextField
    private String s05 ;
    @QueryTextField
    private String s06 ;
    @QueryTextField
    private String s07 ;
    @QueryTextField
    private String s08 ;
    @QueryTextField
    private String s09 ;
    @QueryTextField
    private String s10 ;
    @QueryTextField
    private String s11 ;
    @QueryTextField
    private String s12 ;
    @QueryTextField
    private String s13 ;
    @QueryTextField
    private String s14;
    @QueryTextField
    private String s15 ;
    @QueryTextField
    private String s16 ;
    @QueryTextField
    private String s17 ;
    @QueryTextField
    private String s18 ;
    @QueryTextField
    private String s19 ;
    @QueryTextField
    private String s20 ;

    public TextObj(String id, String bytes) {
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
        return "TextObj{" +
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
