import { Button, Col, Container, Row } from "react-bootstrap";
import { useContext, useEffect, useState } from "react";
import { storeContext } from "../storeContext";
import ProductBox from "./ProductBox";

const WishlistBox = ({ wishlistName }) => {
  const store = useContext(storeContext);

  const { wishlists } = store.state;

  const [products, setProducts] = useState([]);

  useEffect(() => {
    setProducts(wishlists[wishlistName])
  }, [wishlists, wishlists[wishlistName]]);

  const removeWishlist = () => {
    store.dispatch({
      type: 'remove-wishlist', payload: {
        wishlistName: wishlistName,
      }
    })
  }

  const removeFromWishlist = (p) => {
    store.dispatch({
      type: 'remove-from-wishlist', payload: {
        product: p,
        wishlistName: wishlistName,
      }
    })
  }

  const addAllToCart = () => {
    store.dispatch({
      type: 'add-all-from-wishlist-to-cart', payload: {
        wishlistName: wishlistName,
      }
    })
  }


  return (
    <Container
      className="my-5 border border-light border-3"
    >
      <Row className='justify-content-between p-3'>
        <Col sm>
          <h3 className='fst-italic text-decoration-underline'>{wishlistName}</h3>
        </Col>

        <Col sm={{span: "auto"}} className='text-end'>
          <Button
            variant='outline-secondary'
            onClick={addAllToCart}
          >
            Add all to cart
          </Button>
        </Col>

        <Col sm={{span: "auto"}} className='text-end'>
          <Button
            variant='outline-secondary'
            onClick={removeWishlist}
          >
            Remove wishlist
          </Button>
        </Col>

      </Row>

      {products.length > 0 ? (
          <Row>
            <Col className='border-top border-light border-2'>
              {products.map((p, i, { length }) => {
                if (i === length - 1) {
                  return <ProductBox
                    key={p.id}
                    product={p}
                    wishlistAddDropdown={false}
                    last={true}
                    onRemove={() => removeFromWishlist(p)}
                  />
                } else {
                  return <ProductBox
                    key={p.id}
                    product={p}
                    wishlistAddDropdown={false}
                    onRemove={() => removeFromWishlist(p)}
                  />
                }
              })}

            </Col>
          </Row>
        ) :
        <Row className='p-3'>
          <Col>
            <h5>Nothing here yet, go add some products!</h5>
          </Col>
        </Row>
      }

    </Container>
  );
};

export default WishlistBox;
