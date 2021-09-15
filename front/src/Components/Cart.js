import { Button, Col, Container, Row } from "react-bootstrap";
import { useContext, useEffect, useState } from "react";
import { storeContext } from "../Store/storeContext";
import CartItemBox from "./CartItemBox";
import { createOrder } from "../Services/OrderAPI";
import { useHistory } from "react-router-dom";
import { userContext } from "../userContext";

const Cart = () => {
  const store = useContext(storeContext);
  const user = useContext(userContext);
  const history = useHistory();

  const { cart } = store.state;

  const clearCart = () => {
    store.dispatch({ type: 'clear-cart' });
  }

  const [totalPrice, setTotalPrice] = useState(0);

  useEffect(() => {
    let calculatedPrice = 0;
    for (const { count, product } of cart.values()) {
      calculatedPrice += count * product.price;
    }
    setTotalPrice(calculatedPrice);
  }, [cart, cart.entries()]);

  const buyHandler = async () => {
    if (!user.state.email) {
      history.push('/auth/login');
      return;
    }
    if (cart.values().length === 0) {
      alert("Your cart is empty");
      return;
    }
    await createOrder(cart.values());
    history.push('/orders');
  }


  return (
    <Container>

      {Array.from(cart).length ? (
          <>
            <Row className='mt-3 mb-5 justify-content-between align-content-center'>
              <Col md={{ span: 3 }} className='me-auto'>
                <h2>Cart</h2>
              </Col>
              <Col md={{ span: 4 }} className='d-grid gap-2 d-sm-flex justify-content-sm-end'>
                <Button onClick={buyHandler} variant='primary' size='lg'>Buy</Button>
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
        <Row className='mt-3 mb-5 justify-content-between'>
          <h2>Your cart is empty, go add some products!</h2>
        </Row>
      }

    </Container>
  );
};

export default Cart;
