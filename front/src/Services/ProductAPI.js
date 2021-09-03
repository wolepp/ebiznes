import sendRequest from "../utils";

const PRODUCT_ENDPOINT = process.env.REACT_APP_API_URL + '/product';

const getProducts = async () => {
  return await sendRequest(PRODUCT_ENDPOINT, 'get');
}

const getProduct = async (id) => {
  return await sendRequest(PRODUCT_ENDPOINT + `/${id}`, 'get');
}

export {
  getProducts,
  getProduct,
}