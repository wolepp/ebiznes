export default class CartReducer {

  static addToCart(state, product) {
    if (state.cart.has(product.id)) {
      state.cart.get(product.id).count += 1;
    } else {
      state.cart.set(product.id, {
        count: 1,
        product: product,
      });
    }
  }

  static addToCartIfNotExists(state, product) {
    if (state.cart.has(product.id)) {
      return;
    }
    state.cart.set(product.id, {
      count: 1,
      product: product,
    });
  }

  static removeFromCart(state, product) {
    if (state.cart.has(product.id)) {
      if (state.cart.get(product.id).count === 1) {
        state.cart.delete(product.id);
      } else {
        state.cart.get(product.id).count -= 1;
      }
    }
  }

  static setQuantity(state, productId, newCount) {
    if (state.cart.has(productId)) {
      if (newCount) {
        state.cart.get(productId).count = newCount;
      } else {
        state.cart.delete(productId);
      }
    }
  }

}