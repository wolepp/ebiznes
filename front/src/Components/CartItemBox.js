import { Col, Container, Row } from "react-bootstrap";
import CartQuantityCounter from "./CartQuantityCounter";

const CartItemBox = ({ product, count }) => {

  return (
    <Container
      className="border-bottom border-light border-3 my-2"
    >
      <Row>
        <Col>
          <h5>{product.name}</h5>
        </Col>
        <Col>
          <h5>{product.price} PLN / 1</h5>
        </Col>
        <Col>
          <CartQuantityCounter product={product} width='140' />
        </Col>
        <Col>
          <h5>{product.price * count} PLN</h5>
        </Col>
      </Row>
    </Container>
  );
};

export default CartItemBox;
