package com.xz.netty.demo.gcust.entity.transmission;

import com.xz.netty.demo.dserializable.protostaff.ProtostuffUtil;
import com.xz.netty.entity.User;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by xz on 2020/2/1.
 */
public class HeaderTest {
    @Test
    public void test() throws Exception {
        Header header = new Header() ;
        Map<String,Object> map = new HashMap<>() ;
        map.put("1",new User("1",2)) ;
        header.setMap(map);

        byte[] bytes = ProtostuffUtil.serialize(header) ;
        Header header2 = ProtostuffUtil.deserialize(bytes,Header.class) ;
        header2.getMap().values().forEach(object->{
            User user = (User) object;
            System.out.println(user.getAge());
        });
        System.out.println();

    }

}