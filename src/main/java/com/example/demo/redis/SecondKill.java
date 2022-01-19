package com.example.demo.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import java.util.List;

/**
 * 秒杀抢购
 * @author pan_junbiao
 **/
public class SecondKill implements Runnable
{
    String key = "iphone";
    Jedis jedis = new Jedis("127.0.0.1",6379);
    String userInfo;

    public SecondKill(String userInfo)
    {
        this.userInfo = userInfo;
    }

    @Override
    public void run()
    {
        try
        {
            jedis.auth("666666");
            jedis.watch(key); //watchkeys
            String val = jedis.get(key);
            int valint = Integer.valueOf(val);

            if(valint<=100 && valint>=1)
            {
                //1、使用MULTI命令开启事务
                Transaction tx = jedis.multi();

                //2、事务命令入队
                tx.incrBy(key,-1);

                //3、使用EXEC命令执行事务
                //提交事务。如果此时watchkeys被改动了，则返回null
                List<Object> list = tx.exec();

                if(list==null || list.size()==0)
                {
                    String failuserinfo = "fail_" + userInfo;
                    String failinfo = "用户：" + failuserinfo + "商品争抢失败，抢购失败";
                    System.out.println(failinfo);
                    //抢购失败业务逻辑
                    jedis.setnx(failuserinfo,failinfo);
                }
                else
                {
                    for(Object succ : list)
                    {
                        String succuserinfo = "succ_" + succ.toString() + "_" + userInfo;
                        String succinfo = "用户：" + succuserinfo + " 抢购成功，当前抢购成功人数：" + (1 -(valint -100));
                        System.out.println(succinfo);
                        //抢购成功业务逻辑
                        jedis.setnx(succuserinfo,succinfo);
                    }
                }
            }
            else
            {
                String failuserinfo = "kcfail_" + userInfo;
                String failinfo1 = "用户：" + failuserinfo + " 商品被抢购完毕，抢购失败";
                System.out.println(failinfo1);
                jedis.setnx(failuserinfo,failinfo1);
                return;
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            jedis.close();
        }
    }
}
