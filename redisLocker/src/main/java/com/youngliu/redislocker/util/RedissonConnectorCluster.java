package com.youngliu.redislocker.util;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;

import javax.annotation.PostConstruct;

public class RedissonConnectorCluster {
    RedissonClient redisson;
    @PostConstruct
    public void init(){
        Config config = new Config();
        ClusterServersConfig clusterServersConfig = config.useClusterServers();
        clusterServersConfig.addNodeAddress("127.0.0.1:7001");
        clusterServersConfig.addNodeAddress("127.0.0.1:7002");
        clusterServersConfig.addNodeAddress("127.0.0.1:7003");
        clusterServersConfig.addNodeAddress("127.0.0.1:7004");
        clusterServersConfig.addNodeAddress("127.0.0.1:7005");
        clusterServersConfig.addNodeAddress("127.0.0.1:7006");
        redisson = Redisson.create(config);
    }
    public RedissonClient getClient(){
        return redisson;
    }
}
