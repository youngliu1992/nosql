package com.youngliu.redislocker.client;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-mvc.xml"})
public class RedLockClientTest {
    @Autowired
    RedLockClient redLockClient;

    @Test
    public void testRedlock() throws Exception {
        redLockClient.redlock();
    }

}
