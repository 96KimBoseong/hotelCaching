package com.teamsparta.hotelcaching.infra

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.*
import java.time.Duration

@Configuration
open class RedisConfig() {
    @Value("\${spring.data.redis.port}")
    private val port = 0

    @Value("\${spring.data.redis.host}")
    private val host: String = ""

    @Bean
    open fun redisConnectionFactory(): RedisConnectionFactory {
        return LettuceConnectionFactory(host, port)
    }

    @Bean
    open fun redisTemplate(): RedisTemplate<String, Any> {
        return RedisTemplate<String, Any>().apply {
            this.connectionFactory = redisConnectionFactory()

            this.keySerializer = StringRedisSerializer()
            this.valueSerializer = StringRedisSerializer()

            this.hashKeySerializer = StringRedisSerializer()
//            this.hashValueSerializer = GenericJackson2JsonRedisSerializer()
        }
    }

//    @Bean
//    fun cacheManager(): CacheManager {
//        val configuration = RedisCacheConfiguration.defaultCacheConfig()
//            .entryTtl(Duration.ofMinutes(1)) // 캐시 기본 ttl 시간 설정
//
//        return RedisCacheManager.RedisCacheManagerBuilder
//            .fromConnectionFactory(redisConnectionFactory())
//            .cacheDefaults(configuration)
//            .build()
//    }

//    @Bean
//    fun cacheManager(): CacheManager {
//        val configuration = RedisCacheConfiguration.defaultCacheConfig()
//            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer()))
//            .serializeValuesWith(
//                RedisSerializationContext.SerializationPair.fromSerializer(
//                    GenericJackson2JsonRedisSerializer()
//                )) // Serialize 관련 설정
//            .entryTtl(Duration.ofMinutes(32)) // 캐시 기본 ttl 32분 설정
//
//        return RedisCacheManager.RedisCacheManagerBuilder
//            .fromConnectionFactory(redisConnectionFactory())
//            .cacheDefaults(configuration)
//            .build()
//    }

}