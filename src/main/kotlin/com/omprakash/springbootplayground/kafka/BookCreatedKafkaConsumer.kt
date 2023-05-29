package com.omprakash.springbootplayground.kafka

import org.apache.avro.generic.GenericRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class BookCreatedKafkaConsumer {

    var logger = LoggerFactory.getLogger(this::class.java)

    @KafkaListener(topics = [TOPICS.BOOKS_CREATED], groupId = GROUP_ID.BOOKS_CREATED_CONSUMER, containerFactory = "bookCreatedKafkaListenerContainerFactory")
    fun onBookCreated(bookCreated: GenericRecord) {
        logger.info("Consumed message $bookCreated")
    }
}