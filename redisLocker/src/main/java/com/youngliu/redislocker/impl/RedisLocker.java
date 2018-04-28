package com.youngliu.redislocker.impl;

import com.youngliu.redislocker.exception.UnableToAquireLockException;
import com.youngliu.redislocker.inte.AquiredLockWorker;
import com.youngliu.redislocker.inte.DistributedLocker;
import com.youngliu.redislocker.util.RedissonConnector;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisLocker implements DistributedLocker {
    private final static String LOCKER_PREFIX = "lock:";
    @Autowired
    RedissonConnector redissonConnector;

    public <T> T lock(String resourceName, AquiredLockWorker<T> worker) throws UnableToAquireLockException, Exception {
        return lock(resourceName,worker,100);
    }

    public <T> T lock(String resourceName, AquiredLockWorker<T> worker, int lockTime) throws UnableToAquireLockException, Exception {
        RedissonClient redisson= redissonConnector.getClient();
        RLock lock = redisson.getLock(LOCKER_PREFIX + resourceName);
        boolean success = lock.tryLock(100, lockTime, TimeUnit.SECONDS);
        if(success){
            try{
                return worker.invokeAfterLockAquire();
            }catch (Exception e){
                e.printStackTrace();
            }finally{
                lock.unlock();
            }
        }
        throw new UnableToAquireLockException();
    }
}

