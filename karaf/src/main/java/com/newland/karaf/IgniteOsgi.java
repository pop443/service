package com.newland.karaf;

import com.newland.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.osgi.classloaders.BundleDelegatingClassLoader;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * Created by xz on 2020/3/28.
 */
public class IgniteOsgi implements BundleActivator {
    protected Ignite ignite;

    /** Our bundle context. */
    private BundleContext bundleCtx;
    @Override
    public void start(BundleContext bundleContext) throws Exception {
        System.out.println("---加载");
        bundleCtx = bundleContext;
        IgniteConfiguration cfg = IgniteUtil.getIgniteConfiguration()  ;
        ClassLoader clsLdrnew = null;
        try {
            clsLdrnew = new BundleDelegatingClassLoader(bundleCtx.getBundle(), Ignite.class.getClassLoader());
        } catch (Exception e) {
            e.printStackTrace();
        }
        cfg.setClassLoader(clsLdrnew);
        ignite = Ignition.start(cfg);
        System.out.println("----------------"+ignite.cluster().nodes().size());
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        System.out.println("---停止");
        ignite.close();
    }
}
