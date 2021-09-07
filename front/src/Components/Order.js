import { Col, Container, Row } from "react-bootstrap";
import { useContext, useEffect, useState } from "react";
import { storeContext } from "../storeContext";
import { useParams } from "react-router-dom";
import { getOrder } from "../Services/OrderAPI";

const Order = () => {
  const store = useContext(storeContext);
  const [order, setOrder] = useState({});
  const { id } = useParams();

  useEffect(() => {
    getOrder(id)
      .then(data => setOrder(data));
  }, []);


  return (
    <Container>

      <Row className='mt-3 mb-5 justify-content-between'>
        <Col>
          <h2>Order #{order.id}</h2>
        </Col>
      </Row>

      {Object.entries(order).map(([key, value]) => (
        <Row className='border border-light d-grid d-sm-flex'>
          <Col sm={{ span: 4 }} className='border-end'>
            <p className='fw-bold my-auto'>{key}</p>
          </Col>
          <Col sm={{ span: 2 }}>
            <p>{value}</p>
          </Col>
        </Row>
      ))}
    </Container>
  );
};

export default Order;
