package com.design.ivrservice.repository;

import com.design.ivrservice.entity.AgentMetrics
import com.design.ivrservice.entity.AggregatedAgentMetrics
import com.design.ivrservice.entity.AggregatedCallMetrics
import java.time.Instant

interface AggregatedMetricsRepository {
    fun findAggCallMetricsByClientIdAndTimestampBetween(clientId: String,
                                                         startDate: Instant, endDate: Instant): AggregatedCallMetrics

    fun findAggAgentMetricsByClientIdAndTimestampBetween(clientId: String,
                                                        startDate: Instant, endDate: Instant): AggregatedAgentMetrics

    fun findAgentMetricsByClientIdAndAgentNameAndTimestampBetween(
        clientId: String,
        page: Long, size: Long,
        startDate: Instant, endDate: Instant
    ): List<AgentMetrics>

}
