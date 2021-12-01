package com.design.ivrservice.utils

import com.design.ivrservice.entity.AgentMetrics

fun getCSVBody(agentMetric: List<AgentMetrics>): List<List<String>>{

    val csvBody = ArrayList<List<String>>()
    agentMetric.forEach {agentMetric ->
        csvBody.add(
            listOf(agentMetric.agentName, agentMetric.cph.toString(),
                agentMetric.answered.toString(), agentMetric.abandonPerct.toString(),
                agentMetric.unvlPerct.toString(), agentMetric.aht.toString(),
                agentMetric.holdPerct.toString(), agentMetric.avgACW.toString()))
    }
    return csvBody
}