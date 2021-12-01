package com.design.ivrservice.core.calls

import com.design.ivrservice.entity.AggregatedCallMetrics
import com.design.ivrservice.entity.CallMetrics
import java.time.Instant

interface CallArrivalService {

    fun getCallArrivalsMetrics(clientId: String,
                               page: Int, size: Int,
                               startDate: Instant, endDate: Instant): List<CallMetrics>

    fun getAggregatedCallMetrics(clientId: String,
                                 startDate: Instant, endDate: Instant): AggregatedCallMetrics
}
