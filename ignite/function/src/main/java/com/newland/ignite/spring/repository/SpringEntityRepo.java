package com.newland.ignite.spring.repository;

import com.newland.ignite.spring.entity.cust.SpringEntity;
import org.apache.ignite.springdata.repository.IgniteRepository;
import org.apache.ignite.springdata.repository.config.Query;
import org.apache.ignite.springdata.repository.config.RepositoryConfig;
import org.springframework.data.domain.Pageable;

import javax.cache.Cache.Entry;
import java.util.List;

/**
 * Created by xz on 2020/2/27.
 */
@RepositoryConfig(cacheName = "SPRINGENTITY")
public interface SpringEntityRepo extends IgniteRepository<SpringEntity,Integer> {

    /**
     * 年龄相同的
     * @param age
     * @return
     */
    List<SpringEntity> findByAge(Integer age);

    /**
     * top one
     * @param name
     * @return
     */
    Entry<Integer, SpringEntity> findTopByNameLike(String name);

    @Query("SELECT id FROM SpringEntity WHERE id > ?")
    List<Integer> selectId(Integer id, Pageable pageable);
}
