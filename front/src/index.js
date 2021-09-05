import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import 'bootstrap/dist/css/bootstrap.min.css';
import { StoreProvider } from "./storeContext";


ReactDOM.render(
    <StoreProvider>
      <App />
    </StoreProvider>,
  document.getElementById('root')
);

