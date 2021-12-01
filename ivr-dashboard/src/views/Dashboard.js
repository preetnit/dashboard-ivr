import React from "react";
// react plugin used to create charts
import { Line } from "react-chartjs-2";
import GaugeChart from 'react-gauge-chart'
// reactstrap components
import {
  Card,
  CardHeader,
  CardBody,
  CardFooter,
  CardTitle,
  Row,
  Col
} from "reactstrap";
// core components
import {
  dashboardNASDAQChart,
} from "variables/charts.js";
import TableChart from "components/Charts/TableChart";
import SummaryChart from "components/Charts/SummaryChart";
import LineChart from "components/Charts/LineChart";

function Dashboard() {
  return (
    <>
      <div className="content">
        <Row>
          <Col>
            <SummaryChart></SummaryChart>
          </Col>
        </Row>
        <Row>
          <Col md="4">
            <Card>
              <CardHeader>
                <CardTitle tag="h5">Gauges</CardTitle>
                <p className="card-category">Today</p>
              </CardHeader>
              <CardBody>
                <GaugeChart id="gauge-chart1"
                  animate={true}
                  nrOfLevels={20}
                  percent={0.86}
                  colors={["#ff0000", "#00ff00"]}
                  arcWidth={0.3}
                  textColor={"#00000"}
                  height={250}
                />
              </CardBody>
              <CardFooter>
                <hr />
                <div className="stats">
                </div>
              </CardFooter>
            </Card>
          </Col>
          <Col md="8">
            <Card className="card-chart">
              <CardHeader>
                <CardTitle tag="h5">CALL ARRIVALS</CardTitle>
                <p className="card-category">Today</p>
              </CardHeader>
              <CardBody>
                <Line
                  data={dashboardNASDAQChart.data}
                  options={dashboardNASDAQChart.options}
                  width={400}
                  height={100}
                />
              </CardBody>
              <CardFooter>
                <div className="chart-legend">
                  <i className="fa fa-circle text-info" /> Agent Waiting{" "}
                  <i className="fa fa-circle text-warning" /> Calls In Queue{" "}
                  <i className="fa fa-circle .text-danger" /> Agents On Call
                </div>
                <hr />
                <div className="card-stats">
                </div>
              </CardFooter>
            </Card>
          </Col>
        </Row>
        <Row>
          <Col md="4">
            <Card>
              <CardHeader>
                <CardTitle tag="h5">Gauges</CardTitle>
                <p className="card-category">Today</p>
              </CardHeader>
              <CardBody>
                <GaugeChart id="gauge-chart1"
                  animate={true}
                  nrOfLevels={20}
                  percent={0.86}
                  colors={["#00ff00", "#ff0000"]}
                  arcWidth={0.3}
                  textColor={"#00000"}
                />
              </CardBody>
              <CardFooter>
                <hr />
                <div className="stats">
                </div>
              </CardFooter>
            </Card>
          </Col>
          <Col>
            <TableChart name="AGENT SUMMARY"></TableChart>
          </Col>
        </Row>
        <Row>
          <LineChart></LineChart>
        </Row>
      </div>
    </>
  );
}

export default Dashboard;
