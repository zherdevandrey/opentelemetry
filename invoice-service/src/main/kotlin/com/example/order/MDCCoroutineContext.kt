package com.example.order

import kotlinx.coroutines.ThreadContextElement
import org.slf4j.MDC
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

class MDCCoroutineContext(
    private val contextMap: Map<String, String>?
) : ThreadContextElement<Map<String, String>?>, AbstractCoroutineContextElement(Key) {

    companion object Key : CoroutineContext.Key<MDCCoroutineContext>

    override fun updateThreadContext(context: CoroutineContext): Map<String, String>? {
        val previousContextMap = MDC.getCopyOfContextMap()
        MDC.setContextMap(contextMap)
        return previousContextMap
    }

    override fun restoreThreadContext(context: CoroutineContext, oldState: Map<String, String>?) {
        MDC.setContextMap(oldState)
    }
}