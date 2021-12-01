Mongo


db.callArrivals.find()

db.callArrivals.find().forEach(function(item){
    if (typeof(item.event_timestamp) == "string"){
        print(item.event_timestamp);
        item.event_timestamp = new ISODate(item.event_timestamp);
        db.callArrivals.save(item);
    }
})

db.callArrivals.find({
	event_timestamp: { $lte: new ISODate('2019-12-29') }
});

db.callArrivals.find({
	event_timestamp: { $lte: new ISODate('2019-12-01T00:04:00Z') }
});

db.callArrivals.find({ 
    clientId: "testClientOne",
	event_timestamp: { 
		$gt : ISODate("2019-12-01T00:02:00.000+00:00"), 
		$lt : ISODate("2019-12-01T00:31:00.000+00:00")
	}
})


db.callArrivals.find({ 
    "event_timestamp" : { 
        "$gt" : { "$date" : "2019-12-01T00:02:00Z"}, 
        "$lt" : { "$date" : "2019-12-01T00:31:00Z"}
    }
})

db.callArrivals.find({ 
    clientId: "testClientOne",
	"event_timestamp" : { 
		"$gt" : ISODate("2019-12-01T00:02:00Z"), 
		"$lt" : ISODate("2019-12-01T00:31:00Z")
	}
})

"_class" : "com.design.ivrservice.models.CallArrivals"

db.callArrivals.find().forEach(function(item){
    item._class = "com.design.ivrservice.models.CallArrivals"
    db.callArrivals.save(item);
    print(item._class);
})


db.callArrivals.find({
    "clientId": "testClientOne", 
    "event_timestamp": {
        "$gt": ISODate("2019-12-01T00:02:00Z"),
        "$lt": ISODate("2019-12-01T00:31:00Z")
    }
})

db.callArrivals.find().forEach(function(item){
    print("["+item.event_timestamp);
    item.event_timestamp = new Date(item.event_timestamp);
    print("->"+item.event_timestamp);
    item._class = "com.design.ivrservice.models.CallArrivals"
    print("->"+item._class+"]");
    db.callArrivals.save(item);
})


db.callArrivals.aggregate(
    {
	$match : {
        clientId: "testClientOne",
        event_timestamp: {
        "$gt": ISODate("2019-12-01T00:02:00Z"),
        "$lt": ISODate("2019-12-01T00:31:00Z")
    }}
    },
    { 
	$group : {_id:"$clientId", avgCallsInQueue: {$avg: "$CallsInQueue"}, avgSL: {$avg: "$SL"}, avgAbandon: {$avg: "$Abandon"}, avgQueue: {$avg: "$LongQueueTime"} }
    }
);

=TEXT(A1,"yyyy-mm-ddThh:MM:ss")

db.agentSummary.find().forEach(function(item){
    print("["+item.event_timestamp);
    item.event_timestamp = new Date(item.event_timestamp);
    print("->"+item.event_timestamp);
    item._class = "com.design.ivrservice.models.AgentSummary"
    print("->"+item._class+"]");
    db.agentSummary.save(item);
})

db.agentSummary.aggregate(
    {
	$match : {
        clientName: "testClientOne",
        event_timestamp: {
        "$gt": ISODate("2019-12-01T00:02:00Z"),
        "$lt": ISODate("2019-12-01T00:31:00Z")
    }}
    },
    { 
	$group : {_id:"$clientName", avgAHT: {$avg: "$AHT"}, avgConversion: {$avg: "$Conversion"}, avgCSAT: {$avg: "$CSAT"}, avgAnswered: {$avg: "$Answered"} }
    }
);

{"aggregate": "agentSummary", "pipeline": [
    {"$match": {"clientName": "testClientOne"}}, 
    {"$match": {"event_timestamp": {"$gte": {"$date": "2019-12-01T00:02:00Z"}, "$lte": {"$date": "2019-12-01T01:02:00Z"}}}}, 
    {"$group": {"_id": "$clientName", "avgAHT": {"$avg": "$AHT"}, "avgConversion": {"$avg": "$Conversion"}, "avgCSAT": {"$avg": "$CSAT"}, "avgAnswered": {"$avg": "$Answered"}}}


db.agentSummary.aggregate(
    {
	$match : {
        clientName: "testClientOne",
        event_timestamp: {
        "$gt": ISODate("2019-12-01T00:02:00Z"),
        "$lt": ISODate("2019-12-01T00:31:00Z")
    }}
    },
    { 
	$group : {  _id:{clientId:"$clientName", agentName: "$AgentName" },
                avgAHT: {$avg: "$AHT"},
                avgCPH: {$avg: "$CPH"}, 
                avgAnswered: {$avg: "$Answered"}, 
                avgAbandonPerct: {$avg: "$AbandonPerct"},
                avgUnvlPer: {$avg: "$UnvlPer"},
                avgHoldPerct: {$avg: "$HoldPerct"}, 
                avgAvgACW: {$avg: "$AvgACW"} }
    }
);

{ "aggregate" : "__collection__", "pipeline" : [
    { "$match" : { "clientId" : "testClientOne"}}, 
    { "$match" : { "event_timestamp" : { "$gte" : { "$date" : "2019-12-01T00:02:00Z"}, "$lte" : { "$date" : "2019-12-01T01:02:00Z"}}}}, 
    { "$group" : { "_id" : { "agentName" : "$agentName", "clientId" : "$clientId"}, "avgCPH" : { "$avg" : "$cph"}, "avgAnswered" : { "$avg" : "$answered"}, "avgAbandonPerct" : { "$avg" : "$abandonPerct"}, "avgUnvlPer" : { "$avg" : "$unvlPerct"}, "avgAHT" : { "$avg" : "$aht"}, "avgHoldPerct" : { "$avg" : "$holdPerct"}, "avgACW" : { "$avg" : "$acw"}}}, { "$project" : { "avgCPH" : 1, "avgAnswered" : 1, "avgAbandonPerct" : 1, "avgUnvlPer" : 1, "avgAHT" : 1, "avgHoldPerct" : 1, "avgACW" : 1, "_id" : 0, "agentName" : "$_id"}}]}