package com.xz.ignite.basefunction.spring.repository;

import com.xz.ignite.basefunction.spring.entity.cust.Spring1Entity;
import org.apache.ignite.springdata.repository.IgniteRepository;
import org.apache.ignite.springdata.repository.config.Query;
import org.apache.ignite.springdata.repository.config.RepositoryConfig;
import org.springframework.data.domain.Pageable;

import javax.cache.Cache.Entry;
import java.util.List;

/**
 * Created by xz on 2020/2/27.
 */
@RepositoryConfig(cacheName = "Spring1Entity")
public interface Spring1EntityRepo extends IgniteRepository<Spring1Entity,Integer>{

    /**
     * 年龄相同的
     * @param age
     * @return
     */
    List<Spring1Entity> findByAge(Integer age);

    /**
     * top one
     * @param name
     * @return
     */
    Entry<Integer, Spring1Entity> findTopByNameLike(String name);

    @Query("SELECT id FROM Spring1Entity WHERE id > ?")
    List<Integer> selectId(Integer id, Pageable pageable);
}
