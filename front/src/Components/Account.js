import { Button, Col, Container, Row } from "react-bootstrap";
import { useEffect, useState } from "react";
import { getCurrentUser } from "../Services/UserAPI";
import { Link } from "react-router-dom";

const Account = () => {
  const [user, setUser] = useState({
    "id": 1,
    "name": "Steven Johns",
    "email": "Brown_Veum43@example.net",
    "password": "X8QNaXXYtUtkhXg",
    "city": "New Keanu",
    "address": "72880 Zemlak Radial"
  });

  useEffect(() => {
    getCurrentUser()
      .then(data => setUser(data));
  }, []);

  const dataRow = 'mb-3 border-bottom border-light border-3';

  return (
    <Container>

      <Row className='mb-3'>
        <Col>
          <h1>{user.name}</h1>
        </Col>
      </Row>

      <Row className={dataRow}>
        <Col md={{ span: 2 }}>
          <h5>Email</h5>
        </Col>
        <Col md={{ span: 10 }}>
          <h5>{user.email}</h5>
        </Col>
      </Row>

      <Row className={dataRow}>
        <Col md={{ span: 2 }}>
          <h5>City</h5>
        </Col>
        <Col md={{ span: 7 }}>
          <h5>{user.city}</h5>
        </Col>
      </Row>

      <Row>
        <Col md={{ span: 2 }}>
          <h5>Address</h5>
        </Col>
        <Col md={{ span: 7 }}>
          <h5>{user.address}</h5>
        </Col>
      </Row>

      <Row className='mt-5'>
        <Col xs='auto'>
          <Button as={Link} to="/orders">Orders</Button>
        </Col>
        <Col xs='auto'>
          <Button as={Link} to="/returns">Returns</Button>
        </Col>
      </Row>

    </Container>
  );
};

export default Account;
