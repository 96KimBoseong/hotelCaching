package com.teamsparta.hotelcaching.infra.rediscache


import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.teamsparta.hotelcaching.logger
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.cache.Cache
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.CachingConfigurer
import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.interceptor.CacheErrorHandler
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration



//
//class CacheConfig {
//
//    companion object {
//        const val CACHE_PREFIX = "example:"
//        const val USER_KEY_PREFIX = "${CACHE_PREFIX}user:summary::"
//        const val CACHE_TTL = 30L
//
//        val objectMapper: ObjectMapper = ObjectMapper()
//            .registerKotlinModule()
//            .registerModule(JavaTimeModule())
//            .activateDefaultTyping(
//                BasicPolymorphicTypeValidator.builder().allowIfBaseType(Any::class.java).build(),
//                ObjectMapper.DefaultTyping.EVERYTHING
//            )
//    }
//
//    @Configuration
//    @ConditionalOnProperty(name = ["cache.redis.enabled"], havingValue = "true")
//    @EnableCaching
//    class RedisCacheConfig(
//        val redisConnectionFactory: RedisConnectionFactory
//    ) : CachingConfigurerSupport(), CachingConfigurer {
//
//        fun redisCacheConfiguration() = RedisCacheConfiguration.defaultCacheConfig()
//            .disableCachingNullValues()
//            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer()))
//            .serializeValuesWith(
//                RedisSerializationContext.SerializationPair.fromSerializer(
//                    GenericJackson2JsonRedisSerializer(objectMapper)
//                )
//            )
//            .prefixCacheNameWith(CACHE_PREFIX)
//            .entryTtl(Duration.ofSeconds(CACHE_TTL))
//
//        override fun cacheManager(): CacheManager = RedisCacheManager.RedisCacheManagerBuilder
//            .fromConnectionFactory(redisConnectionFactory)
//            .cacheDefaults(redisCacheConfiguration())
//            .build()
//
//        override fun errorHandler(): CacheErrorHandler {
//            return object : CacheErrorHandler {
//                override fun handleCacheGetError(exception: RuntimeException, cache: Cache, key: Any) {
//                    logger().warn("[REDIS_CACHE:GET:${cache.name}]: ${exception.message}")
//                }
//
//                override fun handleCachePutError(exception: RuntimeException, cache: Cache, key: Any, value: Any?) {
//                    logger().warn("[REDIS_CACHE:PUT:${cache.name}]: ${exception.message}")
//                }
//
//                override fun handleCacheEvictError(exception: RuntimeException, cache: Cache, key: Any) {
//                    logger().warn("[REDIS_CACHE:EVICT:${cache.name}]: ${exception.message}")
//                }
//
//                override fun handleCacheClearError(exception: RuntimeException, cache: Cache) {
//                    logger().warn("[REDIS_CACHE:CLEAR:${cache.name}]: ${exception.message}")
//                }
//            }
//        }
//    }
//}