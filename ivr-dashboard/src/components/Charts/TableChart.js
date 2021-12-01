import React from "react";
import {
  Card,
  CardHeader,
  CardBody,
  CardFooter,
  CardTitle,
  Table
} from "reactstrap";
import DateRangePicker from '@wojtekmaj/react-daterange-picker/';

function TableChart(props) {
  const [isLoading, setLoading] = React.useState(false);
  const [metrics, setMetrics] = React.useState([]);
  const [agentMetricsURI, setAgentMetricsURI] = React.useState('http://localhost:3000/v1/testClientOne/agents/agentsMetrics?page=0&size=7&startDate=2019-12-01T00%3A02%3A00Z&endDate=2019-12-01T01%3A02%3A00Z');
  const [download, setDownload] = React.useState(false);
  const [dateRange, setDateRange] = React.useState([new Date("2019-11-30"), new Date("2019-12-30")]);
  const [exportApiURI, setExportApiURI] = React.useState('http://localhost:8080/v1/testClientOne/agents/agentsMetrics/exportResults?page=0&size=7&startDate=2019-12-01T00%3A02%3A00Z&endDate=2019-12-01T01%3A02%3A00Z');

  const hostname = "http://localhost:3000"
  const agentMetricsApi = hostname + "/v1/testClientOne/agents/agentsMetrics"
  const exportApi = "http://localhost:8080/v1/testClientOne/agents/agentsMetrics/exportResults"
  const minDate = new Date("2019-11-30")
  const maxDate = new Date("2019-12-30")

  //let exportApiURI = "http://localhost:8080/v1/testClientOne/agents/agentsMetrics/exportResults?page=0&size=7&startDate=2019-12-01T00%3A02%3A00Z&endDate=2019-12-01T01%3A02%3A00Z"

  const onDateChange = (value) => {
    if (value != null) {
      console.log("new date", value)
      console.log("current date ", dateRange)
      setDateRange(value)
      console.log("updated date range ", dateRange)

      let searchParams = new URLSearchParams();
      searchParams.append("page", "0");
      searchParams.append("size", "7");
      searchParams.append("startDate", dateRange[0].toISOString());
      searchParams.append("endDate", dateRange[1].toISOString());
      searchParams.toString();
      console.log("updated search param", searchParams)

      setAgentMetricsURI(agentMetricsApi + "?" + searchParams)
      setExportApiURI(exportApi + "?" + searchParams)

      console.log("updated agentMetricsApi =>", agentMetricsApi)
      console.log("updated exportApiURI =>", exportApiURI)
    }
  }

  async function fetchMetrics() {
    console.log("Fetching the data", agentMetricsURI);
    setLoading(true)
    const response = await fetch(agentMetricsURI);
    const data = await response.json();
    setMetrics(data);
    setLoading(false);
  }

  async function exportResults() {
    console.log("Exporting the data", exportApiURI);
    await fetch(exportApiURI, {
      headers: {
        'content-type': 'text/csv;charset=UTF-8'
      }
    }).catch(function () {
      console.log("error");
    });
  }

  React.useEffect(() => {
    fetchMetrics()
  }, [agentMetricsURI, exportApiURI]);


  return (
    <Card>
      <CardHeader>
        <CardTitle tag="h4">{props.name}</CardTitle>
      </CardHeader>
      <CardBody>
        <Table responsive>
          <thead className="text-primary">
            <tr>
              <th>Agents</th>
              <th>CPH</th>
              <th>Answered</th>
              <th>Abandon %</th>
              <th>Unvl%</th>
              <th>AHT</th>
              <th>Hold%</th>
              <th>Avg ACW</th>
            </tr>
          </thead>
          <tbody>
            {isLoading ? (<p>Loading...</p>) :
              [metrics.length === 0 ? (<p>No Data Found</p>) :
                (metrics.map((metrics) =>
                  <tr>
                    <td>{metrics.agentName}</td>
                    <td>{metrics.cph}</td>
                    <td>{metrics.answered}</td>
                    <td>{metrics.abandonPerct}</td>
                    <td>{metrics.unvlPerct}</td>
                    <td>{metrics.aht}</td>
                    <td>{metrics.holdPerct}</td>
                    <td>{metrics.avgACW}</td>
                  </tr>))]
            }
          </tbody>
        </Table>
      </CardBody>
      <CardFooter>
        <hr />
        <div className="card-stats">
          <div class="export-bar">
            <DateRangePicker class="export-bar-item"
              onChange={onDateChange}
              value={dateRange}
              minDate={minDate}
              maxDate={maxDate}
              closeCalendar="false"
            />
            <a href={exportApiURI} class="export-bar-item" target="_blank">
              <img class="img-export" border="0" alt="..." src={require("assets/img/export-file.png").default} width="40" height="40" />
            </a>
          </div>
        </div>
      </CardFooter>
    </Card>
  );
}

export default TableChart;