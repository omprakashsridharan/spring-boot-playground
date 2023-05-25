package com.omprakash.springbootplayground.config

import com.omprakash.springbootplayground.models.Book
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext


@Configuration
class RedisConfig {

    @Bean
    fun reactiveRedisTemplate(factory: ReactiveRedisConnectionFactory): ReactiveRedisTemplate<String, Book> {
        val serializer = Jackson2JsonRedisSerializer(Book::class.java)

        val builder = RedisSerializationContext.newSerializationContext<String, Book>(
            Jackson2JsonRedisSerializer(
                String::class.java
            )
        )
        val context = builder.value(serializer).build()

        return ReactiveRedisTemplate(factory, context)
    }
}