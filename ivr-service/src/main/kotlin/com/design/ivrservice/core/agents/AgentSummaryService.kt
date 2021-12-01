package com.design.ivrservice.core.agents

import com.design.ivrservice.entity.AgentMetrics
import com.design.ivrservice.entity.AggregatedAgentMetrics
import java.io.ByteArrayInputStream
import java.time.Instant

interface AgentSummaryService {

    fun getAgentMetrics(clientId: String, page: Long, size: Long, startDate: Instant, endDate: Instant): List<AgentMetrics>

    fun getAggregatedAgentMetrics(clientId: String, startDate: Instant, endDate: Instant): AggregatedAgentMetrics

    fun exportAgentMetrics(clientId: String, startDate: Instant, endDate: Instant): ByteArrayInputStream
}