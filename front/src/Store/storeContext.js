import { createContext, useReducer } from "react";
import CartReducer from "./CartReducer";
import WishlistReducer from "./WishlistReducer";

const KEY = 'EBIZNES_STATE';
const storage = localStorage;

const defaultState = {
  count: 0,
  cart: new Map(),
  wishlists: {},
}

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

      CartReducer.addToCart(newState, product);

      saveData(newState);
      return newState;
    }

    case 'remove-from-cart': {
      const product = action.payload;
      const newState = { ...state }

      CartReducer.removeFromCart(newState, product);

      saveData(newState);
      return newState;
    }

    case 'cart-set-quantity': {
      const { productId, newCount } = action.payload;
      const newState = { ...state }

      CartReducer.setQuantity(newState, productId, newCount);

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

      WishlistReducer.createWishlist(wishlists, wishlistName);

      const newState = { ...state, wishlists: wishlists }
      saveData(newState)
      return newState;
    }

    case 'remove-wishlist': {
      const { wishlistName } = action.payload;
      const { wishlists } = state;

      WishlistReducer.removeWishlist(wishlists, wishlistName)

      const newState = { ...state, wishlists: wishlists }
      saveData(newState);
      return newState;
    }

    case 'add-to-wishlist': {
      const { product, wishlistName } = action.payload;
      const { wishlists } = state;

      WishlistReducer.addToWishlist(wishlists, wishlistName, product);

      const newState = { ...state, wishlists: wishlists }
      saveData(newState);
      return newState;
    }

    case 'remove-from-wishlist': {
      const { product, wishlistName } = action.payload;
      const { wishlists } = state;

      WishlistReducer.removeFromWishlist(wishlists, wishlistName, product);

      const newState = { ...state, wishlists: wishlists }
      saveData(newState);
      return newState;
    }

    case 'add-all-from-wishlist-to-cart': {
      const { wishlistName } = action.payload;
      const newState = { ...state };

      WishlistReducer.addAllToCart(newState, wishlistName)

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