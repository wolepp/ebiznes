import { Col, Container, Row } from "react-bootstrap";
import { useParams } from "react-router-dom";

const Product = () => {
  const { id } = useParams();

  return (
    <Container>
      <Row>
        <Col>
          {JSON.stringify(id)}
        </Col>
      </Row>
    </Container>
  );
};

export default Product;
