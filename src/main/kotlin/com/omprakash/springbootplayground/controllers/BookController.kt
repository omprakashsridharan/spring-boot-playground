package com.omprakash.springbootplayground.controllers

import com.omprakash.springbootplayground.models.Book
import com.omprakash.springbootplayground.services.BookService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/books")
class BookController(private val bookService: BookService) {

    @GetMapping
    fun findAll(): Flux<Book> {
        return bookService.findAll()
    }
}