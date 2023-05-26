package com.omprakash.springbootplayground.kafka

import io.confluent.kafka.serializers.KafkaAvroSerializer
import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.producer.ProducerConfig.*
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

@Configuration
class ProducerConfig(val kafkaProperties: KafkaProperties) {

    @Bean
    fun producerFactory(): ProducerFactory<String, GenericRecord> {
        return mapOf<String, Any>(
            BOOTSTRAP_SERVERS_CONFIG to kafkaProperties.bootstrapServers,
            KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            VALUE_SERIALIZER_CLASS_CONFIG to KafkaAvroSerializer::class.java,
            "schema.registry.url" to "http://localhost:8081",
        ).let(::DefaultKafkaProducerFactory)
    }

    @Bean
    fun booksCreatedKafkaTemplate() = KafkaTemplate(producerFactory())

}