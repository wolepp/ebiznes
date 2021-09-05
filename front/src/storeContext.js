import { createContext, useReducer } from "react";

const KEY = 'EBIZNES_STATE';
const storage = localStorage;

const defaultState = {
  count: 0,
  cart: new Map(),
}

// todo: Nie działa zapisywanie lub ładowanie koszyka
const loadData = (state) => {
  try {
    const value = storage.getItem(KEY);

    if (!value) return;
    const data = JSON.parse(value);

    state.count = data.count;
    state.cart = new Map(data.cart);

  } catch (e) {
    // nothing stored or wrong data
  }
}

const saveData = (state) => {
  try {
    storage.setItem(KEY, JSON.stringify({
      count: state.count,
      cart: state.cart,
    }))
  } catch (e) {
    // no storage
  }
}

const reducer = (state, action) => {
  switch (action.type) {

    case 'increment': {
      const newState = { ...state, count: state.count + 1 }
      saveData(newState);
      return newState;
    }

    case 'add-to-cart': {
      const product = action.payload;
      const newState = { ...state }

      if (newState.cart.has(product.id)) {
        newState.cart.get(product.id).count += 1;
      } else {
        newState.cart.set(product.id, {
          count: 1,
          product: product,
        })
      }

      saveData(newState);
      return newState;
    }

    case 'remove-from-cart': {
      const product = action.payload;
      const newState = { ...state }

      if (newState.cart.has(product.id)) {
        if (newState.cart.get(product.id).count === 1) {
          newState.cart.delete(product.id);
        } else {
          newState.cart.get(product.id).count -= 1;
        }
      }

      saveData(newState);
      return newState;
    }

    case 'cart-set-quantity': {
      const { productId, newCount } = action.payload;
      const newState = { ...state }

      if (newState.cart.has(productId)) {
        if (newCount) {
          newState.cart.get(productId).count = newCount;
        } else {
          newState.cart.delete(productId);
        }
      }

      saveData(newState);
      return newState;
    }

    case 'clear-cart': {
      const newState = { ...state, cart: new Map() }
      saveData(newState);
      return newState;
    }

    default:
      throw new Error('Unknown action type: ' + action.type);
  }
}

// ===========================

loadData(defaultState);
const storeContext = createContext(defaultState);
const { Provider } = storeContext;

const StoreProvider = ({ children }) => {
  const [state, dispatch] = useReducer(reducer, defaultState);
  return <Provider value={{ state, dispatch }}>{children}</Provider>
}

export { storeContext, StoreProvider }