package com.newland.boss.entity.performance;

import com.newland.boss.utils.DiffString;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by xz on 2020/3/10.
 */
public class CustObjBuild<E extends CustObj> {
    private Class<E> cz ;
    private Constructor<E> constructor ;

    public CustObjBuild(Class<E> cz) {
        this.cz = cz;
        try {
            this.constructor = cz.getConstructor(String.class,String.class) ;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }


    public E build10k(String id){
        try {
            String value = DiffString.diffstr(80) ;
            return constructor.newInstance(id,value+value+value);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null ;
    }

    /**
     * lucene 4 :80
     * lucene 7 :74
     * @param id
     * @return
     */
    public E build4k(String id){
        try {
            return constructor.newInstance(id,DiffString.diffstr(80));
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null ;
    }

    /**
     * lucene 4 :4
     * lucene 7 :19
     * @param id
     * @return
     */
    public E build1k(String id){
        try {
            return constructor.newInstance(id, DiffString.diffstr(4));
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null ;
    }

}
