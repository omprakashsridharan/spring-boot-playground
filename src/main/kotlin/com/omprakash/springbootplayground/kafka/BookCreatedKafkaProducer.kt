package com.omprakash.springbootplayground.kafka

import com.omprakash.springbootplayground.kafka.message.BookCreated
import org.apache.avro.Schema
import org.apache.avro.generic.GenericRecord
import org.apache.avro.generic.GenericRecordBuilder
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.io.File

@Service
class BookCreatedKafkaProducer(private val kafkaTemplate: KafkaTemplate<String, GenericRecord>) {

    suspend fun publishBook(id: Long = 1L, title: String = "", isbn: String = ""): RecordMetadata {
        val bookCreated = BookCreated(id, title, isbn)
        val schema = Schema.Parser().parse(File("src/main/resources/book_created.avsc"))
        val avroRecord = GenericRecordBuilder(schema).apply {
            set("id", bookCreated.id)
            set("title", bookCreated.title)
            set("isbn", bookCreated.isbn)
        }.build()
        val sendResult = kafkaTemplate.sendAsync(ProducerRecord(TOPICS.BOOKS_CREATED, "${bookCreated.id}", avroRecord))
        return sendResult.recordMetadata
    }
}