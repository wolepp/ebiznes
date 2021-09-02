import { Redirect, Route, Switch } from "react-router-dom";

const Routes = () => (
  <Switch>

    <Route exact path='/'>
      <Redirect to='/products' />
    </Route>

    <Route path='/auth/login'>
      <p>Login component placeholder</p>
    </Route>

    <Route path='/auth/logout'>
      <p>Logout redirect placeholder</p>
    </Route>

    <Route path='/auth/signup'>
      <p>Sign up component placeholder</p>
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
      <p>Products component placeholder</p>
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
