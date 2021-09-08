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

    public static final String STRING = "STRING";
    public static final String HASH = "HASH";
    public static final String LIST = "LIST";
    public static final String SET = "SET";
    public static final String ZSET = "ZSET";


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

    public void outPutCollection(Collection collection){
        collection.forEach(infos->{
            log.info("集合内容:{}",infos);
        });
    }

    public void outPutCursor(Cursor<Map.Entry<Object, Object>> cursor){
        cursor.forEachRemaining(infos->{
            log.info("集合内容[key:{}  value:{}]",infos.getKey(),infos.getValue());
        });
    }

    /**
     * 向redis(String)中写入键值对
     *
     * @param key   键
     * @param value 值
     */
    public void setStringKey(String key, String value) {
        if (StringUtils.equals(String.valueOf(getKeyType(key)), "NONE")|
                StringUtils.equals(String.valueOf(getKeyType(key)), RedisUnit.STRING)) {
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
                StringUtils.equals(String.valueOf(getKeyType(key)), RedisUnit.LIST)) {
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
        if(StringUtils.equals(String.valueOf(getKeyType(keyName)), RedisUnit.LIST)){
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
        /*redisTemplate.setValueSerializer(new StringRedisSerializer());*/
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
        /*redisTemplate.setValueSerializer(new StringRedisSerializer());*/
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
        /*redisTemplate.setValueSerializer(new StringRedisSerializer());*/
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
        //redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate.opsForSet().members(keyName);
    }

    /**
     * 获取两个SET的交集(I) 并集(U) 差集(D) ,并且是否存储于指定键中
     * @param key 键名
     * @param otherKey 其他键名
     * @param operateOne 操作一 交集(I) 并集(U) 差集(D)
     * @param operateTwo 操作二 Y/other
     * */
    public Set<Object> getAndStoreSetCollection(String key,String otherKey,String operateOne,String destKey,String operateTwo){
        Set<Object> set;
        if(StringUtils.equals(String.valueOf(getKeyType(key)), RedisUnit.SET) &
                StringUtils.equals(String.valueOf(getKeyType(otherKey)), RedisUnit.SET)){
            switch (operateOne.toUpperCase()){
                case "I":
                    set = redisTemplate.opsForSet().intersect(key,otherKey);
                    if(StringUtils.equals(operateTwo.toUpperCase(),"Y")){
                        redisTemplate.opsForSet().intersectAndStore(key,otherKey,destKey);
                        log.info("存储成功");
                    }
                    return set;
                case "U":
                    set = redisTemplate.opsForSet().union(key,otherKey);
                    if(StringUtils.equals(operateTwo.toUpperCase(),"Y")){
                        redisTemplate.opsForSet().unionAndStore(key,otherKey,destKey);
                        log.info("存储成功");
                    }
                    return set;
                case "D":
                    set = redisTemplate.opsForSet().difference(key,otherKey);
                    if(StringUtils.equals(operateTwo.toUpperCase(),"Y")){
                        redisTemplate.opsForSet().differenceAndStore(key,otherKey,destKey);
                        log.info("存储成功");
                    }
                    return set;
            }
        }else{
            log.info("类型必须都为SET类型 key:{} otherKey:{}",getKeyType(key),getKeyType(otherKey));
        }
        return null;
    }

    /**
     * 向redis(ZSet)中插入键值对
     * @param key 键名
     * @param value 值
     * @param score 优先级大小 从小到大
     * */
    public Boolean setZSetKey(String key,String value,long score){
        return redisTemplate.opsForZSet().add(key,value,score);
    }

    /**
     * 根据score值查询ZSet集合内容 从小到大
     * @param keyName 键名
     * @param minScore 最小值
     * @param maxScore 最大值
     * */
    public Set<Object> getZSetValueByScore(String keyName,long minScore,long maxScore){
        return redisTemplate.opsForZSet().range(keyName,minScore,maxScore);
    }

    /**
     * 存储两个ZSet的交集(I) 并集(U) ,于指定键中
     * @param key 键名
     * @param otherKey 其他键名
     * @param operate 操作
     * @param destKey 存储键名
     * */
    public void storeZSetCollection(String key,String otherKey,String destKey,String operate){
        if(StringUtils.equals(String.valueOf(getKeyType(key)), RedisUnit.ZSET) &
                StringUtils.equals(String.valueOf(getKeyType(otherKey)), RedisUnit.ZSET)){
            switch (operate.toUpperCase()){
                case "I":
                        redisTemplate.opsForZSet().intersectAndStore(key,otherKey,destKey);
                        log.info("存储成功");
                    break;
                case "U":
                        redisTemplate.opsForZSet().unionAndStore(key,otherKey,destKey);
                        log.info("存储成功");
                    break;
            }
        }else{
            log.info("类型必须都为ZSET类型 key:{} otherKey:{}",getKeyType(key),getKeyType(otherKey));
        }
    }

    /**
     * 删除指定键中的指定value
     * @param keyName 指定键
     * @param value 指定值
     * @param listIndex LIST类型中的下标
     * @param operate 操作
     * */
    public void deleteValue(String keyName,String value,Long listIndex,String operate){
        switch (operate.toUpperCase()){
            case RedisUnit.STRING:
                if(StringUtils.equals(String.valueOf(getKeyType(keyName)), RedisUnit.STRING)){
                    redisTemplate.delete(keyName);
                    log.info("操作成功");
                }else{
                    log.info("类型错误,输入类型为:{}",getKeyType(keyName));
                }
                break;
            case RedisUnit.LIST:
                if(StringUtils.equals(String.valueOf(getKeyType(keyName)), RedisUnit.LIST)){
                    redisTemplate.opsForList().remove(keyName,listIndex,value);
                    log.info("操作成功");
                }else{
                    log.info("类型错误,输入类型为:{}",getKeyType(keyName));
                }
                break;
            case RedisUnit.HASH:
                if(StringUtils.equals(String.valueOf(getKeyType(keyName)), RedisUnit.HASH)){
                    redisTemplate.opsForHash().delete(keyName,value);
                    log.info("操作成功");
                }else{
                    log.info("类型错误,输入类型为:{}",getKeyType(keyName));
                }
                break;
            case RedisUnit.SET:
                if(StringUtils.equals(String.valueOf(getKeyType(keyName)), RedisUnit.SET)){
                    redisTemplate.opsForSet().remove(keyName,value);
                    log.info("操作成功");
                }else{
                    log.info("类型错误,输入类型为:{}",getKeyType(keyName));
                }
                break;
            case RedisUnit.ZSET:
                if(StringUtils.equals(String.valueOf(getKeyType(keyName)), RedisUnit.ZSET)){
                    redisTemplate.opsForZSet().remove(keyName,value);
                    log.info("操作成功");
                }else{
                    log.info("类型错误,输入类型为:{}",getKeyType(keyName));
                }
                break;
        }
    }

}
