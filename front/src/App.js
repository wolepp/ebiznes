import React from 'react';
import { BrowserRouter as Router } from "react-router-dom";
import MyNavbar from "./MyNavbar";
import Routes from "./Routes";
import { Container, Row } from "react-bootstrap";

function App() {

  return (
    <div className="App h-100 w-100">
      <Router>
        <MyNavbar />
        <Container className='h-100'>
          <Row className='h-100'>
            <Routes />
          </Row>
        </Container>
      </Router>
    </div>
  );
}

export default App;
