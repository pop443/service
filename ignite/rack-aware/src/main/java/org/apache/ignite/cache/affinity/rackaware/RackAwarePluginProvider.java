package org.apache.ignite.cache.affinity.rackaware;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCheckedException;
import org.apache.ignite.IgniteLogger;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.internal.GridKernalContext;
import org.apache.ignite.internal.GridPluginContext;
import org.apache.ignite.internal.IgniteNodeAttributes;
import org.apache.ignite.internal.util.typedef.F;
import org.apache.ignite.internal.util.typedef.internal.U;
import org.apache.ignite.plugin.*;
import org.jetbrains.annotations.Nullable;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

/**
 * 插件自动加载
 * Created by xz on 2020/7/28.
 */
public class RackAwarePluginProvider implements PluginProvider {
    /** Logger. */
    private IgniteLogger log;
    private String jvmRackFilePath = "rackAwareFile" ;
    @Override
    public String name() {
        return "Ignite RackAware Plugin [-D"+jvmRackFilePath+"]";
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
        return new RackAwarePlugin();
    }

    @Override
    public void initExtensions(PluginContext ctx, ExtensionRegistry registry) throws IgniteCheckedException {

    }

    @Nullable
    @Override
    public Object createComponent(PluginContext ctx, Class cls) {
        return null;
    }

    @Override
    public CachePluginProvider createCacheProvider(CachePluginContext ctx) {
        return null;
    }

    @Override
    public void start(PluginContext ctx) throws IgniteCheckedException {
        final Ignite ignite = ctx.grid();
        log = ignite.log();
        if (ctx.igniteConfiguration().isClientMode()){
            log.info(name()+" client is no need !");
            return;
        }
        try {
            GridPluginContext gridPluginContext = (GridPluginContext)ctx ;
            Class<GridPluginContext> cz = GridPluginContext.class ;
            Field field = cz.getDeclaredField("ctx") ;
            field.setAccessible(true);
            GridKernalContext gridKernalContext = (GridKernalContext)field.get(gridPluginContext) ;

            String path = System.getProperty(jvmRackFilePath) ;
            setAttrbuite(path,gridKernalContext);
            log.info(name()+"start ok path:"+path+"!");
        } catch (Exception e) {
            log.error(name()+"start error!");
        }
    }

    private void setAttrbuite(String path,GridKernalContext gridKernalContext) {
        String localIp = getIp() ;
        String rack = getRack(path,localIp) ;
        if (rack==null){
            rack = RackAwareAdapt.DEFAULT_RACK ;
        }
        gridKernalContext.addNodeAttribute(RackAwareAdapt.ATTR_RACK,rack) ;
    }

    private String getIp(){
        String ips = F.concat(U.allLocalIps(), ", ");
        return ips.isEmpty() ? "" : ips ;
    }
    /**
     * 根据机架文件确认属于哪个机架
     *
     * @param path
     * @param localIp
     * @return
     */
    private String getRack(String path, String localIp) {
        Properties properties = new Properties();
        InputStream is = null;
        try {
            is = new FileInputStream(path);
            properties.load(is);
            Iterator<Map.Entry<Object, Object>> it = properties.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Object, Object> entry = it.next();
                String ip = (String) entry.getKey();
                String rack = (String) entry.getValue();
                if (localIp.contains(ip)) {
                    return rack;
                }
            }
        } catch (Exception e) {
            log.error("-- rack aware error:" + e.getMessage());
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    @Override
    public void stop(boolean cancel) throws IgniteCheckedException {

    }

    @Override
    public void onIgniteStart() throws IgniteCheckedException {

    }

    @Override
    public void onIgniteStop(boolean cancel) {

    }

    @Nullable
    @Override
    public Serializable provideDiscoveryData(UUID nodeId) {
        return null;
    }

    @Override
    public void receiveDiscoveryData(UUID nodeId, Serializable data) {

    }

    @Override
    public void validateNewNode(ClusterNode node) throws PluginValidationException {

    }
}
