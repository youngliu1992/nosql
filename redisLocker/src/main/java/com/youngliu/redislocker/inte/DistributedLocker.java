package com.youngliu.redislocker.inte;

import com.youngliu.redislocker.exception.UnableToAquireLockException;

public interface DistributedLocker {
    <T> T lock(String resourceName, AquiredLockWorker<T> worker) throws UnableToAquireLockException, Exception;

    <T> T lock(String resourceName, AquiredLockWorker<T> worker, int lockTime) throws UnableToAquireLockException, Exception;
}
