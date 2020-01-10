package com.xz.ignite.web.restful;

import com.xz.ignite.utils.CacheConfigurationUtil;
import com.xz.ignite.utils.IgniteUtil;
import com.xz.ignite.web.filter.EncodingFilter;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteCluster;
import org.apache.ignite.cache.query.FieldsQueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.cluster.ClusterMetrics;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.servlet.DispatcherType;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

/**
 * Created by Administrator on 2019/12/26.
 */
@Path("/")
public class BaseRestful {
    private static Ignite ignite = null ;
    private static IgniteCache<String,Map<String,String>> tempCache ;

    static{
        System.out.println("rest start");
        ignite = IgniteUtil.getIgnite() ;
        CacheConfiguration<String,Map<String,String>> cacheConfiguration = CacheConfigurationUtil.getPersistenceConfig(String.class, Map.class) ;
        tempCache = ignite.getOrCreateCache(cacheConfiguration) ;
    }

    @Path("create") // url上没有参数，参数通过body传入
    @POST
    public String create(@FormParam("sql") String sql) {
        return "hello"+sql;
    }

    @Path("insert") // url上没有参数，参数通过body传入
    @GET
    public String insert(@QueryParam("sql") String sql) {
        return "hello"+sql;
    }

    @Path("execute") // url上没有参数，参数通过body传入
    @POST
    public String execute(@FormParam("sql") String sql) {
        long start = System.currentTimeMillis() ;
        StringBuilder sb = new StringBuilder() ;
        sb.append(sql).append("\r\n") ;

        SqlFieldsQuery sqlFieldsQuery = null ;
        FieldsQueryCursor<List<?>> fieldsQueryCursor = null ;
        try {
            sqlFieldsQuery = new SqlFieldsQuery(sql) ;
            fieldsQueryCursor = tempCache.query(sqlFieldsQuery) ;
            Iterator<List<?>> it = fieldsQueryCursor.iterator() ;
            while (it.hasNext()){
                List<?> item = it.next() ;
                for (Object o:item) {
                    sb.append(o).append("\t");
                }
                sb.append("\r\n") ;
            }
        } catch (Exception e) {
            sb.append(e.getMessage()) ;
        }
        finally {
            if (fieldsQueryCursor!=null){
                fieldsQueryCursor.close();
            }
        }
        long end = System.currentTimeMillis() ;
        sb.append("cost:").append((end-start)/1000).append("秒") ;
        return sb.toString();
    }

    @Path("metrics") // url上没有参数，参数通过body传入
    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    public String metrics() {
        StringBuilder sb = new StringBuilder();
        Collection<ClusterNode> clusterNodes = ignite.cluster().nodes() ;
        for (ClusterNode clusterNode:clusterNodes) {
            if (clusterNode.isClient()){
                continue;
            }
            sb.append("\r\n---------节点：").append(clusterNode.id().toString()).append("---------\r\n") ;
            ClusterMetrics clusterMetrics = clusterNode.metrics() ;
            sb.append("当前活动线程数：").append(clusterMetrics.getCurrentActiveJobs()).append("\r\n") ;
            sb.append("当前等待线程数：").append(clusterMetrics.getCurrentWaitingJobs()).append("\r\n") ;
            sb.append("节点当前正在执行的作业的执行时间：").append(clusterMetrics.getCurrentJobExecuteTime()).append("\r\n") ;
            sb.append("节点作业的平均执行时间：").append(clusterMetrics.getAverageJobExecuteTime()).append("\r\n") ;
            sb.append("可用CPU数量：").append(clusterMetrics.getTotalCpus()).append("\r\n") ;
            sb.append("CPU使用率：").append(clusterMetrics.getCurrentCpuLoad()).append("\r\n") ;
            sb.append("所有堆内存池中使用的堆内存总数：").append(clusterMetrics.getHeapMemoryUsed()).append("\r\n") ;
            sb.append("JVM当前使用的非堆内存量：").append(clusterMetrics.getNonHeapMemoryUsed()).append("\r\n") ;
            sb.append("包括守护和非守护线程在内的所有有效线程总数：").append(clusterMetrics.getCurrentThreadCount()).append("\r\n") ;
        }
        return sb.toString();
    }

    @Path("status") // url上没有参数，参数通过body传入
    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    public String status() {
        StringBuilder sb = new StringBuilder();
        IgniteCluster igniteCluster = ignite.cluster() ;
        Collection<ClusterNode> clusterNodes = igniteCluster.nodes() ;
        for (ClusterNode clusterNode:clusterNodes) {
            sb.append(clusterNode.id().toString()).append(":").append(igniteCluster.pingNode(clusterNode.id())).append("\r\n") ;
        }
        return sb.toString();
    }



}
