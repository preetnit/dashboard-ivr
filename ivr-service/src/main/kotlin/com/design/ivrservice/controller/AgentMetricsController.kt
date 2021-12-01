package com.design.ivrservice.controller

import com.design.ivrservice.core.agents.AgentSummaryService
import com.design.ivrservice.entity.AgentMetrics
import com.design.ivrservice.entity.AggregatedAgentMetrics
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
@RequestMapping(value = ["/v1/{clientId}/agents/"])
class AgentMetricsController {

    @Autowired
    lateinit var agentSummaryService: AgentSummaryService

    @GetMapping("/agentsMetrics")
    @Operation(
        summary = "Get Agent Summary metrics",
        responses = [ApiResponse(responseCode = "200",
            description = "List Agent Metrics in specified date range",
            content = arrayOf(
                Content(mediaType = "application/json",
                    array = ArraySchema(schema = Schema(implementation = AgentMetrics::class))
                )
            )),
            ApiResponse(responseCode = "401", description = "Unauthorized api call"),
            ApiResponse(responseCode = "405", description = "Validation exception"),
            ApiResponse(responseCode = "500", description = "Server error")]
    )
    fun getCallMetrics(
        @PathVariable("clientId") clientId: String,
        @RequestParam(name = "page", required = false, defaultValue = "0") page: Long,
        @RequestParam(name = "size", required = false, defaultValue = "30") size: Long,
        @RequestParam(name = "startDate", required = true, defaultValue = "2019-12-01T00:02:00.000+00:00") startDate: Instant,
        @RequestParam(name = "endDate", required = true, defaultValue = "2019-12-01T01:02:00.000+00:00") endDate: Instant
    ): ResponseEntity<List<AgentMetrics>> {
        return ResponseEntity.ok(agentSummaryService.getAgentMetrics(clientId, page, size, startDate, endDate))
    }


    @GetMapping("/aggregatedMetrics")
    @Operation(
        summary = "Get Aggregated Agent Summary metrics",
        responses = [ApiResponse(responseCode = "200",
            description = "Get Aggregated Agent Metrics",
            content = arrayOf(
                Content(mediaType = "application/json",
                    array = ArraySchema(schema = Schema(implementation = AggregatedAgentMetrics::class))
                )
            )),
            ApiResponse(responseCode = "401", description = "Unauthorized api call"),
            ApiResponse(responseCode = "405", description = "Validation exception"),
            ApiResponse(responseCode = "500", description = "Server error")]
    )
    fun getAggregatedMetrics(
        @PathVariable("clientId") clientId: String,
        @RequestParam(name = "startDate", required = true, defaultValue = "2019-12-01T00:02:00.000+00:00") startDate: Instant,
        @RequestParam(name = "endDate", required = true, defaultValue = "2019-12-01T01:02:00.000+00:00") endDate: Instant
    ): ResponseEntity<AggregatedAgentMetrics>{
        return ResponseEntity.ok(agentSummaryService.getAggregatedAgentMetrics(clientId,startDate, endDate))
    }

    @GetMapping("/agentsMetrics/exportResults", produces = ["text/csv"])
    @Operation(
        summary = "Export Agent Summary metrics in CSV file",
        responses = [ApiResponse(responseCode = "200",
            description = "List Agent Metrics in specified date range",
            content = arrayOf(
                Content(mediaType = "text/csv",
                    array = ArraySchema(schema = Schema(implementation = AgentMetrics::class))
                )
            )),
            ApiResponse(responseCode = "401", description = "Unauthorized api call"),
            ApiResponse(responseCode = "405", description = "Validation exception"),
            ApiResponse(responseCode = "500", description = "Server error")]
    )
    fun getExportCallMetrics(
        @PathVariable("clientId") clientId: String,
        @RequestParam(name = "startDate", required = true, defaultValue = "2019-12-01T00:02:00.000+00:00") startDate: Instant,
        @RequestParam(name = "endDate", required = true, defaultValue = "2019-12-01T01:02:00.000+00:00") endDate: Instant
    ): ResponseEntity<Resource> {
        val csvFileName = "AgentMetrics.csv"
        val headers = HttpHeaders()
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=${csvFileName}")
        headers.set(HttpHeaders.CONTENT_TYPE, "text/csv")
        val fileInputStream = InputStreamResource(agentSummaryService.exportAgentMetrics(clientId, startDate, endDate))

        return ResponseEntity(
            fileInputStream,
            headers,
            HttpStatus.OK
        )
    }
}