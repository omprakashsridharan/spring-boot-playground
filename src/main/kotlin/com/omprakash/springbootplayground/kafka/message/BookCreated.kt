package com.omprakash.springbootplayground.kafka.message

data class BookCreated(val id: Long = 1L, val title: String , val isbn: String)