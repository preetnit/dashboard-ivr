Design

Design Q
1. How to know the best practices
2. Spring
    1. Should I use docker by default?
    2. Can we use Kotlin by default? {verbosing}
    3. Should I include unit test & integration tests by default? {depends on timeframe} - unit
    4. Monitoring apis by default? - true
3. React
    1. Should I ask gauge/graphs should look exactly the same or it can have variations ?  or can we ask for exact plugins directly
4. Data Model
    1. Should I update all threshold in single go or separate the apis?
    2. Naming convention of metrics in data  model like CSAT, AbandonPerct 


Hi Ashwin, had few questions 
1. What is the time range for top metrics bar?
2. What is Auto in Gauge view?
3. How to decide threshold for each metrics? - for color encodings
    1. should we expose api ? or
    2. set conf in UI ?
4. Should we calculate aggregated metrics (like  CPH, ) or directly expect data in excels?
5. How many  maximum agents are expected for any client? To decide export functionality
6. Should I add multi-tenancy?

Requirement
- Authentication is required
- Stack : React, Spring boot, Mongo
- https://www.betterbuys.com/wp-content/uploads/2020/03/Five9-2.png
- Export table data


Assumptions
- Ingestion is already done
- Time series of call summary is based on minutes and consistent

LB    /v1/ivrservices/
GET /v1/clientId/clientId/calls/callMetrics
GET /v1/clientId/clientId/calls/aggregatedCallMetrics
GET /v1/clientId/clientId/agents/agentMetrics
GET /v1/clientId/clientId/agents/aggregatedAgentMetrics

Interfaces
1. callArrivalsMetrics(startDate, endDate)
2. aggregatedCallMetrics(startDate, endDate)
3. agentSummaryMetrics(startDate, endDate)
4. aggregatedAgentMetrics(startDate, endDate)
5. allMetricsThresholdConf(metricsObjWithThreshold)
6. exportAgentMetrics()


Data Model
1. CallArrival: timestamp, AgentWaiting, CallsInQueue, AgentsOnCall, SL, Abandon, LongQueueTime
2. AgentSummary: timestamp, AgentName, CPH(calls per hour), Answered, AbandonPerct, UnvlPer, AHT(Average Handling time), HoldPerct, AvgACW(After call work), Conversion, CSAT
3. User: Username, client, accountId, 
4. Thresholds:  CPH, Answered, AbandonPerct, UnvlPer, AHT, HoldPerct, AvgACW, InQueue, LongQueue, SL, Abandon, Conversion, CSAT
5. Client: name, accountId, CallsInQueue

Response
1. callMetrics : clientId, timestamp, AgentWaiting, CallsInQueue, AgentsOnCall
2. aggCallMetrics: clientId, SL, Abandon, LongQueueTime
3. agentMetrics: clientId, AgentName, CPH, Answered, AbandonPerct, UnvlPer, AHT, HoldPerct, AvgACW
4. aggAgentMetrics: clientId, AHT, Conversion, CSAT, Answered


High Level Design
	client -> LB/Reverse Proxy ->  App -> DB 

- Sharding
- Traffic 
- Dashboard user usage
- Read 
- Spring boot vs user requests


Implementation
1. Spring boot v2.5.6
2. MongoDb v4.4.3
3. React v17.0.2


MongoDB
mongoimport --db ivr --collection callArrivals --type csv --headerline --file /Users/harpreet.s/Git/learning/data/CallArrival.csv 
mongoimport --db ivr --collection agentSummary --type csv --headerline --file /Users/harpreet.s/Git/learning/data/AgentSummary.csv



Links
1. MVC https://www.geeksforgeeks.org/mvc-design-pattern/
2. React Gauge https://www.npmjs.com/package/react-gauge-chart
3. Area charts https://recharts.org/en-US/
https://plotly.com/javascript/filled-area-plots/
https://react-google-charts.com/gauge-chart#simple-example
4. Mongo https://www.tutorialspoint.com/mongodb/mongodb_drop_collection.htm
5. Swagger https://www.baeldung.com/spring-rest-openapi-documentation
6. Okta https://github.com/okta/okta-spring-boot
7. React realtime fetch of data: https://developer.okta.com/blog/2018/09/25/spring-webflux-websockets-react
8. Mono vs Flux https://dimitr.im/difference-between-mono-and-flux
9. Mongo keywords for query methods https://docs.spring.io/spring-data/mongodb/docs/1.2.0.RELEASE/reference/html/mongo.repositories.html
10. Spring Mongo aggregation https://mkyong.com/mongodb/spring-data-mongodb-aggregation-grouping-example
11. Export to csv https://springhow.com/spring-boot-export-to-csv/, https://codeburst.io/returning-csv-content-from-an-api-in-spring-boot-63ea82bbcf0f
12. CSS Tricks https://css-tricks.com/snippets/css/a-guide-to-flexbox/
13. React basics https://www.twilio.com/blog/react-choose-functional-components



Curl
http://localhost:8080/v1/testClientOne/calls/callMetrics?page=0&size=30&startDate=2019-12-01T00%3A02%3A00.000%2B00%3A00&endDate=2019-12-01T01%3A02%3A00.000%2B00%3A00

http://localhost:8080/v1/testClientOne/agents/agentsMetrics?page=0&size=30&startDate=2019-12-01T00%3A02%3A00.000%2B00%3A00&endDate=2019-12-01T01%3A02%3A00.000%2B00%3A00

http://localhost:8080/v1/testClientOne/agents/agentsMetrics/exportResults?page=0&size=30&startDate=2010-12-01T00%3A02%3A00.000%2B00%3A00&endDate=2090-12-01T01%3A02%3A00.000%2B00%3A00