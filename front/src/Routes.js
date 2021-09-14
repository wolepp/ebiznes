import { Redirect, Route, Switch } from "react-router-dom";
import SignUp from "./Components/SignUp";
import { Col } from "react-bootstrap";
import Login from "./Components/Login";
import Products from "./Components/Products";
import Account from "./Components/Account";
import Orders from "./Components/Orders";
import Returns from "./Components/Returns";
import Cart from "./Components/Cart";
import Product from "./Components/Product";
import Wishlists from "./Components/Wishlists";
import Order from "./Components/Order";
import Categories from "./Components/Categories";
import Logout from "./Components/Logout";

const Routes = () => (
  <>
    {/* sidebar */}
    <Switch>

      <Route path='/products'>
        <Col
          xl={{ span: 4 }}
          className='my-4'
        >
          <Categories />
        </Col>
      </Route>

    </Switch>

    {/* main area */}

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
        <Logout />
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
        <Col
          xl={{ span: 8, offset: 2 }}
          className='my-4'
        >
          <Cart />
        </Col>
      </Route>

      <Route path='/wishlists'>
        <Col
          xl={{ span: 8, offset: 2 }}
          className='my-4'
        >
          <Wishlists />
        </Col>
      </Route>

      <Route path='/account'>
        <Col
          xl={{ span: 8, offset: 2 }}
          className='my-4'
        >
          <Account />
        </Col>
      </Route>

      <Route path='/product/:id'
             children={
               <Col
                 xl={{ span: 8, offset: 2 }}
                 className='my-4'
               >
                 <Product />
               </Col>
             }
      />

      <Route path='/products/:categoryId'>
        <Col
          xs={{ span: 12 }}
          xl={{ span: 8 }}
          className='my-4'
        >
          <Products />
        </Col>
      </Route>

      <Route path='/products'>
        <Col
          xs={{ span: 12 }}
          xl={{ span: 8 }}
          className='my-4'
        >
          <Products />
        </Col>
      </Route>

      <Route path='/orders'>
        <Col
          xl={{ span: 8, offset: 2 }}
          className='my-4'
        >
          <Orders />
        </Col>
      </Route>

      <Route path='/order/:id'
             children={
               <Col
                 xl={{ span: 8, offset: 2 }}
                 className='my-4'
               >
                 <Order />
               </Col>
             }
      />

      <Route path='/returns'>
        <Col
          xl={{ span: 8, offset: 2 }}
          className='my-4'
        >
          <Returns />
        </Col>
      </Route>

    </Switch>
  </>
)

export default Routes;
