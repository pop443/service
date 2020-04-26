package com.newland.boss.entity.performance.complex;

import com.newland.boss.entity.performance.CustObj;
import org.apache.ignite.cache.query.annotations.QuerySqlField;

/**
 * Created by xz on 2020/4/22.
 */
public class ComplexKey{
    @QuerySqlField(index = true)
    private String keyId  ;
    @QuerySqlField(index = true,descending = false)
    private Long keyUserId ;
    @QuerySqlField(index = true,descending = true)
    private Long keyUserId2 ;
    @QuerySqlField(index = true,descending = false)
    private String keyUserStr  ;
    @QuerySqlField(index = true,descending = true)
    private String keyUserStr2  ;
    @QuerySqlField
    private ComplexKeyItem complexKeyItem ;

    public ComplexKey(String keyId, Long keyUserId, Long keyUserId2, String keyUserStr, String keyUserStr2) {
        this.keyId = keyId;
        this.keyUserId = keyUserId;
        this.keyUserId2 = keyUserId2;
        this.keyUserStr = keyUserStr;
        this.keyUserStr2 = keyUserStr2;
        ComplexKeyItem complexKeyItem = new ComplexKeyItem(keyId,keyUserId,keyUserId2,keyUserStr,keyUserStr2);
        this.complexKeyItem = complexKeyItem;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public Long getKeyUserId() {
        return keyUserId;
    }

    public void setKeyUserId(Long keyUserId) {
        this.keyUserId = keyUserId;
    }

    public Long getKeyUserId2() {
        return keyUserId2;
    }

    public void setKeyUserId2(Long keyUserId2) {
        this.keyUserId2 = keyUserId2;
    }

    public String getKeyUserStr() {
        return keyUserStr;
    }

    public void setKeyUserStr(String keyUserStr) {
        this.keyUserStr = keyUserStr;
    }

    public String getKeyUserStr2() {
        return keyUserStr2;
    }

    public void setKeyUserStr2(String keyUserStr2) {
        this.keyUserStr2 = keyUserStr2;
    }

    public ComplexKeyItem getComplexKeyItem() {
        return complexKeyItem;
    }

    public void setComplexKeyItem(ComplexKeyItem complexKeyItem) {
        this.complexKeyItem = complexKeyItem;
    }
}
