package com.omprakash.springbootplayground.controllers

import com.omprakash.springbootplayground.kafka.BookCreatedKafkaProducer
import com.omprakash.springbootplayground.models.Book
import com.omprakash.springbootplayground.services.BookService
import kotlinx.coroutines.flow.Flow
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/books")
class BookController(private val bookService: BookService, private val bookKafkaProducer: BookCreatedKafkaProducer) {

    @GetMapping
    fun findAll(): Flow<Book> {
        return bookService.findAll()
    }

    @GetMapping("/publish")
    suspend fun publishBook(): String {
        return try {
            bookKafkaProducer.publishBook(1L, "Test", "1234").toString()
        } catch (e: Exception) {
            println("Exception while publishing books ${e.message}")
            e.message ?: "Exception"
        }
    }
}