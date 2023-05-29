package com.omprakash.springbootplayground.config

import brave.baggage.BaggageField
import brave.baggage.CorrelationScopeConfig
import brave.context.slf4j.MDCScopeDecorator
import brave.propagation.CurrentTraceContext
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean

class TracingConfig {
    @Bean(name = ["bookId"])
    fun bookId(): BaggageField = BaggageField.create("bookId")

    @Bean
    fun mdcScopeDecorator(@Qualifier("bookId") bookId: BaggageField): CurrentTraceContext.ScopeDecorator {
        return MDCScopeDecorator.newBuilder().clear()
            .add(CorrelationScopeConfig.SingleCorrelationField.newBuilder(bookId).flushOnUpdate().build())
            .build()
    }
}