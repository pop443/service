package com.newland.ignite.label.prepare;

import com.newland.ignite.label.entity.BossIndex;
import com.newland.ignite.label.utils.BeanUtil;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by xz on 2020/4/16.
 */
public class BossIndexUpload extends BaseUpload<String, BossIndex> {
    public BossIndexUpload() {
        super(600000L);
    }
    public BossIndexUpload(Long allSize) {
        super(allSize);
    }

    @Override
    protected BossIndex getBean() {
        BossIndex bossIndex = new BossIndex();
        bossIndex.setUser_id1(BeanUtil.user_id());
        bossIndex.setUser_id2(bossIndex.getUser_id1());
        bossIndex.setUser_name1(bossIndex.getUser_id1() + "");
        bossIndex.setUser_name2(bossIndex.getUser_id1() + "");
        bossIndex.setJoint1(BeanUtil.msisdn(10) + "");
        bossIndex.setJoint2(BeanUtil.msisdn() + "");
        return bossIndex;
    }


    @Override
    protected String getKey(BossIndex bossIndex) {
        return bossIndex.getUser_id1() + "--"+bossIndex.getJoint2();
    }
}