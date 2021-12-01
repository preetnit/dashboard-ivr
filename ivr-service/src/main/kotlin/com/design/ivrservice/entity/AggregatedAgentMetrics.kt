package com.design.ivrservice.entity

import org.springframework.data.mongodb.core.mapping.Field

data class AggregatedAgentMetrics(
    @Field(name = "avgAHT") var aht: Int,
    @Field(name = "avgConversion") var conversion: Int,
    @Field(name = "avgCSAT") var csat: Float,
    @Field(name = "avgAnswered") var answered: Int,
    var clientId: String
)

