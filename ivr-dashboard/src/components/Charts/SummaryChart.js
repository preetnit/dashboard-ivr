import React from "react";
import {
    Card,
    CardHeader,
    CardBody,
    CardFooter,
    Row,
} from "reactstrap";
import MetricComponent from "./MetricComponent";
import DateRangePicker from '@wojtekmaj/react-daterange-picker/';

function SummaryChart() {
    const [isLoading, setLoading] = React.useState(false);
    const [metrics, setMetrics] = React.useState([]);
    const [summaryMetricsURI, setSummaryMetricsURI] = React.useState('http://localhost:3000/v1/testClientOne/summaryMetrics?startDate=2019-12-01T00%3A02%3A00Z&endDate=2019-12-01T01%3A02%3A00Z');
    const [dateRange, setDateRange] = React.useState([new Date("2019-11-30"), new Date("2019-12-30")]);

    const hostname = "http://localhost:3000"
    const summaryMetricsApi = hostname + "/v1/testClientOne/summaryMetrics"
    const minDate = new Date("2019-11-30")
    const maxDate = new Date("2019-12-30")

    async function fetchMetrics() {
        console.log("Fetching the data", summaryMetricsURI);
        setLoading(true)
        const response = await fetch(summaryMetricsURI);
        const data = await response.json();
        setMetrics(data);
        setLoading(false);
    }
    
    

    const onDateChange = (value) => {
        if (value != null) {
            console.log("new date", value)
            console.log("current date ", dateRange)
            setDateRange(value)
            console.log("updated date range ", dateRange)

            let searchParams = new URLSearchParams();
            searchParams.append("startDate", dateRange[0].toISOString());
            searchParams.append("endDate", dateRange[1].toISOString());
            searchParams.toString();
            console.log("updated search param", searchParams)

            setSummaryMetricsURI(summaryMetricsApi + "?" + searchParams)
            
            console.log("updated summaryMetricsApi =>", summaryMetricsApi)
        }
    }

    React.useEffect(() => {
        console.log("Date rangee", dateRange[0], dateRange[1])
        fetchMetrics()
    }, [summaryMetricsURI]);

    String.prototype.toHHMMSS = function () {
        var sec_num = parseInt(this, 10); // don't forget the second param
        var hours   = Math.floor(sec_num / 3600);
        var minutes = Math.floor((sec_num - (hours * 3600)) / 60);
        var seconds = sec_num - (hours * 3600) - (minutes * 60);
    
        if (hours   < 10) {hours   = "0"+hours;}
        if (minutes < 10) {minutes = "0"+minutes;}
        if (seconds < 10) {seconds = "0"+seconds;}
        return hours+':'+minutes+':'+seconds;
    }

    var lqt = ""+metrics.longQueueTime
    var aht = ""+metrics.aht
    var longQueueTime = lqt.toHHMMSS()
    var ahtT = aht.toHHMMSS()

    const value = 100
    return (
        <Card className="card-stats">
            <CardHeader>
                
            </CardHeader>
            <CardBody>
                <Row>
                    <MetricComponent metricsName="SL%" icon="nc-icon nc-globe text-success" value="100" metricsValue={metrics.sl} ></MetricComponent>
                    <MetricComponent metricsName="Abandon%" icon="nc-icon nc-globe text-warning" value="100" metricsValue={metrics.abandon}></MetricComponent>
                    <MetricComponent metricsName="AHT" icon="nc-icon nc-globe text-danger" value="100" metricsValue={ahtT}></MetricComponent>
                    <MetricComponent metricsName="InQueue" icon="nc-icon nc-globe text-success" value="100" metricsValue={metrics.callsInQueue}></MetricComponent>
                    <MetricComponent metricsName="LongQueue" icon="nc-icon nc-globe text-success" value="100" metricsValue={longQueueTime}></MetricComponent>
                </Row>
            </CardBody>
            <CardFooter>
                <hr />
                <div className="stats">
                    <DateRangePicker class="export-bar-item"
                    onChange={onDateChange}
                    value={dateRange}
                    minDate={minDate}
                    maxDate={maxDate}
                    closeCalendar="false"
                />
                </div>
            </CardFooter>
        </Card>
    )
}


export default SummaryChart;