package com.omprakash.springbootplayground.kafka

import brave.Tracing
import brave.kafka.clients.KafkaTracing
import io.confluent.kafka.serializers.KafkaAvroDeserializer
import org.apache.avro.generic.GenericRecord
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
    fun consumerFactory(): ConsumerFactory<String, GenericRecord> {
        val props = mapOf<String, Any>(
            BOOTSTRAP_SERVERS_CONFIG to kafkaProperties.bootstrapServers,
            KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            VALUE_DESERIALIZER_CLASS_CONFIG to KafkaAvroDeserializer::class.java,
            "schema.registry.url" to "http://localhost:8081",
            JsonDeserializer.TRUSTED_PACKAGES to "*"
        )
        return DefaultKafkaConsumerFactory(props)
    }

    @Bean
    fun kafkaTracing(tracing: Tracing?): KafkaTracing? {
        return KafkaTracing.create(tracing)
    }

    @Bean
    fun bookCreatedKafkaListenerContainerFactory(kafkaTracing: KafkaTracing): ConcurrentKafkaListenerContainerFactory<String, GenericRecord> {
        return ConcurrentKafkaListenerContainerFactory<String, GenericRecord>().apply {
            val cf = consumerFactory()
            cf.addPostProcessor(kafkaTracing::consumer)
            consumerFactory = cf
            this.containerProperties.isObservationEnabled = true
        }
    }
}