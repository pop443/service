package com.newland.ignite.affinity;

import com.newland.ignite.utils.CustCacheConfiguration;
import com.newland.ignite.utils.IgniteUtil;
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
    private PersonConfiguration personCfg ;
    private CompanyConfiguration companyCfg ;
    private IgniteCache<PersonKey,Person> personIgniteCache ;
    private IgniteCache<String,Company> companyIgniteCache ;

    @Before
    public void before(){
        ignite = IgniteUtil.getIgnite() ;
        personCfg = new PersonConfiguration() ;
        personIgniteCache = personCfg.getIgniteCache(ignite) ;
        companyCfg = new CompanyConfiguration() ;
        companyIgniteCache = companyCfg.getIgniteCache(ignite) ;
    }

    @Test
    public void initDate(){
        companyIgniteCache.removeAll();
        personIgniteCache.removeAll();
        for (int i = 0; i < 10; i++) {
            Company company = new Company("companyId"+i,"companyName"+i ) ;
            Map<PersonKey,Person> map = new HashMap<>() ;
            for (int j = 0; j < 10; j++) {
                PersonKey personKey = new PersonKey("personId"+i+j,company.getCompanyId()) ;
                Person person = new Person("personId"+i+j,company.getCompanyId(),"personName"+i+j) ;
                map.put(personKey,person) ;
            }
            companyIgniteCache.put(company.getCompanyId(),company);
            personIgniteCache.putAll(map);
        }

    }

    @Test
    public void valdata(){
        Affinity<PersonKey> affinityPerson = ignite.affinity(personCfg.getCacheName()) ;
        Affinity<String> affinityCompany = ignite.affinity(companyCfg.getCacheName());



        for (int i = 0; i < 10; i++) {
            Company company = new Company("companyId"+i,"companyName"+i ) ;
            for (int j = 0; j < 10; j++) {
                PersonKey personKey = new PersonKey("personId"+i+j,company.getCompanyId()) ;
                ClusterNode nodePersonKey = affinityPerson.mapKeyToNode(personKey) ;
                System.out.println(nodePersonKey.id());
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
