package com.omprakash.springbootplayground.services

import com.omprakash.springbootplayground.models.Book
import com.omprakash.springbootplayground.repository.BookRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.collect
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class BookService(
    private val reactiveRedisTemplate: ReactiveRedisTemplate<String, Book>
) {
    @Autowired
    private lateinit var bookRepository: BookRepository
    fun findAll(): Flow<Book> {
        val booksCacheFlow =
            reactiveRedisTemplate.keys("book:*").flatMap { reactiveRedisTemplate.opsForValue()[it!!] }.asFlow()
        return booksCacheFlow
            .onCompletion {
                bookRepository.findAll().collect {
                    emit(it)
                }
            }
            .onEmpty {
                bookRepository.findAll().collect {
                    emit(it)
                }
            }
            .map { book ->
                reactiveRedisTemplate.opsForValue().set("book:${book.id}", book, Duration.ofMinutes(5)).doOnError {
                    println("Error while saving book to cache ${it.message}")
                }.collect {
                    println("Saved book ${book.id} to cache")
                }
                return@map book
            }
    }
}