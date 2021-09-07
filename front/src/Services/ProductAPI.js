import sendRequest from "../utils";

const PRODUCT_ENDPOINT = process.env.REACT_APP_API_URL + '/product';

const getProducts = async () => {
  return await sendRequest(PRODUCT_ENDPOINT, 'get');
}

const getProductsOfCategory = async (categoryId) => {
  const allProducts = await sendRequest(PRODUCT_ENDPOINT, 'get');
  return new Promise((resolve, _) => {
    resolve(allProducts.filter(p => p.categoryId.toString() === categoryId))
  });
}

const getProduct = async (id) => {
  return await sendRequest(PRODUCT_ENDPOINT + `/${id}`, 'get');
}

export {
  getProducts,
  getProductsOfCategory,
  getProduct,
}