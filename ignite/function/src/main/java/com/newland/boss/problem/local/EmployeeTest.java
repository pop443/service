package com.newland.boss.problem.local;

import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.configuration.CacheConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by xz on 2020/6/2.
 */
public class EmployeeTest {
    private Ignite ignite ;
    @Before
    public void before(){
        ignite = IgniteUtil.getIgnite() ;
    }
    @Test
    public void test(){

        final CacheConfiguration<EmployeeId, Employee> cacheConfig = new
                CacheConfiguration<>("Employee");

        cacheConfig.setIndexedTypes(EmployeeId.class, Employee.class);
//        cacheConfig.setQueryEntities(Collections.singletonList(createEmployeeQueryEntity() ));

        IgniteCache<EmployeeId, Employee> cache =
                ignite.getOrCreateCache(cacheConfig);
        IgniteCache<BinaryObject, BinaryObject> employeeCache =
                cache.withKeepBinary();

        EmployeeId employeeId = new EmployeeId() ;
        employeeId.setEmployeeNumber(65348765);
        employeeId.setDepartmentNumber(123);
        BinaryObject key1 = IgniteUtil.toBinary(employeeId) ;

        // create a new value
        Employee employee = new Employee() ;
        employee.setFirstName("John");
        employee.setLastName("Smith");
        employee.setId(employeeId);
        BinaryObject emp1 = IgniteUtil.toBinary(employee);

        // put the record to the DB - OK
        employeeCache.put(key1, emp1);


        BinaryObject key2 = emp1.field("id");

        employeeCache.put(key2.toBuilder().build(), emp1); // OK!

        employeeCache.put(key2, emp1); // CRASH!!! CorruptedTreeException: B+Tree is corrupted

        //employeeCache.put(key2.clone(), emp1); // CRASH!!!    CorruptedTreeException: B+Tree is corrupted
    }


    @After
    public void after(){
        IgniteUtil.release(ignite);
    }
}
