import React, { Component } from 'react';

interface AgentMetrics {
  clientId:	string
  agentName:string
  cph: string
  answered: string
  abandonPerct: string
  unvlPerct: string
  aht: string
  holdPerct: string
  avgACW: string

}

interface AgentMetricsListProps {
}

interface AgentMetricsListState {
  metrics: Array<AgentMetrics>;
  isLoading: boolean;
}

class AgentMetricsList extends Component<AgentMetricsListProps, AgentMetricsListState> {

  constructor(props: AgentMetricsListProps) {
    super(props);

    this.state = {
      metrics: [],
      isLoading: false
    };
  }

  async componentDidMount() {
    this.setState({isLoading: true});

    const response = await fetch('http://localhost:3000/v1/testClientOne/agents/agentsMetrics?page=2&size=5&startDate=2019-12-01T00%3A02%3A00.000%2B00%3A00&endDate=2019-12-01T03%3A02%3A00.000%2B00%3A00', {
     // headers: {
     //   Authorization: 'Bearer ' + await this.props.auth.getAccessToken()
     // }
    });
    const data = await response.json();
    this.setState({metrics: data, isLoading: false});
  }

  render() {
    const {metrics, isLoading} = this.state;

    if (isLoading) {
      return <p>Loading...</p>;
    }

    return (
      <div>
        <h2>Agent Metrics List</h2>
        {metrics.map((metrics: AgentMetrics) =>
          <div key={metrics.clientId}>
            {metrics.agentName}<br/>
          </div>
        )}
      </div>
    );
  }
}

export default AgentMetricsList;