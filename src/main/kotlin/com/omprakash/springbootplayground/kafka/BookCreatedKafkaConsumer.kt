package com.omprakash.springbootplayground.kafka

import com.omprakash.springbootplayground.kafka.message.BookCreated
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class BookCreatedKafkaConsumer {

    @KafkaListener(topics = [TOPICS.BOOKS_CREATED], groupId = GROUP_ID.BOOKS_CREATED_CONSUMER, containerFactory = "bookCreatedKafkaListenerContainerFactory")
    fun onBookCreated(bookCreated: BookCreated) {
        println("Consumed message $bookCreated")
    }
}