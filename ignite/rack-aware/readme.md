# Ignite 机架感知

#### 实现方式

+ ignite启动时加载机架感知插件  
+ 机架感知文件为 properties 格式
+ 通过环境变量 -DrackAwareFile=绝对路径 加载
+ 在节点属性中默认增加一个机架属性
+ SELECT NODE_ID, NAME, VALUE FROM SYS.NODE_ATTRIBUTES WHERE NAME='org.apache.ignite.rack'

#### 使用方式
+ 缓存设置机架 affinity RackawareAffinityFunction
+ 两个参数 分区数 是否简单机架
    + 简单机架：所有数据分布 机架都不同
    + 非简单机架：老机架感知逻辑
        + 双份：不同机架
        + 单份：上个数据同机架不同ip
        
#### 结果
+ 无机架感知文件走主机感知
+ 有机架感知文件
    + 不符合properties 格式 走主机感知
    + 符合properties 格式
        + 全ip覆盖 
        + 部分ip覆盖 未覆盖节点提供默认的机架
        + 无ip覆盖 未覆盖节点提供默认的机架
    + 机架数只有一个 走主机感知
    + 机架数>1 走机架感知
        