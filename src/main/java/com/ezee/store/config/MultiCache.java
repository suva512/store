package com.ezee.store.config;

import java.time.Duration;

import javax.cache.Caching;
import javax.cache.spi.CachingProvider;

import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

@Configuration
@EnableCaching
public class MultiCache {

	    @Bean
	    @Qualifier("redisCacheManager")
	    public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
	        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
	                .entryTtl(Duration.ofMinutes(1))
	                .serializeValuesWith(
	                        RedisSerializationContext.SerializationPair.fromSerializer(
	                                new GenericJackson2JsonRedisSerializer()
	                        )
	                );

	        return RedisCacheManager.builder(redisConnectionFactory)
	                .cacheDefaults(config)
	                .build();
	    }

	    @Bean
	    @Qualifier("ehCacheManager")
	    public CacheManager ehCacheManager() {
	        CacheConfiguration<Object, Object> ehCacheConfig = CacheConfigurationBuilder
	                .newCacheConfigurationBuilder(
	                        Object.class,
	                        Object.class,
	                        ResourcePoolsBuilder.heap(100)
	                )
	                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMinutes(1)))
	                .build();

	        CachingProvider cachingProvider = Caching.getCachingProvider();
	        javax.cache.CacheManager jCacheManager = cachingProvider.getCacheManager();

	        jCacheManager.createCache("localCustomers", Eh107Configuration.fromEhcacheCacheConfiguration(ehCacheConfig));
	        jCacheManager.createCache("localCategory", Eh107Configuration.fromEhcacheCacheConfiguration(ehCacheConfig));
	        jCacheManager.createCache("localWeight", Eh107Configuration.fromEhcacheCacheConfiguration(ehCacheConfig));
	        jCacheManager.createCache("localProduct", Eh107Configuration.fromEhcacheCacheConfiguration(ehCacheConfig));
	        jCacheManager.createCache("localVendor", Eh107Configuration.fromEhcacheCacheConfiguration(ehCacheConfig));

	        return new JCacheCacheManager(jCacheManager);
	    }

	    @Primary
	    @Bean
	    public CacheManager compositeCacheManager(
	            @Qualifier("ehCacheManager") CacheManager ehCacheManager,
	            @Qualifier("redisCacheManager") CacheManager redisCacheManager) {

	        CompositeCacheManager cacheManager = new CompositeCacheManager(ehCacheManager, redisCacheManager);
	        cacheManager.setFallbackToNoOpCache(false);
	        return cacheManager;
	    }
	}



