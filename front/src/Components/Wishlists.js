import { Button, Col, Container, FormControl, InputGroup, Row } from "react-bootstrap";
import { useContext, useState } from "react";
import { storeContext } from "../Store/storeContext";
import WishlistBox from "./WishlistBox";

const Wishlists = () => {
  const store = useContext(storeContext);

  const { wishlists } = store.state;

  const [wishlistName, setWishlistName] = useState('');

  const addWishlist = () => {
    if (!wishlistName) {
      alert("Can't create wishlist with empty name")
      return;
    }

    if (wishlists[wishlistName]) {
      alert(`${wishlistName} already exists`);
    } else {
      store.dispatch({ type: 'create-wishlist', payload: { wishlistName: wishlistName.toString() } });
    }
  }

  return (
    <Container>
      <Row className='mt-3 mb-5 justify-content-between'>
        <Col md={{ span: 3 }}>
          <h2>Wishlists</h2>
        </Col>
        <Col md={{ span: "auto" }}>
          <InputGroup className="mb-3">
            <Button
              variant="outline-primary"
              id="button-create-wishlist"
              onClick={addWishlist}
            >
              Create new wishlist
            </Button>
            <FormControl
              aria-label="Wishlist name"
              aria-describedby="button-create-wishlist"
              onChange={(e) => setWishlistName(e.target.value.toLowerCase())}
              onKeyPress={(e) => {
                if (e.key === "Enter") addWishlist()
              }}
            />
          </InputGroup>
        </Col>
      </Row>

      {Object.keys(wishlists).map(wishlist => (
        <WishlistBox key={wishlist} wishlistName={wishlist} />
      ))}

    </Container>
  );
};

export default Wishlists;
