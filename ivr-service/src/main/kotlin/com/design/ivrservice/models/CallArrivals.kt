package com.design.ivrservice.models

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.format.annotation.DateTimeFormat
import java.util.Date

@Document(collection = "callArrivals")
data class CallArrivals(
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Field(name = "event_timestamp") var timestamp: Date,
    @Field(name = "AgentWaiting") var agentWaiting: Int,
    @Field(name = "CallsInQueue") var callsInQueue: Int,
    @Field(name = "AgentsOnCall") var agentsOnCall: Int,
    @Field(name = "SL") var sl: Float,
    @Field(name = "Abandon") var abandon: Int,
    @Field(name = "LongQueueTime") var longQueue: Int,
    @Field var clientId: String
)