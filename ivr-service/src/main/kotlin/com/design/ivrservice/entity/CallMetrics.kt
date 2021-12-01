package com.design.ivrservice.entity

import org.springframework.data.mongodb.core.mapping.Field
import java.util.Date

data class CallMetrics(
    val clientId: String,
    @Field(value = "event_timestamp") val timestamp: Date,
    @Field(value = "AgentWaiting") val agentWaiting: Int,
    @Field(value = "CallsInQueue") val callsInQueue: Int,
    @Field(value = "AgentsOnCall") val agentsOnCall: Int
)

