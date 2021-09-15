import { Button, Col, Container, FormControl, InputGroup, Row } from "react-bootstrap";
import { useContext } from "react";
import { storeContext } from "../Store/storeContext";

const CartQuantityCounter = ({ product, width }) => {
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
    <Container className='p-0 m-0'>
      <Row className='p-0 m-0'>
        <Col className='p-0 m-0'>
          <InputGroup
            className='p-0 m-0'
            style={{ width: width + 'px' }}
          >
            <Button className='w-25' variant='outline-primary' onClick={decrementProduct}>-</Button>
            <FormControl
              type='text'
              className='w-50'
              value={store.state.cart.get(product.id).count}
              onChange={(e) => updateCount(e.target.value)}
            />
            <Button className='w-25' variant='outline-primary' onClick={incrementProduct}>+</Button>
          </InputGroup>
        </Col>
      </Row>
    </Container>
  );
};

export default CartQuantityCounter;
