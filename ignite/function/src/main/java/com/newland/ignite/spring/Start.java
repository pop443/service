package com.newland.ignite.spring;

import com.newland.ignite.spring.entity.cust.SpringEntity;
import com.newland.ignite.spring.repository.SpringEntityRepo;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.PageRequest;

import javax.cache.Cache;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

/**
 * Created by xz on 2020/2/27.
 */
public class Start {
    /**
     * Spring Application Context.
     */
    private static AnnotationConfigApplicationContext ctx;

    /**
     * Ignite Spring Data repository.
     */
    private static SpringEntityRepo springEntityRepo;

    public static void main(String[] args) {
        // Initializing Spring Data context and Ignite repository.
        igniteSpringDataInit();

        springEntityRepo.deleteAll();
        insert();
        System.out.println("Repository size: " + springEntityRepo.count());
        baseApi();
        custQuery();
        springEntityRepo.deleteAll();


        // Destroying the context.
        ctx.destroy();
        ctx.close();
        System.out.println("spring close");
    }

    private static void igniteSpringDataInit() {
        ctx = new AnnotationConfigApplicationContext();
        // Explicitly registering Spring configuration.
        ctx.register(SpringAppCfg.class);
        //ctx.register(Spring2EntityCacheConfig.class);
        ctx.refresh();
        springEntityRepo = ctx.getBean(SpringEntityRepo.class);
    }
    private static void insert() {
        SpringEntity spring1Entity = new SpringEntity(1,"name1",1) ;
        springEntityRepo.save(spring1Entity.getId(),spring1Entity) ;
        TreeMap<Integer, SpringEntity> spring1Entitys = new TreeMap<>();
        for (int i = 10; i < 20; i++) {
            spring1Entitys.put(i, new SpringEntity(i, "name" + i, 20));
        }
        springEntityRepo.save(spring1Entitys);
        System.out.println("putAll:" + springEntityRepo.count() );
    }
    private static void baseApi() {
        SpringEntity springEntity = springEntityRepo.findOne(12);
        System.out.println("findOne:" + springEntity);
        List<Integer> ids = new ArrayList<>();
        for (int i = 10; i < 15; i++){
            ids.add(i);
        }
        System.out.println("findAll start:");
        springEntityRepo.findAll(ids).forEach(System.out::println);
        System.out.println("findAll end:");
    }

    private static void custQuery() {

        List<SpringEntity> spring1EntityList = springEntityRepo.findByAge(20);
        System.out.println("findByAge start:");
        spring1EntityList.forEach(System.out::println);
        System.out.println("findByAge end:");

        Cache.Entry<Integer, SpringEntity> topPerson = springEntityRepo.findTopByNameLike("name");

        System.out.println("findTopByNameLike: " + topPerson.getValue());

        List<Integer> ids = springEntityRepo.selectId(13, new PageRequest(0,4));

        System.out.println("selectId start:");
        ids.forEach(System.out::println);
        System.out.println("selectId end:");
    }

}
