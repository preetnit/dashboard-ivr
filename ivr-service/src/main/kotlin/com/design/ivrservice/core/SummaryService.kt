package com.design.ivrservice.core

import com.design.ivrservice.entity.SummaryMetrics
import java.time.Instant

interface SummaryService {

    fun getAggregatedSummaryMetrics(clientId: String, startDate: Instant, endDate: Instant): SummaryMetrics

}
