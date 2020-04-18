package com.newland.ignite.label.prepare;

import com.newland.ignite.label.entity.BossAffinity;
import com.newland.ignite.label.entity.BossAffinityKey;
import com.newland.ignite.label.entity.BossIndex;
import com.newland.ignite.label.utils.BeanUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xz on 2020/4/16.
 */
public class BossAffinityUpload extends BaseUpload<BossAffinityKey,BossAffinity> {
    public BossAffinityUpload() {
        super(600000L);
    }
    public BossAffinityUpload(Long allSize) {
        super(allSize);
    }

    @Override
    protected BossAffinity getBean() {
        BossAffinity bossAffinity = new BossAffinity();
        bossAffinity.setUser_id1(BeanUtil.user_id());
        bossAffinity.setUser_id2(bossAffinity.getUser_id1());
        bossAffinity.setUser_name1(bossAffinity.getUser_id1() + "");
        bossAffinity.setUser_name2(bossAffinity.getUser_id1() + "");
        bossAffinity.setJoint1(BeanUtil.msisdn(10) + "");
        bossAffinity.setJoint2(BeanUtil.msisdn() + "");
        List<String> list1 = new ArrayList<>() ;
        list1.add("1") ;
        list1.add("2") ;
        bossAffinity.setList1(list1);

        List<BossIndex> list2 = new ArrayList<>() ;
        BossIndex bossIndex = new BossIndex() ;
        bossIndex.setUser_id1(1111111L);
        list2.add(bossIndex) ;
        bossAffinity.setList2(list2);

        Map<String,String> map1 = new HashMap<>() ;
        map1.put("1","1") ;
        bossAffinity.setMap1(map1);

        Map<String,BossIndex> map2 = new HashMap<>() ;
        map2.put("1",bossIndex) ;
        bossAffinity.setMap2(map2);
        return bossAffinity;
    }


    @Override
    protected BossAffinityKey getKey(BossAffinity bossIndex) {
        BossAffinityKey bossAffinityKey = new BossAffinityKey() ;
        bossAffinityKey.setId(bossIndex.getUser_name1());
        bossAffinityKey.setName(bossIndex.getJoint1());
        return bossAffinityKey;
    }
}