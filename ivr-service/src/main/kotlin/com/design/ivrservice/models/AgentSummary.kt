package com.design.ivrservice.models

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.format.annotation.DateTimeFormat
import java.util.Date

@Document
data class AgentSummary(
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Field(name = "event_timestamp") var timestamp: Date,
    @Field(name = "AgentName") var agentName: String,
    @Field(name = "CPH") var cph: Int,
    @Field(name = "Answered") var answered: Int,
    @Field(name = "AbandonPerct") var abandonPerct: Float,
    @Field(name = "UnvlPerct") var unvlPerct: Float,
    @Field(name = "AHT") var aht: Int,
    @Field(name = "HoldPerct") var holdPerct: Float,
    @Field(name = "AvgACW") var acw: Int,
    @Field(name = "Conversion") var conversion: Int,
    @Field(name = "CSAT") var csat: Float,
    @Field(name = "clientName") var clientId: String
)