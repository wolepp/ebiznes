import { Button, Col, Container, Row } from "react-bootstrap";
import { useContext, useEffect, useState } from "react";
import { storeContext } from "../storeContext";
import CartItemBox from "./CartItemBox";

const Cart = () => {
  const store = useContext(storeContext);

  const { cart } = store.state;

  const clearCart = () => {
    store.dispatch({ type: 'clear-cart' });
  }

  const [totalPrice, setTotalPrice] = useState(0);

  useEffect(() => {
    let calculatedPrice = 0;
    for (const {count, product} of cart.values()) {
      calculatedPrice += count * product.price;
    }
    setTotalPrice(calculatedPrice);
  }, [cart, cart.entries()]);



  return (
    <Container>

      {Array.from(cart).length ? (
        <>
          <Row className='mt-3 mb-5 justify-content-between'>
            <Col md={{ span: 3 }}>
              <h2>Cart</h2>
            </Col>
            <Col md={{ span: "auto"}}>
              <Button onClick={clearCart} variant='outline-danger'>Clear cart</Button>
            </Col>
          </Row>

          <Row>
            {Array.from(cart, ([productId, { count, product }]) => (
              <CartItemBox key={productId} product={product} count={count} />
            ))}
          </Row>

          <Row className='mt-5 align-items-center justify-content-end'>
            <Col md={{ span: "auto" }}>
              <Row className='text-end'>
                <h4>Total price</h4>
              </Row>
              <Row>

                <h3>{totalPrice} PLN</h3>
              </Row>
            </Col>
          </Row>
        </>
      ) :
        <h3>Your cart is empty, go add some products!</h3>
      }

    </Container>
  );
};

export default Cart;
