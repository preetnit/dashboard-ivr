package com.design.ivrservice.controller

import com.design.ivrservice.core.SummaryService
import com.design.ivrservice.entity.SummaryMetrics
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
@RequestMapping(value = ["/v1/{clientId}/"])
class SummaryMetricsController {

    @Autowired
    lateinit var summaryService: SummaryService

    @GetMapping("/summaryMetrics")
    @Operation(
        summary = "Get overview of all metrics",
        responses = [ApiResponse(responseCode = "200",
            description = "List different Metrics in specified date range",
            content = arrayOf(
                Content(mediaType = "application/json",
                array = ArraySchema(schema = Schema(implementation = SummaryMetrics::class))
                )
            )),
            ApiResponse(responseCode = "401", description = "Unauthorized api call"),
            ApiResponse(responseCode = "405", description = "Validation exception"),
            ApiResponse(responseCode = "500", description = "Server error")]
    )
    fun getSummaryMetrics(
        @PathVariable("clientId") clientId: String,
        @RequestParam(name = "startDate", required = true, defaultValue = "2019-12-01T00:02:00.000+00:00") startDate: Instant,
        @RequestParam(name = "endDate", required = true, defaultValue = "2019-12-01T01:02:00.000+00:00") endDate: Instant
    ): ResponseEntity<SummaryMetrics>{
        return ResponseEntity.ok(summaryService.getAggregatedSummaryMetrics(clientId,startDate, endDate))
    }
}