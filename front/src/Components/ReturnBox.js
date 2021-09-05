import { Col, Container, Row } from "react-bootstrap";

const ReturnBox = ({ aReturn }) => {
  const dataRow = 'mb-3 border-bottom border-light border-3';

  return (
    <Container className='mb-3 p-4 pb-2 shadow-sm'>

      <Row className={dataRow}>
        <Col md={{ span: 4 }}>
          <h6>Id</h6>
        </Col>
        <Col md={{ span: 7 }}>
          <h6>{aReturn.id}</h6>
        </Col>
      </Row>

      <Row className={dataRow}>
        <Col md={{ span: 4 }}>
          <h6>orderId</h6>
        </Col>
        <Col md={{ span: 7 }}>
          <h6>{aReturn.orderId}</h6>
        </Col>
      </Row>

      <Row>
        <Col md={{ span: 4 }}>
          <h6>Status</h6>
        </Col>
        <Col md={{ span: 7 }}>
          <h6>{aReturn.status}</h6>
        </Col>
      </Row>

    </Container>
  )
}

export default ReturnBox;