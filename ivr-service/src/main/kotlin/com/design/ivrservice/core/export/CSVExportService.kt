package com.design.ivrservice.core.export

import java.io.ByteArrayInputStream

interface CSVExportService {
    fun exportData(agentMetrics: Array<String>, csvBody: List<List<String>>): ByteArrayInputStream
}