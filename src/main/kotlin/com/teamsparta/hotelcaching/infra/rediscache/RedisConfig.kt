package com.teamsparta.hotelcaching.infra.rediscache


import io.lettuce.core.ClientOptions
import io.lettuce.core.TimeoutOptions
import io.lettuce.core.resource.DefaultClientResources
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisPassword
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisCallback
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration



//
//@Configuration
//@ConditionalOnProperty(name = ["cache.redis.enabled"], havingValue = "true")
//@ConfigurationProperties(prefix = "spring.redis")
//@Import(RedisAutoConfiguration::class)
//class RedisConfig {
//    var host: String = "redis"
//    var port: Int = 6379
//    var password: String? = null
//
//    @Bean
//    fun clientOptions(): ClientOptions = ClientOptions.builder()
//        .disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS)
//        .timeoutOptions(TimeoutOptions.enabled(Duration.ofSeconds(1)))
//        .build()
//
//    @Bean
//    fun lettucePoolingClientConfiguration(clientOptions: ClientOptions) =
//        LettuceClientConfiguration.builder()
//            .clientOptions(clientOptions)
//            .clientResources(DefaultClientResources.create())
//            .build()
//
//    @Bean
//    fun redisStandaloneConfiguration() = RedisStandaloneConfiguration(host, port)
//        .apply {
//            if (this@RedisConfig.password != null) {
//                this.password = RedisPassword.of(this@RedisConfig.password)
//            }
//        }
//
//    @Bean
//    fun redisConnectionFactory(
//        redisStandaloneConfiguration: RedisStandaloneConfiguration,
//        lettucePoolingClientConfiguration: LettuceClientConfiguration
//    ): RedisConnectionFactory = LettuceConnectionFactory(redisStandaloneConfiguration, lettucePoolingClientConfiguration)
//
//    @Bean
//    fun redisTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<String, Any> {
//        val template = object : RedisTemplate<String, Any>() {
//            override fun <T : Any?> execute(  // redisTemplate을 활용한 캐싱할 경우 Exception이 생기면 null을 통한 missed 처리하기 위함
//                action: RedisCallback<T>,
//                exposeConnection: Boolean,
//                pipeline: Boolean
//            ): T? {
//                return runCatching {
//                    super.execute(action, exposeConnection, pipeline)
//                }.getOrElse {
//                    logger.warn(it.localizedMessage)
//                    null
//                }
//            }
//        }.apply {
//            this.isEnableDefaultSerializer = false
//            this.keySerializer = StringRedisSerializer()
//            this.valueSerializer = GenericJackson2JsonRedisSerializer(CacheConfig.objectMapper)
//        }
//
//        template.setConnectionFactory(redisConnectionFactory)
//        return template
//    }
//}