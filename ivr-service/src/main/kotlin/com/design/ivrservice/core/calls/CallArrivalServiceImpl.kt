package com.design.ivrservice.core.calls

import com.design.ivrservice.entity.AggregatedCallMetrics
import com.design.ivrservice.entity.CallMetrics
import com.design.ivrservice.repository.CallArrivalsRepository
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.time.Instant


@Service
class CallArrivalServiceImpl: CallArrivalService {

    private val logger = KotlinLogging.logger {}

    @Autowired
    lateinit var callArrivalsRepository: CallArrivalsRepository

    override fun getCallArrivalsMetrics(
        clientId: String,
        page: Int,
        size: Int,
        startDate: Instant,
        endDate: Instant
    ): List<CallMetrics> {
        logger.info { "Getting Call metrics from db" }
        return callArrivalsRepository.findByClientIdAndTimestampBetween(clientId, startDate, endDate, PageRequest.of(page, size))
    }

    override fun getAggregatedCallMetrics(
        clientId: String,
        startDate: Instant,
        endDate: Instant
    ): AggregatedCallMetrics {
        logger.info { "Getting Aggregated Call metrics from db" }
        return callArrivalsRepository.findAggCallMetricsByClientIdAndTimestampBetween(clientId, startDate, endDate)
    }
}