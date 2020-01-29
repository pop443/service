package com.xz.netty.demo.dserializable.protostaff;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

/**
 * Created by xz on 2020/1/29.
 */
public class ProtostuffUtil {

    private static Map<Class<?>,Schema<?>> map = new ConcurrentHashMap<>() ;

    private static <T> Schema<T> getSchema(Class<T> cz){
        if (!map.containsKey(cz)){
            Schema<T> schema = RuntimeSchema.getSchema(cz);
            map.put(cz,schema) ;
        }
        @SuppressWarnings("unchecked")
        Schema<T> schema = (Schema<T>)map.get(cz) ;
        return schema ;
    }

    public static <T> byte[] serialize(T obj) {
        if (obj == null) {
            throw new RuntimeException("序列化对象( null )!");
        }
        @SuppressWarnings("unchecked")
        Schema<T> schema = getSchema((Class<T>)obj.getClass()) ;
        LinkedBuffer buffer = LinkedBuffer.allocate(1024 * 1024);
        byte[] protostuff = null;
        try {
            protostuff = ProtostuffIOUtil.toByteArray(obj, schema, buffer);
        } catch (Exception e) {
            throw new RuntimeException("序列化(" + obj.getClass() + ")对象(" + obj + ")发生异常!", e);
        } finally {
            buffer.clear();
        }
        return protostuff;
    }

    public static <T> T deserialize(byte[] paramArrayOfByte, Class<T> targetClass) {
        if (paramArrayOfByte == null || paramArrayOfByte.length == 0) {
            throw new RuntimeException("反序列化对象发生异常,byte序列为空!");
        }
        T instance = null;
        try {
            instance = targetClass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("反序列化过程中 创建对象 没有默认构造器!", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("默认构造器 私有!", e);
        }
        Schema<T> schema = getSchema(targetClass) ;
        ProtostuffIOUtil.mergeFrom(paramArrayOfByte, instance, schema);
        return instance;
    }

    public static <T> byte[] serializeList(List<T> objList) {
        if (objList == null || objList.isEmpty()) {
            throw new RuntimeException("序列化对象列表无数据!");
        }
        @SuppressWarnings("unchecked")
        Schema<T> schema = getSchema((Class<T>)objList.get(0).getClass()) ;
        LinkedBuffer buffer = LinkedBuffer.allocate(1024 * 1024);
        byte[] protostuff = null;
        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream();
            ProtobufIOUtil.writeListTo(bos, objList, schema, buffer);
            protostuff = bos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("序列化对象列表(" + objList + ")发生异常!", e);
        } finally {
            buffer.clear();
            try {
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return protostuff;
    }

    public static <T> List<T> deserializeList(byte[] paramArrayOfByte, Class<T> targetClass) {
        if (paramArrayOfByte == null || paramArrayOfByte.length == 0) {
            throw new RuntimeException("反序列化对象 null !");
        }

        Schema<T> schema = getSchema(targetClass) ;
        List<T> result = null;
        try {
            result = ProtobufIOUtil.parseListFrom(new ByteArrayInputStream(paramArrayOfByte), schema);
        } catch (IOException e) {
            throw new RuntimeException("反序列化对象列表发生异常!", e);
        }
        return result;
    }
}
