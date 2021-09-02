import React from 'react';
import {BrowserRouter as Router, Link, Route, Switch} from "react-router-dom";
import MyNavbar from "./MyNavbar";
import Routes from "./Routes";

function App() {
  return (
    <div className="App">
      <Router>
        <MyNavbar />
        <Routes />
      </Router>
    </div>
  );
}

export default App;
