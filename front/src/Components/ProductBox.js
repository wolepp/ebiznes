import { Button, Col, Container, Row } from "react-bootstrap";
import { useContext } from "react";
import { storeContext } from "../storeContext";
import CartQuantityCounter from "./CartQuantityCounter";
import WishlistAddDropdown from "./WishlistAddDropdown";

const ProductBox = (
  {
    product,
    wishlistAddDropdown = true,
    onRemove = false,
    last = false,
  }) => {

  const store = useContext(storeContext);

  const addToCart = (product) => {
    store.dispatch({ type: 'add-to-cart', payload: product });
  }

  const containerClass = last
    ?
    "mb-2"
    :
    "border-bottom border-light border-3 mb-2"

  return (
    <Container
      className={containerClass}
      style={{ minHeight: '120px', maxHeight: '250px' }}
    >
      <Row className='align-items-center justify-content-center'>

        <Col>

          <Container className='p-0'>
            <Row className='mt-3 p-0 justify-content-between'>


              <Col sm={{ span: 3, offset: 0 }} className='px-4'>
                <h4>{product.name}</h4>
              </Col>

              <Col sm={{ span: 2, offset: 0 }}>
                <h5>{product.price} zł</h5>
              </Col>

              {wishlistAddDropdown &&
              <Col sm={{ span: 2, offset: 0 }}>
                <WishlistAddDropdown product={product} />
              </Col>
              }

              {store.state.cart.has(product.id)
                ?
                <Col sm={{ span: "auto", offset: '1' }}>
                  <CartQuantityCounter product={product} />
                </Col>
                :
                <Col sm={{ span: "auto", offset: '1' }}>
                  <Button
                    style={{ width: '120px' }}
                    variant='primary'
                    onClick={() => addToCart(product)}
                  >
                    Add to cart
                  </Button>
                </Col>
              }

              {onRemove &&
              <Col sm={{ span: "auto" }} className='text-end'>
                <Button
                  variant='outline-secondary'
                  size='sm'
                  onClick={onRemove}
                >
                  X
                </Button>
              </Col>
              }


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
