package com.example.demo.redis;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author 团子等等俺
 */
@Repository
@Slf4j
public class RedisUnit {

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${spring.redis.database}")
    private Integer dbIndex;

    public void getDBIndex(){
        log.info("dbIndex:{}",this.dbIndex);
    }

    /**
     * 查询键是否存在
     *
     * @param keyName 指定键名
     */
    public Boolean hasKey(String keyName) {
        return redisTemplate.hasKey(keyName);
    }

    /**
     * 返回查询键的类型
     *
     * @param keyName 指定键名
     */
    public DataType getKeyType(String keyName) {
        return redisTemplate.type(keyName);
    }

    /**
     * 设置redis指定键的过期时间
     *
     * @param key      指定键
     * @param timeOut  超时时间
     * @param timeUnit 时间类型 hour min sec
     */
    public Boolean setExpire(String key, long timeOut, TimeUnit timeUnit) {
        if (hasKey(key)) {
            return redisTemplate.expire(key, timeOut, timeUnit);
        } else {
            log.info("查无此键");
            return false;
        }

    }

    /**
     * 修改指定键名
     *
     * @param oldName 旧键名
     * @param newName 新键名
     */
    public void rename(String oldName, String newName) {
        redisTemplate.rename(oldName, newName);
    }

    /**
     * 删除指定键
     *
     * @param key 指定键
     */
    public Boolean deleteKey(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 删除指定键集合
     *
     * @param keys 键集合
     */
    public Boolean deleteKeys(Collection keys) {
        if (redisTemplate.delete(keys) > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 使指定键持久化
     * @param keyName 键名
     * */
    public Boolean persistKey(String keyName){
        return redisTemplate.persist(keyName);
    }

    /**
     * 获取指定键的过期时间
     * @param keyName 键名
     * */
    public long getExpireTime(String keyName){
        return redisTemplate.getExpire(keyName);
    }

    /**
     * 移动指定的键到指定的redisDB仓库
     * @param keyName 键名
     * @param index DB坐标
     * */
    public Boolean moveToDBIndex(String keyName,Integer index){
        return redisTemplate.move(keyName,index);
    }

    /**
     * 向redis(String)中写入键值对
     *
     * @param key   键
     * @param value 值
     */
    public void setStringKey(String key, String value) {
        if (StringUtils.equals(String.valueOf(getKeyType(key)), "NONE")|
                StringUtils.equals(String.valueOf(getKeyType(key)), "STRING")) {
            redisTemplate.opsForValue().set(key, value);
            log.info("key:{} value:{}", key, value);
        } else {
            log.warn("类型不一致,该键的原类型为:{}", getKeyType(key));
        }
    }

    /**
     * 向redis(list)中写入键值对
     *
     * @param key     键
     * @param values  值
     * @param operate 操作类型
     */
    public void setListKey(String key, String values, String operate) {
        if (StringUtils.equals(String.valueOf(getKeyType(key)), "NONE") |
                StringUtils.equals(String.valueOf(getKeyType(key)), "LIST")) {
            switch (operate) {
                case "right":
                    redisTemplate.opsForList().rightPush(key, values);
                    log.info("operate:{}", operate);
                    break;
                case "left":
                    redisTemplate.opsForList().leftPush(key, values);
                    log.info("operate:{}", operate);
                    break;
                default:
                    log.warn("没有该操作选项,请重新输入.");
                    break;
            }
            log.info("key:{} value:{}", key, values);
        } else {
            log.warn("类型不一致,该键的原类型为:{}", getKeyType(key));
        }
    }

    /**
     * 修改指定键(list)中指定坐标的值
     * */
    public void setListKeyWithValue(String keyName,long index,String value){
        if(StringUtils.equals(String.valueOf(getKeyType(keyName)), "LIST")){
            redisTemplate.opsForList().set(keyName,index,value);
        }else{
            log.warn("类型不一致,该键的原类型为:{}", getKeyType(keyName));
        }
    }

    /**
     * 从redis(String)中根据键取值
     *
     * @param keyName 键名
     *
     */
    public String getStringValue(String keyName) {
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        log.info("key:{} value:{}", keyName, ops.get(keyName));
        return ops.get(keyName);
    }

    /**
     * 从redis(List)获取指定键的值信息
     *
     * @param keyName 键名
     * */
    public void getListValue(String keyName){
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.opsForList().range(keyName,0,-1).forEach(infos->{
            log.info("infos:{}",infos);
        });
        log.info("size:{}",redisTemplate.opsForList().size(keyName));
    }

    /**
     * 向redis(hashMap)中插入键值对
     * @param keyName 键名
     * @param hashKey hash键名
     * @param value 值
     * */
    public void setHashMapKey(Object keyName,Object hashKey,Object value){
         redisTemplate.opsForHash().put(keyName, hashKey, value);
    }

    /**
     * 向redis(hashMap)中插入键值对 -- 通过map的形式
     * @param keyName 键名
     * @param maps 键值对集合
     * */
    public void setHashMapKeys(Object keyName, Map<Object,Object> maps){
         redisTemplate.opsForHash().putAll(keyName,maps);

    }

    /**
     * 从redis(HashMap)获取指定键的值信息
     *
     * @param keyName 键名
     * */
    public void getHashMapValue(String keyName){
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        HashOperations hashOperations = redisTemplate.opsForHash();
        List keyList = new ArrayList(hashOperations.keys(keyName));
        List valueList = hashOperations.values(keyName);
        for(Integer i = 0; i <hashOperations.size(keyName);i++){
            log.info("key:{} value:{}",keyList.get(i), valueList.get(i));
        }
        log.info("size:{}",redisTemplate.opsForHash().size(keyName));
    }

    /**
     * 匹配获取键值对
     * @param keyName 键名
     * @param options 设置扫描模式
     * */
    public Cursor<Map.Entry<Object, Object>> hashScan(String keyName, ScanOptions options) {
        return redisTemplate.opsForHash().scan(keyName, options);
    }

    /**
     * 向redis(set)中插入键值对
     * @param key 键名
     * @param value 值
     * */
    public void setSetKey(String key,String value){
        redisTemplate.opsForSet().add(key,value);
    }

    /**
     * 匹配获取值
     * @param keyName 键名
     * */
    public Set<Object> getSetScan(String keyName){
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate.opsForSet().members(keyName);
    }


}
