package com.youngliu.client;

import com.alibaba.fastjson.JSON;
import com.youngliu.entity.Car;
import redis.clients.jedis.Jedis;

import java.util.*;

public class RedisConditionsQueryClient {
    static final String TABLE_NAME_CAR = "TABLE_NAME_CAR";
    static final String CAR_CORLOR_RED = "CAR_CORLOR_RED";
    static final String CAR_CORLOR_BLUE = "CAR_CORLOR_BLUE";
    static final String CAR_CORLOR_YELLOW = "CAR_CORLOR_YELLOW";
    static final String CAR_PRICE_1p9 = "CAR_PRICE_1p9";
    static final String CAR_PRICE_2p0 = "CAR_PRICE_2p0";

    public static void main(String[] args){
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        new RedisConditionsQueryClient().mockProductCars(jedis);
        //查询1.9的红色车
        //new RedisConditionsQueryClient().mockAnd(jedis,CAR_CORLOR_RED,CAR_PRICE_1p9);
        //查询1.9车
        //new RedisConditionsQueryClient().mockAnd(jedis,CAR_PRICE_1p9);
        //查询1.9或者黄色车
        new RedisConditionsQueryClient().mockOr(jedis,CAR_CORLOR_YELLOW,CAR_PRICE_1p9);

    }

    public void mockAnd(Jedis jedis,String... conditions){
        Set<String> carIds = jedis.sinter(conditions);
        for(String carId : carIds) {
            String car = jedis.hget(TABLE_NAME_CAR, carId);
            System.out.println("--------->【结果】:"+car);
        }
    }

    public void mockOr(Jedis jedis,String... conditions){
        Set<String> carIds = jedis.sunion(conditions);
        for(String carId : carIds) {
            String car = jedis.hget(TABLE_NAME_CAR, carId);
            System.out.println("--------->【结果】:"+car);
        }
    }

    public Map<String, String> mockProductCars (Jedis jedis){
        Map<String,String> map = new HashMap<String, String>();
        //批量生产红色车 价格 1.9
        for(int i = 0;i < 10;i++){
            Car car = new Car();
            car.setId(i+"");
            car.setColor("RED");
            car.setPrice("1.9");
            jedis.sadd(CAR_CORLOR_RED,i+"");
            jedis.sadd(CAR_PRICE_1p9,i+"");
            map.put(i+"", JSON.toJSONString(car));
        }
        //批量生产红色车 价格 2.0
        for(int i = 11;i < 21;i++){
            Car car = new Car();
            car.setId(i+"");
            car.setColor("RED");
            car.setPrice("2.0");
            jedis.sadd(CAR_CORLOR_RED,i+"");
            jedis.sadd(CAR_PRICE_2p0,i+"");
            map.put(i+"", JSON.toJSONString(car));
        }
        //批量生产蓝色车  价格1.9
        for(int i = 21;i < 31;i++){
            Car car = new Car();
            car.setId(i+"");
            car.setColor("BLUE");
            car.setPrice("1.9");
            jedis.sadd(CAR_CORLOR_BLUE,i+"");
            jedis.sadd(CAR_PRICE_1p9,i+"");
            map.put(i+"", JSON.toJSONString(car));
        }
        //批量生产黄色车  价格2.0
        for(int i = 31;i < 41;i++){
            Car car = new Car();
            car.setId(i+"");
            car.setColor("YELLOW");
            car.setPrice("2.0");
            jedis.sadd(CAR_CORLOR_YELLOW,i+"");
            jedis.sadd(CAR_PRICE_2p0,i+"");
            map.put(i+"", JSON.toJSONString(car));
        }

        jedis.hmset(TABLE_NAME_CAR,map);
        return map;
    }

}
