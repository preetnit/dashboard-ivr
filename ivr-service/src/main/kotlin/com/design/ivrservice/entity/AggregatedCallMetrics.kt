package com.design.ivrservice.entity

import org.springframework.data.mongodb.core.mapping.Field

data class AggregatedCallMetrics(
    val clientId: String,
    @Field(name = "avgCallsInQueue") var callsInQueue: Int,
    @Field(value = "avgSL") val sl: Float,
    @Field(value = "avgAbandon") val abandon: Int,
    @Field(value = "avgLongQueueTime") val longQueueTime: Int
)
