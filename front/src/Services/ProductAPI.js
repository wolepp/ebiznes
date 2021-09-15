import sendRequest from "../utils";

const PRODUCT_ENDPOINT = process.env.REACT_APP_API_URL + '/product';

const getProducts = async () => {
  return sendRequest(PRODUCT_ENDPOINT);
}

const getProductsOfCategory = async (categoryId) => {
  const allProducts = await sendRequest(PRODUCT_ENDPOINT);

  return Promise.resolve(
    allProducts.filter(p => p.categoryId.toString() === categoryId)
  )
}

const getProduct = async (id) => {
  return sendRequest(PRODUCT_ENDPOINT + `/${id}`);
}

export {
  getProducts,
  getProductsOfCategory,
  getProduct,
}