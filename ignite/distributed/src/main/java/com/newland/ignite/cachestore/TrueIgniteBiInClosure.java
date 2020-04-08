package com.newland.ignite.cachestore;

import com.newland.ignite.cachestore.entity.Expiry;
import org.apache.ignite.lang.IgniteBiInClosure;
import org.apache.ignite.lang.IgniteBiPredicate;

/**
 * Created by xz on 2020/4/3.
 */
public class TrueIgniteBiInClosure implements IgniteBiPredicate<String,Expiry> {
    @Override
    public boolean apply(String s, Expiry o) {
        return true;
    }
}
