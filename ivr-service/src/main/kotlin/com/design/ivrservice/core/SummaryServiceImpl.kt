package com.design.ivrservice.core

import com.design.ivrservice.core.agents.AgentSummaryService
import com.design.ivrservice.core.calls.CallArrivalService
import com.design.ivrservice.entity.AggregatedAgentMetrics
import com.design.ivrservice.entity.AggregatedCallMetrics
import com.design.ivrservice.entity.SummaryMetrics
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Instant


@Service
class SummaryServiceImpl: SummaryService {

    private val logger = KotlinLogging.logger {}

    @Autowired
    lateinit var callArrivalService: CallArrivalService

    @Autowired
    lateinit var agentSummaryService: AgentSummaryService

    override fun getAggregatedSummaryMetrics(
        clientId: String,
        startDate: Instant,
        endDate: Instant
    ): SummaryMetrics {
        logger.info { "Getting Summary metrics" }
        var agentMetrics: AggregatedAgentMetrics = agentSummaryService.getAggregatedAgentMetrics(clientId,startDate, endDate)
        var callMetrics: AggregatedCallMetrics = callArrivalService.getAggregatedCallMetrics(clientId,startDate, endDate)

        return SummaryMetrics(clientId = callMetrics.clientId, sl = callMetrics.sl,
            abandon = callMetrics.abandon, aht= agentMetrics.aht,
            callsInQueue = callMetrics.callsInQueue, longQueueTime = callMetrics.callsInQueue)
    }
}