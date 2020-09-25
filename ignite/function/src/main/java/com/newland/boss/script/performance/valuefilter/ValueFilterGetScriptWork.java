package com.newland.boss.script.performance.valuefilter;

import com.newland.boss.entity.performance.affinity.AffinityItemYes;
import com.newland.boss.entity.performance.affinity.AffinityItemYesKey;
import com.newland.boss.entity.performance.complex.ComplexValueItem2;
import com.newland.boss.entity.performance.complex.ListObj;
import com.newland.boss.script.performance.EnterParam;
import com.newland.boss.script.performance.PerformanceScriptWork;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.cache.query.FieldsQueryCursor;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.ScanQuery;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.lang.IgniteBiPredicate;

import javax.cache.Cache;
import java.util.Iterator;
import java.util.List;

/**
 * Created by xz on 2020/3/10.
 */
public class ValueFilterGetScriptWork extends PerformanceScriptWork<String,ListObj> {
    private IgniteCache<String,BinaryObject> ic ;
    public ValueFilterGetScriptWork(EnterParam enterParam, IgniteCache<String,ListObj> igniteCache, IgniteDataStreamer<String,ListObj> igniteDataStreamer, Integer baseKey) {
        super(enterParam, igniteCache, igniteDataStreamer,baseKey);
        ic = igniteCache.withKeepBinary() ;
    }

    @Override
    public long doing() {
        long l1 = System.currentTimeMillis() ;
        for (int i = 0; i < enterParam.getCount(); i++) {
            String randomKey = i+baseKey+"" ;
            ScanQuery<String,BinaryObject> scanQuery = new ScanQuery<>(new CustIgniteFilter(randomKey)) ;
            QueryCursor<Cache.Entry<String,BinaryObject>> cursor = ic.query(scanQuery) ;
            List<Cache.Entry<String,BinaryObject>> list = cursor.getAll() ;
            int size = list.size();
        }
        long l2 = System.currentTimeMillis() ;
        return l2-l1 ;
    }
    class CustIgniteFilter implements IgniteBiPredicate<String,BinaryObject>{
        private String value ;

        public CustIgniteFilter(String value) {
            this.value = value;
        }

        @Override
        public boolean apply(String s, BinaryObject binaryObject) {
            List<Object> list = binaryObject.field("list") ;
            for (Object o:list) {
                BinaryObject binary = (BinaryObject)o ;
                String itemId = binary.field("itemId") ;
                if (itemId.equals(value)){
                    return true ;
                }
            }
            return false;
        }
    }
}
