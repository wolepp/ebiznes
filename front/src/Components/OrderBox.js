import { Button, Col, Container, Row } from "react-bootstrap";
import OrderItemsTable from "./OrderItemsTable";

const OrderBox = ({ order }) => {
  const dataRow = 'mb-3 border-bottom border-light border-3';

  return (
    <Container className='mb-3 p-4 pb-2 shadow-sm'>
      <Row className='align-items-center justify-content-between'>

        {/* left column */}
        <Col md={{ span: 8 }}>

          <Row className={dataRow}>
            <Col md={{ span: 4 }}>
              <h6>Id</h6>
            </Col>
            <Col md={{ span: 7 }}>
              <h6>{order.id}</h6>
            </Col>
          </Row>

          <Row className={dataRow}>
            <Col md={{ span: 4 }}>
              <h6>userId</h6>
            </Col>
            <Col md={{ span: 7 }}>
              <h6>{order.userId}</h6>
            </Col>
          </Row>

          <Row className={dataRow}>
            <Col md={{ span: 4 }}>
              <h6>deliveryId</h6>
            </Col>
            <Col md={{ span: 7 }}>
              <h6>{order.deliveryId || "No delivery available"}</h6>
            </Col>
          </Row>

          <Row>
            <Col md={{ span: 4 }}>
              <h6>paymentId</h6>
            </Col>
            <Col md={{ span: 3 }}>
              <h6>{order.paymentId || "Not paid"}</h6>
            </Col>
          </Row>

        </Col>

        {/* right column */}
        <Col md={{ span: 3 }}>
          <Button onClick={(e) => {
            // todo: zaimplementować
            alert("Not implemented")
          }}>
            Make a return
          </Button>
        </Col>

      </Row>

      <Row className='mt-3'>
        <Col>
          <OrderItemsTable orderId={order.id} />
        </Col>
      </Row>
    </Container>
  );
};

export default OrderBox;
