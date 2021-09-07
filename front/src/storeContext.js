import { createContext, useReducer } from "react";

const KEY = 'EBIZNES_STATE';
const storage = localStorage;

const defaultState = {
  count: 0,
  cart: new Map(),
  wishlists: {},
}

// todo: Nie działa zapisywanie lub ładowanie koszyka
const loadData = (state) => {
  try {
    const value = storage.getItem(KEY);

    if (!value) return;
    const data = JSON.parse(value);

    state.count = data.count;
    state.wishlists = data.wishlists;
    state.cart = new Map(data.cart);

  } catch (e) {
    // nothing stored or wrong data
  }
}

const saveData = (state) => {
  try {
    storage.setItem(KEY, JSON.stringify({
      count: state.count,
      wishlists: state.wishlists,
      cart: [...state.cart],
    }))
  } catch (e) {
    // no storage
  }
}

const reducer = (state, action) => {
  switch (action.type) {

    case 'CLEAR-STATE': {
      const newState = defaultState;
      saveData(newState);
      return newState;
    }

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

    case 'create-wishlist': {
      const { wishlistName } = action.payload
      const { wishlists } = state;

      if (wishlists[wishlistName]) {
        return state;
      }

      wishlists[wishlistName] = [];

      const newState = { ...state, wishlists: wishlists }
      saveData(newState);
      return newState;
    }

    case 'remove-wishlist': {
      const { wishlistName } = action.payload;
      const { wishlists } = state;

      if (!wishlists[wishlistName]) {
        return state;
      }

      delete wishlists[wishlistName];

      const newState = { ...state, wishlists: wishlists }
      saveData(newState);
      return newState;
    }

    case 'add-to-wishlist': {
      const { product, wishlistName } = action.payload;
      const { wishlists } = state;

      const wishlist = wishlists[wishlistName];

      if (wishlist) {
        if (wishlist.find(p => p.id === product.id)) {
          return state;
        }
        wishlist.push(product);
      } else {
        return state;
      }

      const newState = { ...state, wishlists: wishlists }
      saveData(newState);
      return newState;
    }

    case 'remove-from-wishlist': {
      const { product, wishlistName } = action.payload;
      const { wishlists } = state;

      const wishlist = wishlists[wishlistName];

      if (wishlist) {
        const index = wishlist.indexOf(wishlist.find(p => p.id === product.id));
        if (index !== -1) {
          wishlist.splice(index, 1);
        }
      } else {
        return state;
      }

      const newState = { ...state, wishlists: wishlists }
      saveData(newState);
      return newState;
    }

    case 'add-all-from-wishlist-to-cart': {
      const { wishlistName } = action.payload;
      const { cart, wishlists } = state;

      const wishlist = wishlists[wishlistName];

      if (wishlist) {
        wishlist.forEach(p => {
          if (!cart.has(p.id)) {
            cart.set(p.id, {
              count: 1,
              product: p,
            })
          }
        })
      }

      const newState = { ...state, cart: cart }
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