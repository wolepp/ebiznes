import { Col, Container, Row } from "react-bootstrap";
import { useEffect, useState } from "react";
import { getReturns } from "../Services/UserAPI";
import ReturnBox from "./ReturnBox";

const Returns = () => {
  const [returns, setReturns] = useState([])

  useEffect(() => {
    getReturns()
      .then(data => setReturns(data));
  }, []);


  return (
    <Container>
      <Row className='mt-5'>
        <Col>
          <h1>Returns</h1>
        </Col>
      </Row>

      {returns.length ?
        <Row>
          <Col>
            {returns.map(r => (
              <ReturnBox key={r.id} aReturn={r} />
            ))}
          </Col>
        </Row>
        :
        <h4>No returns available</h4>
      }

    </Container>
  );
};

export default Returns;
