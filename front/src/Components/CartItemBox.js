import { Button, Col, Container, FormControl, InputGroup, Row } from "react-bootstrap";
import { useContext } from "react";
import { storeContext } from "../storeContext";

const CartItemBox = ({ product, count }) => {
  const store = useContext(storeContext);

  const incrementProduct = () => {
    store.dispatch({ type: 'add-to-cart', payload: product })
  }

  const decrementProduct = () => {
    store.dispatch({ type: 'remove-from-cart', payload: product })
  }

  const updateCount = (newCount) => {
    const newCountInt = parseInt(newCount)
    if (newCountInt || newCountInt === 0) {
      store.dispatch({
        type: 'cart-set-quantity', payload: {
          productId: product.id,
          newCount: newCountInt,
        }
      });
    }
  }

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
          <InputGroup
            style={{ width: '140px' }}
          >
            <Button variant='outline-danger' onClick={decrementProduct}>-</Button>
            <FormControl
              type='text'
              style={{ width: '70px' }}
              variant='outline-primary'
              value={count}
              onChange={(e) => updateCount(e.target.value)}
            />
            <Button variant='outline-success' onClick={incrementProduct}>+</Button>
          </InputGroup>
        </Col>
        <Col>
          <h5>{product.price * count} PLN</h5>
        </Col>
      </Row>
    </Container>
  );
};

export default CartItemBox;
