package com.omprakash.springbootplayground.repository

import com.omprakash.springbootplayground.models.Book
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface BookRepository : CoroutineCrudRepository<Book, Long> {}
