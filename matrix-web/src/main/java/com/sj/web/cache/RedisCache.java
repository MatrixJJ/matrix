package com.sj.web.cache;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

/**
 * Redis 缓存操作
 */
@Slf4j
@Repository
@ConditionalOnClass(RedisTemplate.class)
public class RedisCache {

  private final static String SEQUENCE = "sys:sequence";
  private final static long SEQ_DELTA = 1;
  private final static int DEFAULT_INDEX = 0;


  @Autowired
  @Qualifier("redisTemplate")
  private RedisTemplate redisTemplate;

  public RedisTemplate getRedisTemplate() {
    return redisTemplate;
  }

  /**
   * 写入缓存设置时效时间
   *
   * @param key        键
   * @param value      值
   * @param expireTime 过期时间 （-1：永不过期）
   */
  public boolean set(final String key, final Object value, Duration expireTime) {
    try {
      redisTemplate.opsForValue().set(key, value);
      expire(key, expireTime);
      return true;
    } catch (Exception e) {
      log.error("写入缓存失败：[{}:{}], {}", key, value, e.getMessage());
      return false;
    }
  }

  /**
   * 获取缓存
   *
   * @param key 键
   * @return 值
   */
  public Object get(final String key) {
    try {
      return redisTemplate.opsForValue().get(key);
    } catch (Exception e) {
      log.error("获取缓存失败：[{}], {}", key, e.getMessage());
      throw e;
    }
  }

  /**
   * 删除缓存
   *
   * @param key 键
   * @return
   */
  public boolean delete(final String key) {
    try {
      return redisTemplate.delete(key);
    } catch (Exception e) {
      log.error("删除缓存失败：[{}], {}", key, e.getMessage());
      return false;
    }
  }

  /**
   * 设置缓存过期时间
   *
   * @param key        键
   * @param expireTime 过期时间 （-1：永不过期）
   * @return
   */
  public boolean expire(final String key, Duration expireTime) {
    try {
      long expireMillis = expireTime.toMillis();
      if (expireMillis > 0) {
        return redisTemplate.expire(key, expireMillis, TimeUnit.MILLISECONDS);
      }
      return false;
    } catch (Exception e) {
      log.error("设置过期失败：[{}], {}", key, e.getMessage());
      return false;
    }
  }

  /**
   * key 是否存在
   *
   * @param key 键
   * @return
   */
  public boolean hasKey(final String key) {
    return redisTemplate.hasKey(key);
  }

  /**
   * 删除缓存
   *
   * @param pattern 通配符键
   * @return
   */
  public long deletePattern(final String pattern) {
    try {
      Set keys = redisTemplate.keys(pattern);
      return redisTemplate.delete(keys);
    } catch (Exception e) {
      log.error("删除缓存失败：[{}], {}", pattern, e.getMessage());
      return -1;
    }
  }

  /* ----- Hash ----- */

  /**
   * 获取递增序列
   *
   * @param hashKey 序列名
   * @return 序列号
   */
  public long getSequence(final String hashKey) {
    try {
      return redisTemplate.opsForHash().increment(SEQUENCE, hashKey, SEQ_DELTA);
    } catch (Exception e) {
      log.error("获取序列失败：[{}], {}", hashKey, e.getMessage());
      return -1;
    }
  }

  /**
   * 获取递增序列
   *
   * @param hashKey 序列名
   * @param delta   增量
   * @return 序列号
   */
  public long increment(final String hashKey, long delta) {
    try {
      return redisTemplate.opsForHash().increment(SEQUENCE, hashKey, delta);
    } catch (Exception e) {
      log.error("获取递增序列失败：[{},{}], {}", hashKey, delta, e.getMessage());
      return -1;
    }
  }


  /**
   * 获取递减序列
   *
   * @param hashKey 序列名
   * @param delta   增量
   * @return 序列号
   */
  public long decrease(final String hashKey, long delta) {
    try {
      return redisTemplate.opsForHash().increment(SEQUENCE, hashKey, -delta);
    } catch (Exception e) {
      log.error("获取递减序列失败：[{},{}], {}", hashKey, delta, e.getMessage());
      return -1;
    }
  }


  /* ----- List ----- */

  /**
   * 将一个值 value 插入到列表 key 的表头(最左边)。
   *
   * @param key        键
   * @param value      值
   * @param expireTime 过期时间 （-1：永不过期）
   * @return 执行 lpush 操作后，表的长度。
   */
  public long leftPush(final String key, Object value, Duration expireTime) {
    try {
      long size = redisTemplate.opsForList().leftPush(key, value);
      expire(key, expireTime);
      return size;
    } catch (Exception e) {
      log.error("leftPush[{},{}], {}", key, value, e.getMessage());
      e.printStackTrace();
      return -1;
    }
  }

  /**
   * 将一个值 value 插入到列表 key 的表尾(最右边)。
   *
   * @param key        键
   * @param value      值
   * @param expireTime 过期时间 （-1：永不过期）
   * @return 执行 rpush 操作后，表的长度。
   */
  public long rightPush(final String key, Object value, Duration expireTime) {
    try {
      Long size = redisTemplate.opsForList().rightPush(key, value);
      expire(key, expireTime);
      return size;
    } catch (Exception e) {
      log.error("rightPush[{},{}], {}", key, value, e.getMessage());
      e.printStackTrace();
      return -1;
    }
  }

  /**
   * 移除并返回列表 key 的头元素。
   *
   * @param key 键
   * @return 列表的头元素。 当 key 不存在时，返回 null。
   */
  public Object leftPop(final String key) {
    try {
      return redisTemplate.opsForList().leftPop(key);
    } catch (Exception e) {
      log.error("leftPop[{}], {}", key, e.getMessage());
      return null;
    }
  }

  /**
   * 移除并返回列表 key 的尾元素。
   *
   * @param key 键
   * @return 列表的尾元素。 当 key 不存在时，返回 null。
   */
  public Object rightPop(final String key) {
    try {
      return redisTemplate.opsForList().rightPop(key);
    } catch (Exception e) {
      log.error("rightPop[{}], {}", key, e.getMessage());
      return null;
    }
  }

  /**
   * 返回列表 key 的长度。
   *
   * @param key 键
   * @return 列表 key 的长度。
   */
  public long listSize(final String key) {
    try {
      return redisTemplate.opsForList().size(key);
    } catch (Exception e) {
      log.error("listSize[{}], {}", key, e.getMessage());
      return -1;
    }
  }

  /**
   * 将多个值 value 插入到列表 key 的表尾(最右边)。
   *
   * @param key        键
   * @param values     值
   * @param expireTime 过期时间 （-1：永不过期）
   * @return 列表 key 的长度。
   */
  public long rightPushAll(final String key, final Collection values, Duration expireTime) {
    try {
      Long size = redisTemplate.opsForList().rightPushAll(key, values);
      expire(key, expireTime);
      return size;
    } catch (Exception e) {
      log.error("listSize[{}], {}", key, e.getMessage());
      return -1;
    }
  }

  /**
   * 返回列表范围。
   *
   * @param key 键
   * @return 列表 key 的长度。
   */
  public List range(final String key, final long start, final long end) {
    try {
      return redisTemplate.opsForList().range(key, start, end);
    } catch (Exception e) {
      log.error("listSize[{}], {}", key, e.getMessage());
      return new ArrayList<>();
    }
  }


}