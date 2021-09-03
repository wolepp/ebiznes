import { Redirect, Route, Switch } from "react-router-dom";
import SignUp from "./Components/SignUp";
import { Col } from "react-bootstrap";
import Login from "./Components/Login";
import Products from "./Components/Products";

const Routes = () => (
  <Switch>

    <Route exact path='/'>
      <Redirect to='/products' />
    </Route>

    <Route path='/auth/login'>
      <Col
        md={{ span: 8, offset: 2 }}
        lg={{ span: 6, offset: 3 }}
        className='my-4'>
        <Login />
      </Col>
    </Route>

    <Route path='/auth/logout'>
      <p>Logout redirect placeholder</p>
    </Route>

    <Route path='/auth/signup'>
      <Col
        md={{ span: 8, offset: 2 }}
        lg={{ span: 6, offset: 3 }}
        className='my-4'>
        <SignUp />
      </Col>
    </Route>

    <Route path='/cart'>
      <p>Cart component placeholder</p>
    </Route>

    <Route path='/wishlists'>
      <p>Wishlists component placeholder</p>
    </Route>

    <Route path='/account'>
      <p>Account component placeholder</p>
    </Route>

    <Route path='/product/:id'>
      <p>Product Id component placeholder</p>
    </Route>

    <Route path='/products'>
      <Col
        xl={{ span: 8, offset: 4 }}
        className='my-4'
      >
        <Products />
      </Col>
    </Route>

    <Route path='/orders'>
      <p>Orders component placeholder, you can make return here</p>
    </Route>

    <Route path='/returns'>
      <p>Returns component placeholder</p>
    </Route>

  </Switch>
)

export default Routes;