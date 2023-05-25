package com.omprakash.springbootplayground.kafka

import com.omprakash.springbootplayground.kafka.message.BookCreated
import org.apache.kafka.clients.producer.ProducerConfig.*
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer

@Configuration
class ProducerConfig(val kafkaProperties: KafkaProperties) {

    @Bean
    fun booksProducerFactory(): ProducerFactory<String, BookCreated> {
        return mapOf<String, Any>(
            BOOTSTRAP_SERVERS_CONFIG to kafkaProperties.bootstrapServers,
            KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java
        ).let(::DefaultKafkaProducerFactory)
    }

    @Bean
    fun booksKafkaTemplate(): KafkaTemplate<String, BookCreated> = KafkaTemplate(booksProducerFactory())

}