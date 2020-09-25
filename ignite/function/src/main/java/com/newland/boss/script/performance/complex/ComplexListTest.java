package com.newland.boss.script.performance.complex;

import com.newland.boss.entity.performance.complex.ComplexList;
import com.newland.boss.entity.performance.complex.ComplexListConfiguration;
import com.newland.boss.entity.performance.complex.ComplexValueItem2;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteBinary;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.binary.BinaryObjectBuilder;
import org.apache.ignite.cache.query.*;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.lang.IgniteBiPredicate;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.cache.Cache;
import javax.cache.processor.EntryProcessor;
import javax.cache.processor.EntryProcessorException;
import javax.cache.processor.MutableEntry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by xz on 2020/4/24.
 */
public class ComplexListTest {
    private Ignite ignite ;
    private IgniteCache<String,ComplexList> igniteCache ;
    @Before
    public void before(){
        ignite = IgniteUtil.getIgnite() ;
        CacheConfiguration<String,ComplexList> cfg = new ComplexListConfiguration().getCacheConfiguration();
        igniteCache = ignite.getOrCreateCache(cfg) ;
    }

    @Test
    public void put(){
        for (int i = 0; i < 20; i++) {
            ComplexList complexList = new ComplexList(i) ;
            igniteCache.put(complexList.getId(),complexList);
        }
    }

    @Test
    public void ep(){
        IgniteCache<String,BinaryObject> ic = igniteCache.withKeepBinary() ;
        BinaryObject binaryObject = ic.invoke("1", new EntryProcessor<String, BinaryObject, BinaryObject>() {

            @IgniteInstanceResource
            private Ignite ignite ;

            @Override
            public BinaryObject process(MutableEntry<String, BinaryObject> mutableEntry, Object... objects) throws EntryProcessorException {
                BinaryObject binaryObject1 = mutableEntry.getValue() ;
                System.out.println("\r\n"+binaryObject1.field("objArray").getClass()+"\r\n");
                Object[] o = binaryObject1.field("objArray") ;
                for (Object b:o) {
                    System.out.println("\r\n"+b.getClass()+"\r\n");
                    BinaryObject binaryObject2 = (BinaryObject)b ;
                    System.out.println(binaryObject2.field("item2Id")+"--"+binaryObject2.field("age"));
                }
                IgniteBinary igniteBinary = ignite.binary() ;
                BinaryObjectBuilder builder = igniteBinary.builder("com.newland.boss.entity.performance.complex.ComplexList");

                BinaryObjectBuilder builderItem = igniteBinary.builder("com.newland.boss.entity.performance.complex.ComplexValueItem2");
                builderItem.setField("age",1) ;
                List<BinaryObject> list = new ArrayList<>() ;
                list.add(builderItem.build()) ;
                builder.setField("objList",list) ;
                return builder .build();
            }
        }) ;

        ComplexList complexList = binaryObject.deserialize() ;
        System.out.println(complexList);
    }


    @Test
    public void sql(){
        SqlFieldsQuery qry = new SqlFieldsQuery("select _key,stringArray[0],objArray[0] from NEWLAND.COMPLEXLIST");
        FieldsQueryCursor<List<?>> fieldsQueryCursor = igniteCache.query(qry) ;
        try {
            Iterator<List<?>> it = fieldsQueryCursor.iterator() ;
            while (it.hasNext()){
                List<?> list = it.next() ;
                String key = (String)list.get(0) ;
                String value = (String)list.get(1) ;
                ComplexValueItem2 complexValueItem2 = (ComplexValueItem2)list.get(2) ;
                System.out.println(key+"---"+value+"---"+complexValueItem2);
            }
        } finally {
            fieldsQueryCursor.close();
        }
    }

    @Test
    public void scanquery(){
        ScanQuery<String,ComplexList> scanQuery = new ScanQuery<>(new IgniteBiPredicate<String,ComplexList>() {
            @Override
            public boolean apply(String s, ComplexList complexList) {
                System.out.println("111111111111");
                List<ComplexValueItem2> list = complexList.getObjList() ;
                if (list.get(0).getItem2Id().equals("1!1")){
                    return true ;
                }
                return false;
            }
        }) ;
        QueryCursor cursor = igniteCache.query(scanQuery) ;
        try {
            Iterator<List<?>> it = cursor.iterator() ;
            while (it.hasNext()){
                List<?> list = it.next() ;
                String key = (String)list.get(0) ;
                String value = (String)list.get(1) ;
                ComplexValueItem2 complexValueItem2 = (ComplexValueItem2)list.get(2) ;
                System.out.println(key+"---"+value+"---"+complexValueItem2);
            }
        } finally {
            cursor.close();
        }
    }

    @Test
    public void scanquerybinary(){

        for (int i = 0; i < 3; i++) {
            int index = i ;
            ScanQuery<String,BinaryObject> scanQuery = new ScanQuery<>(new IgniteBiPredicate<String,BinaryObject>() {
                @Override
                public boolean apply(String s, BinaryObject binaryObject) {
                    System.out.println(index+"---key:"+s);
                    List<Object> list = binaryObject.field("objList") ;
                    for (Object object:list) {
                        BinaryObject binaryObject1 = (BinaryObject)object ;
                        String ss = binaryObject1.field("item2Id") ;
                        if (ss.equals(index+"!1")){
                            return true ;
                        }
                    }
                    return false;
                }
            }) ;
            IgniteCache<String,BinaryObject> ic = igniteCache.withKeepBinary() ;
            QueryCursor<Cache.Entry<String,BinaryObject>> cursor = ic.query(scanQuery) ;
            try {
                Iterator<Cache.Entry<String,BinaryObject>> it = cursor.iterator() ;
                while (it.hasNext()){
                    Cache.Entry<String,BinaryObject> entry = it.next() ;
                    BinaryObject binaryObject = entry.getValue() ;
                    ComplexList complexList = binaryObject.deserialize() ;
                    System.out.println(complexList);
                }
            } finally {
                cursor.close();
            }
            System.out.println("\r\n\r\n");
        }

    }


    @After
    public void after(){
        if (ignite!=null){
            ignite.close();
        }
    }
}
