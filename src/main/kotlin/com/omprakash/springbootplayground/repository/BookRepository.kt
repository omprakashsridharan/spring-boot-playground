package com.omprakash.springbootplayground.repository

import com.omprakash.springbootplayground.models.Book
import org.springframework.data.r2dbc.repository.R2dbcRepository

interface BookRepository : R2dbcRepository<Book, Long>