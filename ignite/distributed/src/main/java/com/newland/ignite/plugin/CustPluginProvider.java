package com.newland.ignite.plugin;

import org.apache.ignite.IgniteCheckedException;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.plugin.*;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by xz on 2020/8/27.
 */
public class CustPluginProvider implements PluginProvider {
    @Override
    public String name() {
        return "Ignite Cust Plugin";
    }

    @Override
    public String version() {
        return "";
    }

    @Override
    public String copyright() {
        return "Copyright(C) newland";
    }

    @Override
    public IgnitePlugin plugin() {
        return new CustPlugin();
    }

    @Override
    public void initExtensions(PluginContext ctx, ExtensionRegistry registry) throws IgniteCheckedException {
        System.out.println("initExtensions");
    }

    @Nullable
    @Override
    public Object createComponent(PluginContext ctx, Class cls) {
        System.out.println("createComponent");
        return null;
    }

    @Override
    public CachePluginProvider createCacheProvider(CachePluginContext ctx) {
        System.out.println("createCacheProvider");
        return null;
    }

    @Override
    public void start(PluginContext ctx) throws IgniteCheckedException {
        System.out.println("start");
    }

    @Override
    public void stop(boolean cancel) throws IgniteCheckedException {
        System.out.println("stop");
    }

    @Override
    public void onIgniteStart() throws IgniteCheckedException {
        System.out.println("onIgniteStart");
    }

    @Override
    public void onIgniteStop(boolean cancel) {
        System.out.println("onIgniteStop");
    }

    @Nullable
    @Override
    public Serializable provideDiscoveryData(UUID nodeId) {
        System.out.println("provideDiscoveryData");
        return null;
    }

    @Override
    public void receiveDiscoveryData(UUID nodeId, Serializable data) {
        System.out.println("receiveDiscoveryData");
    }

    @Override
    public void validateNewNode(ClusterNode node) throws PluginValidationException {
        System.out.println("validateNewNode");
    }
}
