package pxf.weixin.manager.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pxf.weixin.enums.KeyType;
import redis.clients.jedis.Jedis;

/**
 * @Author pxf
 * @Date 2018/3/18
 * @Description
 */
@Service
public class RedisManager {
    private static final Logger log = LoggerFactory.getLogger(RedisManager.class);

    private static Jedis jedis = null;

    static {
        jedis = new Jedis("127.0.0.1",6379);
    }

    /**
     * 将数据存入redis缓存，如果相同key，则覆盖数据
     * @param keyType 缓存数据类型
     * @param value 缓存值
     * @param expireTime 有效时间
     */
    public void setString(KeyType keyType, String value,int expireTime){
        log.info("settting data to redis:key[{}],value[{}]",keyType.getKey(),value);
        jedis.set(keyType.getKey(),value);
        jedis.expire(keyType.getKey(),expireTime);
    }
    public String getString(KeyType keyType){
        return jedis.get(keyType.getKey());
    }

}
