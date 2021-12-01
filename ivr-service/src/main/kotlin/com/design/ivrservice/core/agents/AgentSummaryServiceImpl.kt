package com.design.ivrservice.core.agents

import com.design.ivrservice.core.export.CSVExportService
import com.design.ivrservice.entity.AgentMetrics
import com.design.ivrservice.entity.AggregatedAgentMetrics
import com.design.ivrservice.exceptions.IVRCSVExportException
import com.design.ivrservice.repository.AgentSummaryRepository
import com.design.ivrservice.utils.getCSVBody
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.ByteArrayInputStream
import java.time.Instant

@Service
class AgentSummaryServiceImpl: AgentSummaryService {

    private val logger = KotlinLogging.logger {}

    @Autowired
    lateinit var agentSummaryRepository: AgentSummaryRepository

    @Autowired
    lateinit var exportService: CSVExportService

    override fun getAgentMetrics(
        clientId: String,
        page: Long,
        size: Long,
        startDate: Instant,
        endDate: Instant
    ): List<AgentMetrics> {
        logger.info { "Getting Agent metrics from db" }
        return agentSummaryRepository.findAgentMetricsByClientIdAndAgentNameAndTimestampBetween(clientId,page, size,
            startDate, endDate)
    }

    override fun getAggregatedAgentMetrics(
        clientId: String,
        startDate: Instant,
        endDate: Instant
    ): AggregatedAgentMetrics {
        logger.info { "Getting Aggregated Agent metrics from db" }
        return agentSummaryRepository.findAggAgentMetricsByClientIdAndTimestampBetween(clientId, startDate, endDate)
    }

    override fun exportAgentMetrics(clientId: String, startDate: Instant, endDate: Instant): ByteArrayInputStream {
        val page: Long = 0
        val size: Long = Long.MAX_VALUE
        val agentMetrics: List<AgentMetrics> =getAgentMetrics(clientId, page, size, startDate, endDate)

        val csvHeader: Array<String> = getAgentMetricsHeaders()
        val csvBody: List<List<String>> = getCSVBody(agentMetrics)

        try {
            return exportService.exportData(csvHeader, csvBody)
        }catch (e: Exception) {
            throw IVRCSVExportException()
        }
    }

    fun getAgentMetricsHeaders(): Array<String> {
        return arrayOf(
            "AgentName","CPH","Answered","AbandonPerct","UnvlPerct","AHT","HoldPerct","AvgACW"
        )
    }
}