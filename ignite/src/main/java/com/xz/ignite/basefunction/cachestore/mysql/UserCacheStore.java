package com.xz.ignite.basefunction.cachestore.mysql;

import com.xz.ignite.basefunction.cachestore.entity.User;
import org.apache.ignite.cache.store.CacheStoreAdapter;
import org.apache.ignite.lang.IgniteBiInClosure;

import javax.cache.Cache;
import javax.cache.integration.CacheLoaderException;
import javax.cache.integration.CacheWriterException;
import java.util.Collection;
import java.util.Map;

/**
 * Created by xz on 2020/2/5.
 */
public class UserCacheStore extends CacheStoreAdapter<Integer, User> {

    @Override
    public User load(Integer integer) throws CacheLoaderException {
        return null;
    }

    @Override
    public void loadCache(IgniteBiInClosure<Integer, User> clo, Object... args) {
        super.loadCache(clo, args);
    }

    @Override
    public Map<Integer, User> loadAll(Iterable<? extends Integer> keys) {
        return super.loadAll(keys);
    }

    @Override
    public void writeAll(Collection<Cache.Entry<? extends Integer, ? extends User>> entries) {
        super.writeAll(entries);
    }

    @Override
    public void deleteAll(Collection<?> keys) {
        super.deleteAll(keys);
    }

    @Override
    public void write(Cache.Entry<? extends Integer, ? extends User> entry) throws CacheWriterException {

    }

    @Override
    public void delete(Object o) throws CacheWriterException {

    }
}
