import { Container, Nav, Navbar, NavDropdown } from "react-bootstrap";
import { Link } from "react-router-dom";
import { storeContext } from "../Store/storeContext";
import { useContext } from "react";
import { userContext } from "../userContext";

const MyNavbar = () => {
  const store = useContext(storeContext);
  const user = useContext(userContext);

  const { count } = store.state;
  const { email, name } = user.state;

  const increment = () => {
    store.dispatch({ type: 'increment' });
  }

  return (
    <Navbar collapseOnSelect expand="lg" bg="dark" variant="dark" sticky="top">
      <Container>
        <Navbar.Brand as={Link} to="/">The Shop</Navbar.Brand>
        <Navbar.Toggle aria-controls="responsive-navbar-nav" />
        <Navbar.Collapse id="responsive-navbar-nav">
          <Nav className="me-auto">
            <Nav.Link as={Link} to="/cart">Cart</Nav.Link>
            <Nav.Link as={Link} to="/wishlists">Wishlists</Nav.Link>
            <Nav.Link onClick={increment}>{count}</Nav.Link>
          </Nav>

          {email ?
            <Nav>
              <NavDropdown title={"Logged in as: " + name} id="collasible-nav-dropdown">
                <NavDropdown.Item as={Link} to="/account">Account</NavDropdown.Item>
                <NavDropdown.Item as={Link} to="/orders">Orders</NavDropdown.Item>
                <NavDropdown.Item as={Link} to="/returns">Returns</NavDropdown.Item>
                <NavDropdown.Divider />
                <NavDropdown.Item as={Link} to="/auth/logout">Log out</NavDropdown.Item>
              </NavDropdown>
            </Nav>
            :
            <Nav>
              <Nav.Link as={Link} to="/auth/login">Log in</Nav.Link>
              <Nav.Link as={Link} to="/auth/signup" bg="primary">Sign up</Nav.Link>
            </Nav>
          }

        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};

export default MyNavbar;
