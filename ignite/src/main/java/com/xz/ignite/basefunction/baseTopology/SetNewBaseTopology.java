package com.xz.ignite.basefunction.baseTopology;

import com.xz.ignite.utils.IgniteUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCluster;

/**
 * 1.增加节点后 才可以更新基线
 * 2.停止节点后 才可以更新基线
 * 3.依据副本数量 选择基线的减少数量 会丢失数据
 */
public class SetNewBaseTopology {
    public static void main(String[] args) {
        //更新基线拓扑的时候  必须提供包含persistenceEnabled=true的持久化文件
        //否则不会执行更新基线拓扑
        Ignite ignite = IgniteUtil.getIgniteByXml();
        IgniteCluster igniteCluster = ignite.cluster() ;
        //定义版本 更新拓扑
        //Long topologyVersion = igniteCluster.topologyVersion();
        //igniteCluster.setBaselineTopology(topologyVersion);

        //定义节点 更新拓扑
        igniteCluster.setBaselineTopology(igniteCluster.forServers().nodes());

        IgniteUtil.release(ignite);
    }

}
