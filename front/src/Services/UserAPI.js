import sendRequest from "../utils";

const ORDERS_ENDPOINT = process.env.REACT_APP_API_URL + '/order';
const RETURNS_ENDPOINT = process.env.REACT_APP_API_URL + '/return';

const LOGIN_ENDPOINT = process.env.REACT_APP_API_URL + '/signIn';
const SIGNUP_ENDPOINT = process.env.REACT_APP_API_URL + '/signUp';

const getOrders = async () => {
  return sendRequest(ORDERS_ENDPOINT)
}

const getReturns = async () => {
  return sendRequest(RETURNS_ENDPOINT)
}

const logIn = async (userData) => {
  return sendRequest(LOGIN_ENDPOINT, userData, 'POST');
}

const signUp = async (userData) => {
  return sendRequest(SIGNUP_ENDPOINT, userData, 'POST');
}

export {
  getOrders,
  getReturns,
  logIn,
  signUp,
}