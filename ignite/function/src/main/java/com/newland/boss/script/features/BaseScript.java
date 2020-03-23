package com.newland.boss.script.features;

import com.newland.ignite.utils.CustCacheConfiguration;
import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;

import javax.cache.Cache;
import java.util.Iterator;

/**
 * 统一初始化ignite客户端
 */
public abstract class BaseScript<K,V> {
    protected Ignite ignite ;
    private CustCacheConfiguration<K,V> cfg;
    protected String cacheName ;
    protected IgniteCache<K,V> igniteCache ;
    private IgniteDataStreamer<K,V> igniteDataStreamer ;

    public BaseScript(CustCacheConfiguration<K,V> cfg) {
        if (cfg!=null){
            this.cfg = cfg;
            this.cacheName = cfg.getCacheName() ;
        }
    }

    /**
     * 普通测试用
     */
    public void start()  {
        try {
            init();
            work();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            destory();
        }
    }

    private void init() {
        ignite = IgniteUtil.getIgnite() ;
        afterInitIgnite();
        if (cfg!=null){
            igniteCache = cfg.getIgniteCache(ignite) ;
        }
    }
    protected void afterInitIgnite(){

    }

    public IgniteDataStreamer<K, V> getIgniteDataStreamer() {
        if (igniteDataStreamer==null){
            igniteDataStreamer = cfg.getDataStreamer(ignite) ;
        }
        igniteDataStreamer.allowOverwrite(true);
        return igniteDataStreamer;
    }

    protected abstract void work();

    protected void destory()  {
        if (cfg!=null){
            cfg.close();
        }
        ignite.close();
    }

    protected Iterable<V> findAll() {
        final Iterator<Cache.Entry<K, V>> iter = igniteCache.iterator();
        return new Iterable<V>() {
            public Iterator<V> iterator() {
                return new Iterator<V>() {
                    public boolean hasNext() {
                        return iter.hasNext();
                    }
                    public V next() {
                        return ((Cache.Entry<K,V>)iter.next()).getValue();
                    }
                    public void remove() {
                        iter.remove();
                    }
                };
            }
        };
    }



}
