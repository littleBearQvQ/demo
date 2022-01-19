package com.example.demo.redis;

import redis.clients.jedis.Jedis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Redis秒杀功能的实现，1000人抢购100部手机
 * @author pan_junbiao
 **/
public class SecondKillTest
{
    public static void main(String[] args)
    {
        final String key = "iphone";
        //20个线程池并发数
        ExecutorService executor = Executors.newFixedThreadPool(20);

        final Jedis jedis = new Jedis("127.0.0.1",6379);
        jedis.auth("666666");
        jedis.del(key); //先删除
        jedis.set(key,"100"); //设置起始的抢购数
        jedis.close();

        for(int i=0; i<1000; i++)
        {
            String userInfo = "pan_junbiao的博客" + i;
            executor.execute(new SecondKill(userInfo));
        }
        executor.shutdown();
    }
}
