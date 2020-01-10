package com.xz.ignite.affinity;

import com.xz.ignite.utils.CacheConfigurationUtil;
import com.xz.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.affinity.Affinity;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2019/12/25.
 */
public class AffinityTest {

    private Ignite ignite ;
    @Before
    public void before(){
        ignite = IgniteUtil.getIgnite() ;
    }

    @Test
    public void initDate(){
        CacheConfiguration<String,Company> companyCacheConfiguration = CacheConfigurationUtil.getPersistenceConfig(String.class,Company.class) ;

        CacheConfiguration<PersonKey,Person> personCacheConfiguration = CacheConfigurationUtil.getPersistenceConfig(PersonKey.class,Person.class) ;
        CacheConfiguration<String,PersonNo> personNoCacheConfiguration = CacheConfigurationUtil.getPersistenceConfig(String.class,PersonNo.class) ;
        ignite.destroyCache(companyCacheConfiguration.getName());
        ignite.destroyCache(personCacheConfiguration.getName());
        ignite.destroyCache(personNoCacheConfiguration.getName());

        IgniteCache<String,Company> companyIgniteCache = ignite.getOrCreateCache(companyCacheConfiguration) ;
        IgniteCache<PersonKey,Person> personIgniteCache = ignite.getOrCreateCache(personCacheConfiguration) ;
        IgniteCache<String,PersonNo> personNoIgniteCache = ignite.getOrCreateCache(personNoCacheConfiguration) ;

        for (int i = 0; i < 10; i++) {
            Company company = new Company("companyId"+i,"companyName"+i ) ;
            Map<PersonKey,Person> map = new HashMap<>() ;
            Map<String,PersonNo> mapno = new HashMap<>() ;
            for (int j = 0; j < 10; j++) {
                PersonKey personKey = new PersonKey("personId"+i+j,company.getCompanyId()) ;
                Person person = new Person("personId"+i+j,company.getCompanyId(),"personName"+i+j) ;
                PersonNo personNo = new PersonNo("personId"+i+j,company.getCompanyId(),"personName"+i+j) ;
                map.put(personKey,person) ;
                mapno.put(personNo.getPersonId(),personNo) ;
            }
            companyIgniteCache.put(company.getCompanyId(),company);
            personIgniteCache.putAll(map);
            personNoIgniteCache.putAll(mapno);
        }

    }

    @Test
    public void valdata(){
        CacheConfiguration<String,Company> companyCacheConfiguration = CacheConfigurationUtil.getPersistenceConfig(String.class,Company.class) ;
        CacheConfiguration<PersonKey,Person> personCacheConfiguration = CacheConfigurationUtil.getPersistenceConfig(PersonKey.class,Person.class) ;
        CacheConfiguration<String,PersonNo> personNoCacheConfiguration = CacheConfigurationUtil.getPersistenceConfig(String.class,PersonNo.class) ;
        Affinity<PersonKey> affinityPerson = ignite.affinity(personCacheConfiguration.getName()) ;
        Affinity<String> affinityPersonNo = ignite.affinity(personNoCacheConfiguration.getName());
        Affinity<String> affinityCompany = ignite.affinity(companyCacheConfiguration.getName());



        for (int i = 0; i < 10; i++) {
            Company company = new Company("companyId"+i,"companyName"+i ) ;
            for (int j = 0; j < 10; j++) {
                PersonKey personKey = new PersonKey("personId"+i+j,company.getCompanyId()) ;
                ClusterNode node = affinityPerson.mapKeyToNode(personKey) ;
                ClusterNode nodeno = affinityPersonNo.mapKeyToNode(personKey.getPersonId()) ;
                System.out.println(node.id()+"--"+nodeno.id());
            }
            ClusterNode node = affinityCompany.mapKeyToNode(company.getCompanyId()) ;
            System.out.println("-----"+node.id());
        }


    }


    @After
    public void after(){
        IgniteUtil.release(ignite);
    }
}
