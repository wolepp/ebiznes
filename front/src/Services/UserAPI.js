import sendRequest from "../utils";

const USER_ENDPOINT = process.env.REACT_APP_API_URL + '/user';
const ORDERS_ENDPOINT = process.env.REACT_APP_API_URL + '/order';
const RETURNS_ENDPOINT = process.env.REACT_APP_API_URL + '/return';

const LOGIN_ENDPOINT = process.env.REACT_APP_API_URL + '/signIn';
const SIGNUP_ENDPOINT = process.env.REACT_APP_API_URL + '/signUp';

const getOrders = async () => {
  return await sendRequest(ORDERS_ENDPOINT)
}

const getReturns = async () => {
  return await sendRequest(RETURNS_ENDPOINT)
}

const logIn = async (userData) => {
  return await sendRequest(LOGIN_ENDPOINT, userData, 'POST');
}

const signUp = async (userData) => {
  return await sendRequest(SIGNUP_ENDPOINT, userData, 'POST');
}

export {
  getOrders,
  getReturns,
  logIn,
  signUp,
}