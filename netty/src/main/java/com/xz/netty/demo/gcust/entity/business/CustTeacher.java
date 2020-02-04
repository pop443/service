package com.xz.netty.demo.gcust.entity.business;

import com.xz.netty.demo.gcust.entity.transmission.BaseBusiness;

/**
 * Created by xz on 2020/2/3.
 */
public class CustTeacher extends BaseBusiness{
    private String id ;
    private String name ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CustTeacher{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
