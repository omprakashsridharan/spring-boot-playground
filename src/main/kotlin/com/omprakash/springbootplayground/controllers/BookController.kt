package com.omprakash.springbootplayground.controllers

import brave.baggage.BaggageField
import com.omprakash.springbootplayground.kafka.BookCreatedKafkaProducer
import com.omprakash.springbootplayground.models.Book
import com.omprakash.springbootplayground.models.request.CreateBook
import com.omprakash.springbootplayground.services.BookService
import io.micrometer.core.instrument.kotlin.asContextElement
import io.micrometer.observation.ObservationRegistry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactor.mono
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/books")
class BookController(
    private val bookService: BookService,
    private val bookKafkaProducer: BookCreatedKafkaProducer,
    @Qualifier("bookId") var bookIdBaggageField: BaggageField,
    private val observationRegistry: ObservationRegistry
) {

    var logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping
    fun findAll(): Flow<Book> {
        return bookService.findAll()
    }

    @PostMapping("/publish")
    fun publishBook(@RequestBody createBook: CreateBook): Mono<String> {
        return mono(observationRegistry.asContextElement()) {
            try {
                bookIdBaggageField.updateValue(createBook.id.toString())
                bookKafkaProducer.publishBook(createBook.id, createBook.title, createBook.isbn).toString()
                logger.info("Book published")
            } catch (e: Exception) {
                println("Exception while publishing books ${e.message}")
                e.message ?: "Exception"
            }
            "Completed"
        }
    }
}