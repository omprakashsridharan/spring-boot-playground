package com.omprakash.springbootplayground.models

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("book")
data class Book(@Id val id: Long = 1L, @Column("title") val title: String = "",@Column("isbn") val isbn: String ="")