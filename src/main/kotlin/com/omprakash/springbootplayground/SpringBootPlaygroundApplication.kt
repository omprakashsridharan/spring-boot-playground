package com.omprakash.springbootplayground

import com.omprakash.springbootplayground.config.TracingConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import
import org.springframework.kafka.annotation.EnableKafka

@SpringBootApplication
@EnableKafka
@Import(TracingConfig::class)
class SpringBootPlaygroundApplication

fun main(args: Array<String>) {
    runApplication<SpringBootPlaygroundApplication>(*args)
}