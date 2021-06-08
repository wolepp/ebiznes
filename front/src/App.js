import React from 'react';
import Welcome from "./Welcome";
import Products from "./Products";
import Categories from "./Categories";
import {BrowserRouter, Link, Route} from "react-router-dom";

function App() {
  return (
    <div className="App">
      <div>
        <BrowserRouter>
          <ul>
            <li><Link to={"/"}>Home</Link></li>
            <li><Link to={"/products"}>Products</Link></li>
            <li><Link to={"/categories"}>Categories</Link></li>
          </ul>
          <Route exact path={"/"} render={() => <Welcome text={"Welcome to The Shop shop"}/>}/>
          <Route exact path={"/products"} component={Products}/>
          <Route exact path={"/categories"} component={Categories}/>
        </BrowserRouter>
      </div>
    </div>
  );
}

export default App;
