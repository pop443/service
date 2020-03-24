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
    public E build2k(String id){
        try {
            return constructor.newInstance(id,DiffString.diffstr(26));
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null ;
    }

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