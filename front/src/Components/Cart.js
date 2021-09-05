import { Button, Col, Container, Row } from "react-bootstrap";
import { useContext } from "react";
import { storeContext } from "../storeContext";
import CartItemBox from "./CartItemBox";

const Cart = () => {
  const store = useContext(storeContext);

  const { cart } = store.state;

  const clearCart = () => {
    store.dispatch({ type: 'clear-cart' });
  }

  return (
    <Container>

      {Array.from(cart).length ? (
        <>
          <Row className='mb-5'>
            <Col>
              <Button onClick={clearCart} variant='outline-danger'>Clear cart</Button>
            </Col>
          </Row>

          <Row>
            {Array.from(cart, ([productId, { count, product }]) => (
              <CartItemBox key={productId} product={product} count={count} />
            ))}
          </Row>
        </>
      ) :
        <h3>Your cart is empty, go add some products!</h3>
      }

    </Container>
  );
};

export default Cart;
