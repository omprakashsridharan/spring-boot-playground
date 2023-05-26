package com.omprakash.springbootplayground

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.kafka.annotation.EnableKafka

@SpringBootApplication
@EnableKafka
class SpringBootPlaygroundApplication

fun main(args: Array<String>) {
    runApplication<SpringBootPlaygroundApplication>(*args)
}