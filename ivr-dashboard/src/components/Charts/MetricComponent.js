import React from "react";
import {
    CardTitle,
    Row,
    Col
} from "reactstrap";

function MetricComponent({ metricsName, metricsValue, icon }) {
    return (
        <Col>
            <Row>
                <Col>
                    <div className="icon-big text-center icon-warning">
                        <i className={icon} />
                    </div>
                </Col>
                <div className="numbers text-center">
                    <p className="card-category" bold>{metricsName}</p>
                    <CardTitle tag="p">{metricsValue}</CardTitle>
                    <p />
                </div>
                <Col>
                </Col>
            </Row>
        </Col>
    );
}

export default MetricComponent;