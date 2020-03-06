package com.newland.ignite.spring.support;

import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.CachePeekMode;
import org.apache.ignite.springdata20.repository.IgniteRepository;

import javax.cache.Cache;
import java.io.Serializable;
import java.util.*;

/**
 * Created by xz on 2020/2/28.
 */
public class CustIgniteRepositoryImpl<T, ID extends Serializable> implements IgniteRepository<T, ID> {
    private final IgniteCache<ID, T> cache;

    public CustIgniteRepositoryImpl(IgniteCache<ID, T> cache) {
        this.cache = cache;
    }

    public <S extends T> S save(ID key, S entity) {
        this.cache.put(key, entity);
        return entity;
    }

    public <S extends T> Iterable<S> save(Map<ID, S> entities) {
        this.cache.putAll(entities);
        return entities.values();
    }

    public <S extends T> S save(S entity) {
        throw new UnsupportedOperationException("Use IgniteRepository.save(key,value) method instead.");
    }

    public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
        throw new UnsupportedOperationException("Use IgniteRepository.save(Map<keys,value>) method instead.");
    }

    public Optional<T> findById(ID id) {
        return Optional.ofNullable(this.cache.get(id));
    }

    public boolean existsById(ID id) {
        return this.cache.containsKey(id);
    }

    public Iterable<T> findAll() {
        final Iterator<Cache.Entry<ID, T>> iter = this.cache.iterator();
        return new Iterable<T>() {
            public Iterator<T> iterator() {
                return new Iterator<T>() {
                    public boolean hasNext() {
                        return iter.hasNext();
                    }

                    public T next() {
                        return (iter.next()).getValue();
                    }

                    public void remove() {
                        iter.remove();
                    }
                };
            }
        };
    }

    public Iterable<T> findAllById(Iterable<ID> ids) {
        if(ids instanceof Set) {
            return this.cache.getAll((Set)ids).values();
        } else if(ids instanceof Collection) {
            return this.cache.getAll(new HashSet((Collection)ids)).values();
        } else {
            TreeSet<ID> keys = new TreeSet();
            Iterator var3 = ids.iterator();

            while(var3.hasNext()) {
                ID id = (ID)var3.next();
                keys.add(id);
            }

            return this.cache.getAll(keys).values();
        }
    }

    public long count() {
        return (long)this.cache.size(new CachePeekMode[]{CachePeekMode.PRIMARY});
    }

    public void deleteById(ID id) {
        this.cache.remove(id);
    }

    public void delete(T entity) {
        throw new UnsupportedOperationException("Use IgniteRepository.deleteById(key) method instead.");
    }

    public void deleteAll(Iterable<? extends T> entities) {
        throw new UnsupportedOperationException("Use IgniteRepository.deleteAllById(keys) method instead.");
    }

    public void deleteAllById(Iterable<ID> ids) {
        if(ids instanceof Set) {
            this.cache.removeAll((Set)ids);
        }

        if(ids instanceof Collection) {
            this.cache.removeAll(new HashSet((Collection)ids));
        }

        TreeSet<ID> keys = new TreeSet();
        Iterator var3 = ids.iterator();

        while(var3.hasNext()) {
            ID id = (ID)var3.next();
            keys.add(id);
        }

        this.cache.removeAll(keys);
    }

    public void deleteAll() {
        this.cache.clear();
    }
}
