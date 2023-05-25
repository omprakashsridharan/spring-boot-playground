package com.omprakash.springbootplayground.kafka

import com.omprakash.springbootplayground.kafka.message.BookCreated
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class BookKafkaProducer(private val kafkaTemplate: KafkaTemplate<String, BookCreated>) {

    suspend fun publishBook(bookCreated: BookCreated): RecordMetadata {
        val sendResult = kafkaTemplate.sendAsync(ProducerRecord(TOPICS.BOOKS, "${bookCreated.id}", bookCreated))
        return sendResult.recordMetadata
    }
}