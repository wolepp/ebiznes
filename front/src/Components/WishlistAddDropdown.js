import { Dropdown } from "react-bootstrap";
import { useContext, useEffect } from "react";
import { storeContext } from "../storeContext";
import { Link } from "react-router-dom";

const WishlistAddDropdown = ({ product }) => {
  const store = useContext(storeContext);

  const { wishlists } = store.state;

  const addToWishlist = (wishlistName) => {
    store.dispatch({
      type: 'add-to-wishlist', payload: {
        wishlistName: wishlistName,
        product: product
      }
    })
  }

  useEffect(() => {

  }, [wishlists]);


  return (
    <Dropdown className="d-inline mx-2" autoClose="outside">
      <Dropdown.Toggle  id="dropdown-autoclose-outside" variant='outline-primary'>
        Add to wishlist
      </Dropdown.Toggle>

      <Dropdown.Menu>
        <Dropdown.Item as={Link} to="/wishlists">Create new wishlist</Dropdown.Item>

        {Object.keys(wishlists).length ? (
          <>
            <Dropdown.Divider />
            {Object.keys(wishlists).map(wishlistName => (
              <Dropdown.Item
                key={wishlistName}
                onClick={() => addToWishlist(wishlistName)}
              >
                {wishlistName}
              </Dropdown.Item>
            ))}
          </>
        ) : <></>}

      </Dropdown.Menu>
    </Dropdown>
  );
};

export default WishlistAddDropdown;
