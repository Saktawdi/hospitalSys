package top.cnwdi.Sakta_hospitalSys.utils;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class BashCache {
    private Cache<String,Object> oneMinuteCache = CacheBuilder.newBuilder()

            //设置缓存初始大小
            .initialCapacity(10)
            //最大值
            .maximumSize(100)
            //并发数设置
            .concurrencyLevel(5)

            //缓存过期时间，写入后1分钟过期
            .expireAfterWrite(60, TimeUnit.SECONDS)

            //统计缓存命中率
            .recordStats()

            .build();


    public Cache<String, Object> getOneMinuteCache() {
        return oneMinuteCache;
    }

    public void setOneMinuteCache(Cache<String, Object> oneMinuteCache) {
        this.oneMinuteCache = oneMinuteCache;
    }
}
