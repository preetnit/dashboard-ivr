package com.design.ivrservice.entity

data class SummaryMetrics(
    val clientId: String,
    val aht: Int,
    val sl: Float,
    val abandon: Int,
    var callsInQueue: Int,
    val longQueueTime: Int
)
