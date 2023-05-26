package com.omprakash.springbootplayground.kafka

import com.omprakash.springbootplayground.kafka.message.BookCreated
import org.apache.kafka.clients.consumer.ConsumerConfig.*
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer

object GROUP_ID {
    const val BOOKS_CREATED_CONSUMER = "books_created_consumer"
}

@Configuration
class ConsumerConfig(val kafkaProperties: KafkaProperties) {
    @Bean
    fun  consumerFactory(): ConsumerFactory<String, BookCreated> {
        val props =  mapOf<String, Any>(
            BOOTSTRAP_SERVERS_CONFIG to kafkaProperties.bootstrapServers,
            KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            VALUE_DESERIALIZER_CLASS_CONFIG to JsonDeserializer::class.java,
            JsonDeserializer.TRUSTED_PACKAGES to "*"
        )
        return DefaultKafkaConsumerFactory(props)
    }

    @Bean
    fun bookCreatedKafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, BookCreated> {
        return ConcurrentKafkaListenerContainerFactory<String, BookCreated>().apply {
            consumerFactory = consumerFactory()
        }
    }
}