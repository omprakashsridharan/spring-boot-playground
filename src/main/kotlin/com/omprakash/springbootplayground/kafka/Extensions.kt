package com.omprakash.springbootplayground.kafka

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@OptIn(ExperimentalCoroutinesApi::class)
suspend inline fun <reified K : Any, reified V : Any> KafkaTemplate<K, V>.sendAsync(record: ProducerRecord<K, V>): SendResult<K, V> {
    return suspendCancellableCoroutine { cancellableContinuation ->
        val future = this.send(record)
        this.setObservationEnabled(true)
        future.whenComplete { metadata, exception ->
            if (metadata != null) {
                cancellableContinuation.resume(metadata)
            } else {
                cancellableContinuation.resumeWithException(exception)
            }
        }
        cancellableContinuation.invokeOnCancellation {
            future.cancel(true)
        }
    }
}