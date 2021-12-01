package com.design.ivrservice.repository

import com.design.ivrservice.models.AgentSummary
import org.springframework.data.mongodb.repository.MongoRepository

interface AgentSummaryRepository : MongoRepository<AgentSummary, String>, AggregatedMetricsRepository {
    fun findByClientId(clientId: String)
}