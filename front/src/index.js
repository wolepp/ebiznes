import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import 'bootstrap/dist/css/bootstrap.min.css';
import { StoreProvider } from "./Store/storeContext";
import { UserProvider } from "./userContext";


ReactDOM.render(
  <UserProvider>
    <StoreProvider>
      <App />
    </StoreProvider>,
  </UserProvider>,
  document.getElementById('root')
);

