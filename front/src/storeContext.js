import { createContext, useReducer } from "react";

const KEY = 'EBIZNES_STATE';
const storage = localStorage;

const defaultState = {
  count: 0,
}

const loadData = (state) => {
  try {
    const value = storage.getItem(KEY);

    if (!value) return;
    const data = JSON.parse(value);

    state.count = data.count;

  } catch (e) {
    // nothing stored or wrong data
  }
}

const saveData = (state) => {
  try {
    storage.setItem(KEY, JSON.stringify({
      count: state.count,
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