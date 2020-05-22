package com.newland.boss.script.features.deploy;

import com.newland.boss.entity.expired.Expired;
import com.newland.boss.entity.expired.ExpiredConfiguration;
import com.newland.boss.script.features.BaseScript;
import com.newland.boss.script.features.TestExpired;
import com.newland.boss.script.features.event.ep.Ep1;
import com.newland.ignite.cachestore.entity.Automation;
import com.newland.ignite.cachestore.entity.Expiry;
import com.newland.ignite.cachestore.entity.ExpiryConfiguration;
import org.apache.ignite.cache.CacheEntryProcessor;

/**
 * Created by xz on 2020/5/19.
 */
public class DeployQuestion extends BaseScript<String, Expiry> {

    public DeployQuestion() {
        super(new ExpiryConfiguration());
    }

    @Override
    public void work() {

        String name = "sss";
        Automation automation = new Automation("1", 1, "1");
        Expiry expiry = igniteCache.invoke("1", new Ep1(name, automation));

        System.out.println(expiry);

    }

    public static void main(String[] args) {
        DeployQuestion scirpt = new DeployQuestion();
        scirpt.start();
    }
}
