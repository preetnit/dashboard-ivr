package com.design.ivrservice.controller

import com.design.ivrservice.core.calls.CallArrivalService
import com.design.ivrservice.entity.AggregatedCallMetrics
import com.design.ivrservice.entity.CallMetrics
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
@RequestMapping(value = ["/v1/{clientId}/calls/"])
class CallMetricsController {

    @Autowired
    lateinit var callArrivalService: CallArrivalService

    @GetMapping("/callMetrics")
    @Operation(
        summary = "Get Call Arrival metrics",
        responses = [ApiResponse(responseCode = "200",
            description = "List Call Metrics in specified date range",
            content = arrayOf(
                Content(mediaType = "application/json",
                array = ArraySchema(schema = Schema(implementation = CallMetrics::class))
                )
            )),
            ApiResponse(responseCode = "401", description = "Unauthorized api call"),
            ApiResponse(responseCode = "405", description = "Validation exception"),
            ApiResponse(responseCode = "500", description = "Server error")]
    )
    fun getCallMetrics(
        @PathVariable("clientId") clientId: String,
        @RequestParam(name = "page", required = false, defaultValue = "0") page: Int,
        @RequestParam(name = "size", required = false, defaultValue = "30") size: Int,
        @RequestParam(name = "startDate", required = true, defaultValue = "2019-12-01T00:02:00.000+00:00") startDate: Instant,
        @RequestParam(name = "endDate", required = true, defaultValue = "2019-12-01T01:02:00.000+00:00") endDate: Instant
    ): ResponseEntity<List<CallMetrics>> {
        return ResponseEntity.ok(callArrivalService.getCallArrivalsMetrics(clientId, page, size, startDate, endDate))
    }


    @GetMapping("/aggregatedMetrics")
    @Operation(
        summary = "Get Aggregated Call Arrival metrics",
        responses = [ApiResponse(responseCode = "200",
            description = "Get Aggregated Call Metrics",
            content = arrayOf(
                Content(mediaType = "application/json",
                    array = ArraySchema(schema = Schema(implementation = AggregatedCallMetrics::class))
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
    ): ResponseEntity<AggregatedCallMetrics>{
        return ResponseEntity.ok(callArrivalService.getAggregatedCallMetrics(clientId,startDate, endDate))
    }
}