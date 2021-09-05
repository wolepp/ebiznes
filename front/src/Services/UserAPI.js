import sendRequest from "../utils";

const USER_ENDPOINT = process.env.REACT_APP_API_URL + '/user';
const ORDERS_ENDPOINT = process.env.REACT_APP_API_URL + '/order';
const RETURNS_ENDPOINT = process.env.REACT_APP_API_URL + '/return';

const getCurrentUser = async () => {
  // todo: change endpoint - it will not be user with id "1" :)
  return await sendRequest(USER_ENDPOINT + '/' + 1, 'get')
}

const getOrders = async () => {
  return await sendRequest(ORDERS_ENDPOINT, 'get')
}

const getReturns = async () => {
  return await sendRequest(RETURNS_ENDPOINT, 'get')
}

export {
  getCurrentUser,
  getOrders,
  getReturns,
}