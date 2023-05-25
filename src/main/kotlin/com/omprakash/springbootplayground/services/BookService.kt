package com.omprakash.springbootplayground.services

import com.omprakash.springbootplayground.models.Book
import com.omprakash.springbootplayground.repository.BookRepository
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.time.Duration

@Service
class BookService(
    private val bookRepository: BookRepository,
    private val reactiveRedisTemplate: ReactiveRedisTemplate<String, Book>
) {
    fun findAll(): Flux<Book> {
        return reactiveRedisTemplate.keys("book:*").flatMap { key -> reactiveRedisTemplate.opsForValue()[key!!] }
            .switchIfEmpty(
                bookRepository.findAll().flatMap { book ->
                    reactiveRedisTemplate.opsForValue().set("book:$book.id", book, Duration.ofMinutes(5))
                }.thenMany(reactiveRedisTemplate.keys("book:*").flatMap { reactiveRedisTemplate.opsForValue().get(it) })
            )
    }
}