package com.example.order.config

import io.opentelemetry.api.common.AttributeKey
import io.opentelemetry.api.common.Attributes
import io.opentelemetry.api.trace.SpanKind
import io.opentelemetry.context.Context
import io.opentelemetry.sdk.trace.data.LinkData
import io.opentelemetry.sdk.trace.samplers.Sampler
import io.opentelemetry.sdk.trace.samplers.SamplingResult

class CustomSampler : Sampler {

    private val healthEndpoint = "/actuator/health"

    override fun shouldSample(p0: Context, p1: String, p2: String, p3: SpanKind, p4: Attributes, p5: MutableList<LinkData>): SamplingResult {
        val httpTarget = p4.get(AttributeKey.stringKey("url.path"))
        return if (httpTarget == healthEndpoint) {
            SamplingResult.drop() // Drop traces for /actuator/health
        } else {
            SamplingResult.recordAndSample() // Default tracing for other endpoints
        }
    }

    override fun getDescription(): String = "CustomSampler excluding /actuator/health"
}