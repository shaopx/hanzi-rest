package com.spx.gushi;

import groovy.lang.Singleton;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by SHAOPENGXIANG on 2016/12/21.
 */
@Component
@Singleton
public class GCacheManager {

    private Cache<String, List> myCache;

    public GCacheManager() {
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("preConfigured",
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, List.class,
                                ResourcePoolsBuilder.heap(400))
                                .build())
                .build(true);

        Cache<String, List> preConfigured
                = cacheManager.getCache("preConfigured", String.class, List.class);

        myCache = cacheManager.createCache("myCache",
                CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, List.class,
                        ResourcePoolsBuilder.heap(400)).build());
    }

    public void put(String key, List list) {
        myCache.put(key, list);
    }

    public List get(String key){
        return myCache.get(key);
    }
}
