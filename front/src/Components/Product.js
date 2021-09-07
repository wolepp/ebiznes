import { Button, Col, Container, Row } from "react-bootstrap";
import { useParams } from "react-router-dom";
import { useContext, useEffect, useState } from "react";
import { storeContext } from "../storeContext";
import { getProduct } from "../Services/ProductAPI";
import CartQuantityCounter from "./CartQuantityCounter";
import WishlistAddDropdown from "./WishlistAddDropdown";

const Product = () => {
  const store = useContext(storeContext);
  const [product, setProduct] = useState({});
  const { id } = useParams();

  useEffect(() => {
    getProduct(id)
      .then(data => setProduct(data))
  }, []);

  const addToCart = (product) => {
    store.dispatch({ type: 'add-to-cart', payload: product });
  }

  return (
    <Container>
      <Row className='justify-content-between align-items-end mb-3'>
        <Col>
          <h1>{product.name}</h1>
        </Col>
        <Col>
          <h3 className='text-end'>{product.price} PLN</h3>
        </Col>
      </Row>
      <Row className='justify-content-end align-items-stretch'>
      </Row>

      <Row className='justify-content-end align-items-stretch mb-5'>

        <Col sm={{ span: 3 }} className='my-1'>
          {store.state.cart.has(product.id)
            ?
            // <Col sm={{ span: "auto", offset: '1' }}>
              <CartQuantityCounter product={product} />
            // </Col>
            :
            // <Col sm={{ span: "auto", offset: '1' }}>
              <Button
                className='w-100 h-100'
                variant='primary'
                onClick={() => addToCart(product)}
              >
                Add to cart
              </Button>
            // </Col>
          }
        </Col>

        <Col sm={{ span: 3 }} className='my-1'>
          <WishlistAddDropdown product={product} />
        </Col>

      </Row>

      <Row>
        <Col>
          <p>{product.description}</p>
        </Col>
      </Row>

    </Container>
  );
};

export default Product;
