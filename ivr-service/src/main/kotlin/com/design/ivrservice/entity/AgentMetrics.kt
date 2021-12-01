package com.design.ivrservice.entity

import org.springframework.data.mongodb.core.mapping.Field

data class AgentMetrics(
    var agentName: String,
    @Field(name = "avgCPH") var cph: Int,
    @Field(name = "avgAnswered") var answered: Int,
    @Field(name = "avgAbandonPerct") var abandonPerct: Float,
    @Field(name = "avgUnvlPerct") var unvlPerct: Float,
    @Field(name = "avgAHT") var aht: Int,
    @Field(name = "avgHoldPerct") var holdPerct: Float,
    @Field(name = "avgACW") var avgACW: Int
)
