package com.xz.netty.demo.gcust.entity.business;

import com.xz.netty.demo.gcust.entity.transmission.BaseBusiness;

/**
 * Created by xz on 2020/2/1.
 */
public class CustUser extends BaseBusiness {
    private String name ;
    private String password ;
    private Integer age ;
    private String remark ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
