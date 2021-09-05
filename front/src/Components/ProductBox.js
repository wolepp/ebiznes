import { Button, Col, Container, Row } from "react-bootstrap";
import { useContext } from "react";
import { storeContext } from "../storeContext";

const ProductBox = ({ product }) => {
  const store = useContext(storeContext);

  const addToCart = (product) => {
    console.log(product);
    store.dispatch({ type: 'add-to-cart', payload: product });
  }

  return (
    <Container
      className="border-bottom border-light border-3 mb-2"
      style={{ minHeight: '120px', maxHeight: '250px' }}
    >
      <Row>
        <Col>

          <Container className='p-0'>
            <Row className='mt-3 p-0'>

              <Col sm={{ span: 4, offset: 0 }} className='px-4'>
                <h3>{product.name}</h3>
              </Col>

              <Col sm={{ span: 2, offset: 2 }}>
                <h4>{product.price} zł</h4>
              </Col>

              <Col sm={{ span: 3, offset: 1 }}>
                <Button
                  variant='outline-primary'
                  onClick={() => addToCart(product)}
                >
                  Add to cart
                </Button>
              </Col>

            </Row>
          </Container>

          <Container className='p-3'>
            <Row>
              <Col>
                <p className="text-truncate ">
                  {product.description}
                </p>
              </Col>
            </Row>
          </Container>

        </Col>
      </Row>
    </Container>
  );
};

export default ProductBox;
