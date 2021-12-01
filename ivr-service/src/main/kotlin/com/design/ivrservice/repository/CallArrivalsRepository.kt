package com.design.ivrservice.repository

import com.design.ivrservice.entity.CallMetrics
import com.design.ivrservice.models.CallArrivals
import org.springframework.data.domain.PageRequest
import org.springframework.data.mongodb.repository.MongoRepository
import java.time.Instant

interface CallArrivalsRepository : MongoRepository<CallArrivals, String>, AggregatedMetricsRepository {

    fun findByClientId(clientId: String): List<CallArrivals>

    fun findByClientId(clientId: String, pageable: PageRequest): List<CallArrivals>

    fun findByClientIdAndTimestampBetween(clientId: String,
                                          startDate: Instant, endDate: Instant,
                                          pageable: PageRequest): List<CallMetrics>

}