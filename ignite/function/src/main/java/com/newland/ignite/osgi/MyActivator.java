package com.newland.ignite.osgi;

import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.osgi.IgniteAbstractOsgiContextActivator;
import org.apache.ignite.osgi.classloaders.OsgiClassLoadingStrategyType;

/**
 * Created by xz on 2020/4/15.
 */
public class MyActivator extends IgniteAbstractOsgiContextActivator {
    @Override
    public IgniteConfiguration igniteConfiguration() {
        IgniteConfiguration config = IgniteUtil.getIgniteConfiguration();
        config.setGridName("testGrid");
        return config;
    }

    @Override
    public OsgiClassLoadingStrategyType classLoadingStrategy() {
        return OsgiClassLoadingStrategyType.BUNDLE_DELEGATING;
    }
}
