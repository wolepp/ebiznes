import sendRequest from "../utils";
import { getProduct } from "./ProductAPI";

const ORDER_ENDPOINT = process.env.REACT_APP_API_URL + '/order';
const ORDER_ITEM_ENDPOINT = process.env.REACT_APP_API_URL + '/orderitem';

const getOrder = async (id) => {
  return await sendRequest(ORDER_ENDPOINT + `/${id}`);
}

const createOrder = async (cart) => {

  const orderData = await sendRequest(ORDER_ENDPOINT, {
    userId: 1
  }, 'POST');

  const orderId = orderData[orderData.length - 1].id;

  for (const { count, product } of cart) {
    const data = {
      "orderId": orderId,
      "productId": product.id,
      "quantity": count,
    }
    console.log(data);
    await sendRequest(ORDER_ITEM_ENDPOINT, {
      "orderId": orderId,
      "productId": product.id,
      "quantity": count,
    }, 'POST')
  }
}

const getOrderItems = async (orderId) => {
  const allOrdersItems = await sendRequest(ORDER_ITEM_ENDPOINT);
  const orderItems = allOrdersItems.filter(o => o.orderId === orderId);

  const products = [];
  for (const o of orderItems) {
    const product = await getProduct(o.productId)
    products.push(product);
  }

  return new Promise((resolve, _) => {
    resolve({
      orderItems: orderItems,
      products: products,
    })
  })

}

export {
  getOrder,
  createOrder,
  getOrderItems,
}