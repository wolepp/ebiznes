import { Col, Container, Row } from "react-bootstrap";
import { useEffect, useState } from "react";
import { getOrders } from "../Services/UserAPI";
import OrderBox from "./OrderBox";

const Orders = () => {
  const [orders, setOrders] = useState([])

  useEffect(() => {
    getOrders()
      .then(data => setOrders(data));
  }, []);


  return (
    <Container>
      <Row className='mt-5'>
        <Col>
          <h1>Orders</h1>
        </Col>
      </Row>
      <Row>
        <Col>
          {orders.map(o => (
            <OrderBox key={o.id} order={o} />
          ))}
        </Col>
      </Row>
    </Container>
  );
};

export default Orders;
