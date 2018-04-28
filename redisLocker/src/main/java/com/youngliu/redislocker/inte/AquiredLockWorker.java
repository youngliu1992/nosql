package com.youngliu.redislocker.inte;

public interface AquiredLockWorker<T> {
    T invokeAfterLockAquire() throws Exception;
}
