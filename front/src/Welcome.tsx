import React, {Component} from "react";

interface WelcomeProps {
  text: String
}

type WelcomeState = {
  time: Date
}

class Welcome extends Component<WelcomeProps, WelcomeState> {
  tick() {
    this.setState({
      time: new Date()
    });
  }

  componentWillMount() {
    this.tick();
  }

  componentDidMount() {
    setInterval(() => this.tick(), 1000);
  }

  render() {
    return (
      <div>
        <h1>{this.props.text}</h1>
        <p>Current time is: {this.state.time.toLocaleTimeString()}</p>
      </div>
    )
  }
}

export default Welcome;
