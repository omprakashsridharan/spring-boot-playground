package com.omprakash.springbootplayground.kafka

import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaAdmin

object TOPICS {
    const val BOOKS_CREATED = "books_created"
}


@Configuration
class TopicConfig(val kafkaProperties: KafkaProperties) {

    @Bean
    fun kafkaAdmin(): KafkaAdmin {
        val configs: MutableMap<String, Any?> = HashMap()
        configs[AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaProperties.bootstrapServers
        return KafkaAdmin(configs)
    }
    @Bean
    fun books(): NewTopic? {
        return NewTopic(TOPICS.BOOKS_CREATED, 1, 1.toShort())
    }
}