package com.xz.ignite.basefunction.computer;

import com.xz.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCompute;

import java.util.List;

/**
 * Created by xz on 2020/2/18.
 */
public class GetNodeId {
    public static void main(String[] args) {
        Ignite ignite = IgniteUtil.getIgnite() ;
        ignite.cluster().forServers().nodes().forEach(clusterNode -> {
            String host = "" ;
            if (clusterNode.hostNames().size()>0){
                List<String> list = (List<String>)clusterNode.hostNames();
                host = list.get(0) ;
            }
            String nodeId = clusterNode.id().toString() ;
            String consistentId = clusterNode.consistentId().toString() ;
            StringBuilder sb = new StringBuilder() ;
            sb.append("host:").append(host).append(";nodeId:").append(nodeId)
                    .append(";consistentId:").append(consistentId).append("\r\n") ;
            System.out.println(sb.toString());
        });
        IgniteUtil.release(ignite);
    }
}
