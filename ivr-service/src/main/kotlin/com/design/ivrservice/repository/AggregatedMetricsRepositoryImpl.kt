package com.design.ivrservice.repository

import com.design.ivrservice.entity.AgentMetrics
import com.design.ivrservice.entity.AggregatedAgentMetrics
import com.design.ivrservice.entity.AggregatedCallMetrics
import com.design.ivrservice.models.AgentSummary
import com.design.ivrservice.models.CallArrivals
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.aggregation.Aggregation.group
import org.springframework.data.mongodb.core.aggregation.Aggregation.limit
import org.springframework.data.mongodb.core.aggregation.Aggregation.match
import org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation
import org.springframework.data.mongodb.core.aggregation.Aggregation.project
import org.springframework.data.mongodb.core.aggregation.Aggregation.skip
import org.springframework.data.mongodb.core.aggregation.Aggregation.sort
import org.springframework.data.mongodb.core.aggregation.AggregationResults
import org.springframework.data.mongodb.core.query.Criteria
import java.time.Instant


class AggregatedMetricsRepositoryImpl: AggregatedMetricsRepository {

    @Autowired
    lateinit var mongoTemplate: MongoTemplate

    override fun findAggCallMetricsByClientIdAndTimestampBetween(
        clientId: String,
        startDate: Instant,
        endDate: Instant
    ): AggregatedCallMetrics {
        val agg: Aggregation = newAggregation(
            match(Criteria.where("clientId").`is`(clientId)),
            match(Criteria.where("event_timestamp").gte(startDate).lte(endDate)),
            group("clientId")
                .avg("callsInQueue").`as`("avgCallsInQueue")
                .avg("sl").`as`("avgSL")
                .avg("abandon").`as`("avgAbandon")
                .avg("longQueue").`as`("avgLongQueueTime"),
            project("avgCallsInQueue", "avgSL","avgAbandon","avgLongQueueTime" ).and("clientId").previousOperation())

        //Convert the aggregation result into a List
        val groupResults: AggregationResults<AggregatedCallMetrics> =
            mongoTemplate.aggregate(agg, CallArrivals::class.java, AggregatedCallMetrics::class.java)
        val result: List<AggregatedCallMetrics> = groupResults.mappedResults
        return result[0]
    }

    override fun findAggAgentMetricsByClientIdAndTimestampBetween(
        clientId: String,
        startDate: Instant,
        endDate: Instant
    ): AggregatedAgentMetrics {
        val agg: Aggregation = newAggregation(
            match(Criteria.where("clientId").`is`(clientId)),
            match(Criteria.where("event_timestamp").gte(startDate).lte(endDate)),
            group("clientId")
                .avg("aht").`as`("avgAHT")
                .avg("conversion").`as`("avgConversion")
                .avg("csat").`as`("avgCSAT")
                .avg("answered").`as`("avgAnswered"),
            project("avgAHT","avgConversion","avgCSAT","avgAnswered" ).and("clientId").previousOperation())

        //Convert the aggregation result into a List
        val groupResults: AggregationResults<AggregatedAgentMetrics> =
            mongoTemplate.aggregate(agg, AgentSummary::class.java, AggregatedAgentMetrics::class.java)
        val result: List<AggregatedAgentMetrics> = groupResults.mappedResults
        return result[0]
    }

    override fun findAgentMetricsByClientIdAndAgentNameAndTimestampBetween(
        clientId: String,
        page: Long,
        size: Long,
        startDate: Instant,
        endDate: Instant
    ):  List<AgentMetrics>{
        val agg: Aggregation = newAggregation(
            match(Criteria.where("clientId").`is`(clientId)),
            match(Criteria.where("event_timestamp").gte(startDate).lte(endDate)),
            group("agentName")
                .avg("cph").`as`("avgCPH")
                .avg("answered").`as`("avgAnswered")
                .avg("abandonPerct").`as`("avgAbandonPerct")
                .avg("unvlPerct").`as`("avgUnvlPerct")
                .avg("aht").`as`("avgAHT")
                .avg("holdPerct").`as`("avgHoldPerct")
                .avg("acw").`as`("avgACW"),
            project("avgCPH", "avgAnswered", "avgAbandonPerct", "avgUnvlPerct", "avgAHT", "avgHoldPerct","avgACW")
                .and("agentName").previousOperation(),
            skip(size*page),limit(size), sort(Sort.DEFAULT_DIRECTION, "agentName")
        )

        //Convert the aggregation result into a List
        val groupResults: AggregationResults<AgentMetrics> =
            mongoTemplate.aggregate(agg, AgentSummary::class.java, AgentMetrics::class.java)
        val result: List<AgentMetrics> = groupResults.mappedResults
        return result
    }


}