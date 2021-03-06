import sendRequest from "../utils";

const CATEGORIES_ENDPOINT = process.env.REACT_APP_API_URL + '/category';

const getCategories = async () => {
  return sendRequest(CATEGORIES_ENDPOINT);
}

export {
  getCategories,
}
